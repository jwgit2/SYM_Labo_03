package com.example.Labo3

import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NfcAdapter
import android.nfc.NfcAdapter.ACTION_TECH_DISCOVERED
import android.nfc.Tag
import android.nfc.tech.IsoDep
import android.os.Bundle
import com.example.Labo03.R
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class NFCActivity : AppCompatActivity() {
    private lateinit var mNfcAdapter : NfcAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this)

    }

    override fun onResume() {
        super.onResume()

        NfcAdapter.getDefaultAdapter(this)?.let { nfcAdapter ->
            // An Intent to start your current Activity. Flag to singleTop
            // to imply that it should only be delivered to the current
            // instance rather than starting a new instance of the Activity.
            val launchIntent = Intent(this, this.javaClass)
            launchIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)

            // Supply this launch intent as the PendingIntent, set to cancel
            // one if it's already in progress. It never should be.
            val pendingIntent = PendingIntent.getActivity(
                this, 0, launchIntent, PendingIntent.FLAG_CANCEL_CURRENT
            )

            // Define your filters and desired technology types
            val filters = arrayOf(IntentFilter(ACTION_TECH_DISCOVERED))
            val techTypes = arrayOf(arrayOf(IsoDep::class.java.name))

            // And enable your Activity to receive NFC events. Note that there
            // is no need to manually disable dispatch in onPause() as the system
            // very strictly performs this for you. You only need to disable
            // dispatch if you don't want to receive tags while resumed.
            nfcAdapter.enableForegroundDispatch(
                this, pendingIntent, filters, techTypes
            )
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)

        if (NfcAdapter.ACTION_TECH_DISCOVERED == intent.action) {
            val tag = intent.getParcelableExtra<Tag>(NfcAdapter.EXTRA_TAG)
            IsoDep.get(tag)?.let { isoDepTag ->
                // Handle the tag here
            }
        }
    }

}