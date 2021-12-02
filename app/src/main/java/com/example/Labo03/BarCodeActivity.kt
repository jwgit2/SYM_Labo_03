package com.example.Labo3

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.example.Labo03.R
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback

import com.journeyapps.barcodescanner.ScanContract

import com.journeyapps.barcodescanner.ScanOptions

import androidx.activity.result.ActivityResultLauncher
import com.google.zxing.client.android.Intents
import com.journeyapps.barcodescanner.ScanIntentResult
import android.graphics.Camera

import android.view.ViewGroup

import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.Fragment

import com.mikepenz.aboutlibraries.LibsBuilder

class BarCodeActivity : AppCompatActivity() {
    // Suivant l'exemple de https://github.com/journeyapps/zxing-android-embedded/tree/master/sample
    private val barcodeLauncher = registerForActivityResult(
        ScanContract()
    ) { result: ScanIntentResult ->
        if (result.contents == null) {
            val originalIntent = result.originalIntent
            if (originalIntent == null) {
                Log.d("MainActivity", "Cancelled scan")
                Toast.makeText(this@BarCodeActivity, "Cancelled", Toast.LENGTH_LONG).show()
            } else if (originalIntent.hasExtra(Intents.Scan.MISSING_CAMERA_PERMISSION)) {
                Log.d("MainActivity", "Cancelled scan due to missing camera permission")
                Toast.makeText(
                    this@BarCodeActivity,
                    "Cancelled due to missing camera permission",
                    Toast.LENGTH_LONG
                ).show()
            }
        } else {
            Log.d("MainActivity", "Scanned")
            Toast.makeText(
                this@BarCodeActivity,
                "Scanned: " + result.contents,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    /**
     * Sur création de l'activité
     */
    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_barcode)
    }
    fun scanBarcode(view: View?) {
        barcodeLauncher.launch(ScanOptions())
    }

    fun scanBarcodeInverted(view: View?) {
        val options = ScanOptions()
        options.addExtra(Intents.Scan.SCAN_TYPE, Intents.Scan.INVERTED_SCAN)
        barcodeLauncher.launch(options)
    }

    fun scanMixedBarcodes(view: View?) {
        val options = ScanOptions()
        options.addExtra(Intents.Scan.SCAN_TYPE, Intents.Scan.MIXED_SCAN)
        barcodeLauncher.launch(options)
    }

    fun scanBarcodeCustomLayout(view: View?) {
        val options = ScanOptions()
        options.captureActivity = AnyOrientationCaptureActivity::class.java
        options.setDesiredBarcodeFormats(ScanOptions.ONE_D_CODE_TYPES)
        options.setPrompt("Scan something")
        options.setOrientationLocked(false)
        options.setBeepEnabled(false)
        barcodeLauncher.launch(options)
    }

    fun scanPDF417(view: View?) {
        val options = ScanOptions()
        options.setDesiredBarcodeFormats(ScanOptions.PDF_417)
        options.setPrompt("Scan something")
        options.setOrientationLocked(false)
        options.setBeepEnabled(false)
        barcodeLauncher.launch(options)
    }


    fun scanBarcodeFrontCamera(view: View?) {
        val options = ScanOptions()
        options.setCameraId(Camera.CameraInfo.CAMERA_FACING_FRONT)
        barcodeLauncher.launch(options)
    }

    fun scanContinuous(view: View?) {
        val intent = Intent(this, ContinuousCaptureActivity::class.java)
        startActivity(intent)
    }

    fun scanToolbar(view: View?) {
        val options = ScanOptions().setCaptureActivity(ToolbarCaptureActivity::class.java)
        barcodeLauncher.launch(options)
    }





    fun scanWithTimeout(view: View?) {
        val options = ScanOptions()
        options.setTimeout(8000)
        barcodeLauncher.launch(options)
    }


    fun about(view: View?) {
        LibsBuilder().start(this)
    }

    /**
     * Sample of scanning from a Fragment
     */
    class ScanFragment : Fragment() {
        private val fragmentLauncher: ActivityResultLauncher<ScanOptions> =
            registerForActivityResult<ScanOptions, ScanIntentResult>(ScanContract(),
                ActivityResultCallback { result: ScanIntentResult ->
                    if (result.contents == null) {
                        Toast.makeText(getContext(), "Cancelled from fragment", Toast.LENGTH_LONG)
                            .show()
                    } else {
                        Toast.makeText(
                            getContext(),
                            "Scanned from fragment: " + result.contents,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                })

        fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View {
            val view: View = inflater.inflate(R.layout.fragment_scan, container, false)
            val scan: Button = view.findViewById(R.id.scan_from_fragment)
            scan.setOnClickListener { v: View? -> scanFromFragment() }
            return view
        }

        fun scanFromFragment() {
            fragmentLauncher.launch(ScanOptions())
        }
    }
}

class AnyOrientationCaptureActivity {

}

