package com.example.Labo3

import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NdefMessage
import android.nfc.NfcAdapter
import android.nfc.NfcAdapter.ACTION_TECH_DISCOVERED
import android.nfc.Tag
import android.nfc.tech.IsoDep
import android.os.Bundle
import android.util.Log
import com.example.Labo03.R
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class NFCActivity : AppCompatActivity() {


    private lateinit var mNfcAdapter: NfcAdapter
    private lateinit var loginEdit: EditText
    private lateinit var passwordEdit: EditText
    private lateinit var cancelButton: Button
    private lateinit var validateButton: Button
    private var authenticationLevel: Int = 0

    //TODO : GERER LA DECREMENTATION DU NIVEAU D'AUTHENTIFICATION


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nfc)
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this)
        setButtonsBehaviours()

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
                //TODO : GERER LE CONTENU DU TAG
                    val rawMessages = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)
            var ndefMsg = rawMessages?.get(0) as NdefMessage
            var ndefRecord = ndefMsg.records[0]
            Log.d(TAG, "La balise NFC contient : " + ndefRecord.toMimeType())
            authenticationLevel = 10;
        }
    }

    private fun setButtonsBehaviours() {
        loginEdit = findViewById(R.id.login)
        passwordEdit = findViewById(R.id.password)
        cancelButton = findViewById(R.id.cancel_button)
        validateButton = findViewById(R.id.validate_button)

        //mise en place des événements
        cancelButton.setOnClickListener {
            //on va vider les champs de la page de login lors du clique sur le bouton Cancel
            loginEdit.text?.clear()
            passwordEdit.text?.clear()
            // on annule les éventuels messages d'erreur présents sur les champs de saisie
            loginEdit.error = null
            passwordEdit.error = null
            return@setOnClickListener
        }

        validateButton.setOnClickListener {
            validateButtonAction()
            return@setOnClickListener
        }
    }
    fun validateButtonAction() {
        //on réinitialise les messages d'erreur
        loginEdit.error = null
        passwordEdit.error = null

        //on récupère le contenu de deux champs dans des variables de type String
       if( loginEdit.text.toString().equals(LOGIN) and passwordEdit.text.toString().equals(PASSWORD)){
           Toast.makeText(applicationContext, "u done it gg", Toast.LENGTH_SHORT).show()
           //TODO : CHANGER LA VUE ET ALLER SUR LE CHOIX DE SECURITE
       }
    }

    companion object {
        private const val AUTHENTICATE_MAX = 10
        private const val AUTHENTICATE_MEDIUM = 6
        private const val AUTHENTICATE_LOW = 2
        private const val TAG: String = "NFCActivity"
        private const val LOGIN: String = "test"
        private const val PASSWORD: String = "test"
    }

}

