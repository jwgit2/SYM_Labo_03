package com.example.Labo3.nfc

import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import com.example.Labo03.R

import android.content.Intent
import android.nfc.NfcAdapter
import android.os.Bundle
import android.util.Log
import android.content.IntentFilter
import android.app.PendingIntent
import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.os.CountDownTimer

/**
 * Project      : SYM_Labo3
 * Author       : Tailhades laurent, Werkle Johann, Zeller Corentin
 * Date         : 19.12.21
 * Description  : activité de scan d'un nfc
 */

class NFCActivity : AppCompatActivity() {
    val MIME_TEXT_PLAIN = "text/plain"
    val TAG = "Nfc Activity"
    var loggedIn = false;
    val FULL_AUTH = 10
    val MAX_AUTH = 6
    val MED_AUTH = 4
    val LOW_AUTH = 2
    private val NFC_TAG_STR = "test"

    private var accessValue = 0

    private lateinit var mNfcAdapter: NfcAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this)
        if (mNfcAdapter == null) {
            Toast.makeText(this, "Error : NFC is needed", Toast.LENGTH_LONG).show()
            finish()
            return
        }
        if (!mNfcAdapter.isEnabled)
            Toast.makeText(this, "Error : Please enable NFC", Toast.LENGTH_LONG).show()
        if(savedInstanceState == null)
            supportFragmentManager.beginTransaction().replace(R.id.nfc_fragment_holder,
                LoginFragment()).commit()
        setContentView(R.layout.activity_nfc)
    }

    override fun onResume() {
        super.onResume()
        setupForegroundDispatch()
    }

    override fun onPause() {
        super.onPause()
        if(mNfcAdapter != null)
            mNfcAdapter.disableForegroundDispatch(this)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        NfcTagDetected(intent)
    }

    private fun NfcTagDetected(intent: Intent?){
        if (intent != null) {
            if (NfcAdapter.ACTION_NDEF_DISCOVERED == intent.action) {
                val data = unserializeData(intent)?.get(0)
                if (data != null) {
                    val payload: String = String(data.payload)
                    if(payload.subSequence(3, payload.length) == NFC_TAG_STR) {
                        startTimer()
                    }
                }
            }
        }
    }

    private fun setupForegroundDispatch() {
        val intent = Intent(this.applicationContext, this.javaClass)
        intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        val pendingIntent = PendingIntent.getActivity(this.applicationContext, 0, intent, 0)
        val filters = arrayOfNulls<IntentFilter>(1)
        val techList = arrayOf<Array<String>>()

        // On souhaite être notifié uniquement pour les TAG au format NDEF
        filters[0] = IntentFilter()
        filters[0]!!.addAction(NfcAdapter.ACTION_NDEF_DISCOVERED)
        filters[0]!!.addCategory(Intent.CATEGORY_DEFAULT)
        try {
            filters[0]!!.addDataType(MIME_TEXT_PLAIN)
        } catch (e: IntentFilter.MalformedMimeTypeException) {
            Log.e(TAG, "MalformedMimeTypeException", e)
        }
        mNfcAdapter.enableForegroundDispatch(this, pendingIntent, filters, techList)
    }


    fun loggedIn() {
        loggedIn = true;
        supportFragmentManager.findFragmentById(R.id.nfc_fragment_holder)?.let {
            supportFragmentManager.beginTransaction().remove(
                it
            ).add(R.id.nfc_fragment_holder,
                LoggedFragment()
            ).commit()
        };
    }

    private fun unserializeData(intent: Intent?): Array<out NdefRecord>? {
        val ndefMessagesArray = intent!!.getParcelableArrayExtra(
            NfcAdapter.EXTRA_NDEF_MESSAGES
        )
        if (ndefMessagesArray != null) {
            val parsedMessage: NdefMessage = ndefMessagesArray[0] as NdefMessage
            return parsedMessage.records
        }
        return null
    }

    fun startTimer(){
        accessValue = FULL_AUTH;
        object: CountDownTimer((FULL_AUTH * 1000).toLong(), 1000) {
            //https://developer.android.com/reference/android/os/CountDownTimer
            override fun onTick(p0: Long) {
                if (accessValue > 0) {
                    accessValue -= 1
                }
            }
            override fun onFinish() {
                accessValue = 0
            }
        }.start()
    }
    fun getAccessValue(): Int {
        return accessValue
    }


}
