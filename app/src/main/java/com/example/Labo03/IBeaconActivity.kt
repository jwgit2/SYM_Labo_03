package com.example.Labo3

import android.os.Bundle
import com.example.Labo03.R
import androidx.appcompat.app.AppCompatActivity
import android.content.pm.PackageManager
import android.os.Build
import android.os.RemoteException
import android.util.Log
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.system.exitProcess
import com.example.Labo03.beacon.BeaconData
import com.example.Labo03.beacon.BeaconAdapter
import org.altbeacon.beacon.*
import org.altbeacon.beacon.powersave.BackgroundPowerSaver

/***
 * Activitée pour l'utilisation de l'iBeacon, faite avec une recycler view et avec la librairie
 * https://altbeacon.github.io/android-beacon-library/
 * necessite les permissions de localisation et de bluetooth
 *
 * Ressources sur les permissions: https://altbeacon.github.io/android-beacon-library/requesting_permission.html
 * see also InternalBeaconConsumer from altbeacon
 * https://altbeacon.github.io/android-beacon-library/autobind.html
 */
class IBeaconActivity : AppCompatActivity(), BeaconConsumer {
    private lateinit var beaconManager: BeaconManager
    private lateinit var beaconAdapter: BeaconAdapter
    private lateinit var beacon_recyclerView: RecyclerView

    private var beaconsDetected = ArrayList<BeaconData>()
    private var beaconFilter = "m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ibeacon)

        verifyPermissions()

        beaconManager = BeaconManager.getInstanceForApplication(this)
        beaconManager.beaconParsers.add(BeaconParser().setBeaconLayout(beaconFilter))
        beaconManager.bind(this)

        val layoutManager = LinearLayoutManager(this)

        // setup de la recycler view
        beacon_recyclerView = findViewById(R.id.beacon_recyclerView)
        beaconAdapter = BeaconAdapter(beaconsDetected)
        beacon_recyclerView.layoutManager = layoutManager
        beacon_recyclerView.adapter = beaconAdapter
        beacon_recyclerView.itemAnimator = DefaultItemAnimator()

    }

    // voir doc, repris en mode kotlin
    private fun verifyPermissions() {
        if (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED
        ) {
            if (checkSelfPermission(android.Manifest.permission.ACCESS_BACKGROUND_LOCATION)
                != PackageManager.PERMISSION_GRANTED
            ) {
                if (!shouldShowRequestPermissionRationale(
                        android.Manifest.permission.ACCESS_BACKGROUND_LOCATION)) {
                    val builder = android.app.AlertDialog.Builder(this)
                    builder.setTitle("This app needs background location access")
                    builder.setMessage("Please grant location access so this app can " +
                            "detect beacons in the background.")
                    builder.setPositiveButton(android.R.string.ok, null)
                    builder.setOnDismissListener {
                        requestPermissions(
                            arrayOf(android.Manifest.permission.ACCESS_BACKGROUND_LOCATION),
                            PERMISSION_REQUEST_BACKGROUND_LOCATION
                        )
                    }
                    builder.show()
                } else {
                    val builder = android.app.AlertDialog.Builder(this)
                    builder.setTitle("Functionality limited")
                    builder.setMessage("Since background location access has not " +
                            "been granted, this app will not be able to discover beacons " +
                            "in the background.  Please go to Settings -> Applications " +
                            "-> Permissions and grant background location access to this " +
                            "app.")
                    builder.setPositiveButton(android.R.string.ok, null)
                    builder.setOnDismissListener { }
                    builder.show()
                }
            }
        } else {
            if (!shouldShowRequestPermissionRationale(
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                requestPermissions(
                    arrayOf(
                        android.Manifest.permission.ACCESS_FINE_LOCATION,
                        android.Manifest.permission.ACCESS_BACKGROUND_LOCATION
                    ),
                    PERMISSION_REQUEST_FINE_LOCATION
                )
            } else {
                val builder = android.app.AlertDialog.Builder(this)
                builder.setTitle("Functionality limited")
                builder.setMessage("Since location access has not been granted, this app " +
                        "will not be able to discover beacons.  Please go to Settings -> " +
                        "Applications -> Permissions and grant location access to this app.")
                builder.setPositiveButton(android.R.string.ok, null)
                builder.setOnDismissListener { }
                builder.show()
            }
        }

        try {
            if (!BeaconManager.getInstanceForApplication(this).checkAvailability()) {
                val builder = android.app.AlertDialog.Builder(this)
                builder.setTitle("Bluetooth not enabled")
                builder.setMessage("Please enable bluetooth in settings and restart this " +
                        "application.")
                builder.setPositiveButton(android.R.string.ok, null)
                builder.setOnDismissListener {
                    finish()
                    exitProcess(0)
                }
                builder.show()
            }
        } catch (e: RuntimeException) {
            val builder = android.app.AlertDialog.Builder(this)
            builder.setTitle("Bluetooth LE not available")
            builder.setMessage("Sorry, this device does not support Bluetooth LE.")
            builder.setPositiveButton(android.R.string.ok, null)
            builder.setOnDismissListener {
                finish()
                exitProcess(0)
            }
            builder.show()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_REQUEST_FINE_LOCATION -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(
                        TAG,
                        "fine location permission granted"
                    )
                } else {
                    val builder = android.app.AlertDialog.Builder(this)
                    builder.setTitle("Functionality limited")
                    builder.setMessage("Since location access has not been granted, " +
                            "this app will not be able to discover beacons.")
                    builder.setPositiveButton(android.R.string.ok, null)
                    builder.setOnDismissListener { }
                    builder.show()
                }
                return
            }
            PERMISSION_REQUEST_BACKGROUND_LOCATION -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(
                        TAG,
                        "background location permission granted"
                    )
                } else {
                    val builder = android.app.AlertDialog.Builder(this)
                    builder.setTitle("Functionality limited")
                    builder.setMessage("Since background location access has not been granted, " +
                            "this app will not be able to discover beacons when in the background.")
                    builder.setPositiveButton(android.R.string.ok, null)
                    builder.setOnDismissListener { }
                    builder.show()
                }
                return
            }
        }
    }

    override fun onBeaconServiceConnect() {
        val rangeNotifier = RangeNotifier { beacons, _ ->
            this.beaconsDetected.clear()
            for (b in beacons) {
                Log.d(TAG, "Beacon info:\nuuid: ${b.id1}\n rssi: ${b.rssi}\nmajor: ${b.id2}\nminor: ${b.id3}\n")
                this.beaconsDetected.add(BeaconData(b.id1, b.id2, b.id3, b.rssi))
                beaconAdapter.notifyDataSetChanged() // last ressort solution , changes were not detectedd
            }
        }
        try {
            beaconManager.startRangingBeaconsInRegion(Region("blablabla",null, null, null))
            beaconManager.addRangeNotifier(rangeNotifier)
        } catch (e: RemoteException) {
        }
    }

    companion object{
        val TAG = this::class.simpleName
        private val PERMISSION_REQUEST_FINE_LOCATION = 1
        private val PERMISSION_REQUEST_BACKGROUND_LOCATION = 2
    }
}