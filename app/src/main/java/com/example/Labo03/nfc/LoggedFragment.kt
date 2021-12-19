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

/**
 * Project      : SYM_Labo3
 * Author       : Tailhades laurent, Werkle Johann, Zeller Corentin
 * Date         : 19.12.21
 * Description  : fragment des boutons d'authentification de l'activié NFC
 */

class LoggedFragment : Fragment() {

    private lateinit var nfcTimer: TextView
    private lateinit var maxBtn: Button
    private lateinit var medBtn: Button
    private lateinit var minBtn: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_logged, container, false)
    }

    override fun onStart() {
        super.onStart()
        val nfcActivity = activity as NFCActivity
        nfcTimer = view?.findViewById(R.id.auth_countdown)!!
        maxBtn = view?.findViewById(R.id.max_security_btn)!!
        maxBtn.setOnClickListener{
            if(nfcActivity.getAccessValue() < nfcActivity.MAX_AUTH){
                Toast.makeText(this.context, "You shall not pass", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this.context, "You are the chosen one", Toast.LENGTH_SHORT).show()
            }
        }
        medBtn = view?.findViewById(R.id.medium_security_btn)!!
        medBtn.setOnClickListener {
            if(nfcActivity.getAccessValue() < nfcActivity.MED_AUTH){
                Toast.makeText(this.context, "You shall not pass", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this.context, "You are the chosen one", Toast.LENGTH_SHORT).show()
            }
        }
        minBtn = view?.findViewById(R.id.low_security_btn)!!
        minBtn.setOnClickListener {
            if(nfcActivity.getAccessValue() < nfcActivity.LOW_AUTH){
                Toast.makeText(this.context, "You shall not pass", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this.context, "You are the chosen one", Toast.LENGTH_SHORT).show()
            }
        }
        nfcTimer.postDelayed(mUpdate, 0)
    }

    private val mUpdate: Runnable = object : Runnable {
        override fun run() {
            if (activity == null)
                return
            val nfcActivity =  activity as NFCActivity
            updateSecurity(nfcActivity)
            nfcTimer.setText("Timer: " + nfcActivity.getAccessValue().toString())
            nfcTimer.postDelayed(this, 1000);
        }
    }

    private fun updateSecurity(act: NFCActivity){
        val accessValue = act.getAccessValue()

        if (accessValue < act.LOW_AUTH){
            maxBtn.visibility = View.GONE
            medBtn.visibility = View.GONE
            minBtn.visibility = View.GONE
        } else if (accessValue < act.MED_AUTH){
            maxBtn.visibility = View.GONE
            medBtn.visibility = View.GONE
            minBtn.visibility = View.VISIBLE
        } else if (accessValue < act.MAX_AUTH){
            maxBtn.visibility = View.GONE
            medBtn.visibility = View.VISIBLE
            minBtn.visibility = View.VISIBLE
        } else {
            maxBtn.visibility = View.VISIBLE
            medBtn.visibility = View.VISIBLE
            minBtn.visibility = View.VISIBLE
        }
    }
}
