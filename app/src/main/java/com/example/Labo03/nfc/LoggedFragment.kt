package com.example.Labo3.nfc

import com.example.Labo03.R
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class LoggedFragment : Fragment() {

    private lateinit var nfcAuthCountdown: TextView
    private lateinit var maxBtn: Button
    private lateinit var medBtn: Button
    private lateinit var lowBtn: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_logged, container, false)
    }

    override fun onStart() {
        super.onStart()
        val act = activity as NFCActivity
        nfcAuthCountdown = view?.findViewById(R.id.auth_countdown)!!
        maxBtn = view?.findViewById(R.id.max_security_btn)!!
        maxBtn.setOnClickListener{
            showAuthenticationToast(act.MED_AUTH, act)
        }
        medBtn = view?.findViewById(R.id.medium_security_btn)!!
        medBtn.setOnClickListener {
            showAuthenticationToast(act.LOW_AUTH, act)
        }
        lowBtn = view?.findViewById(R.id.low_security_btn)!!
        lowBtn.setOnClickListener {
            showAuthenticationToast(0, act)
        }
        nfcAuthCountdown.postDelayed(mUpdate, 0)
    }

    private val mUpdate: Runnable = object : Runnable {
        override fun run() {
            if (activity == null)
                return
            val act =  activity as NFCActivity
            updateSecurity(act)
            nfcAuthCountdown.setText("Countdown: " + act.getAuthenticationLevel().toString())
            nfcAuthCountdown.postDelayed(this, 1000);
        }
    }

    private fun updateSecurity(act: NFCActivity){
        val authLevel = act.getAuthenticationLevel()
        if(act.MED_AUTH < authLevel){
            //nfcAuthStatus.setText("MAX")
        } else if((act.LOW_AUTH < authLevel) and (authLevel <= act.MED_AUTH)){
            //nfcAuthStatus.setText("MED")
        } else if((0 < authLevel) and (authLevel <= act.LOW_AUTH)){
           // nfcAuthStatus.setText("LOW")
        } else {
           // nfcAuthStatus.setText("SCAN YOUR TAG")
        }
    }

    private fun showAuthenticationToast(threshold: Int, act: NFCActivity){
        if(act.getAuthenticationLevel() > threshold){
            Toast.makeText(this.context, "You shall not pass", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this.context, "You are the chosen one", Toast.LENGTH_SHORT).show()
        }
    }
}