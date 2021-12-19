package com.example.Labo3.nfc

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.Labo03.R
import android.widget.EditText
import android.widget.Toast

class LoginFragment : Fragment() {

    private lateinit var login: EditText
    private lateinit var password : EditText
    private lateinit var loginBtn: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onStart(){
        super.onStart()
        login = view?.findViewById(R.id.login_login_tv)!!
        password = view?.findViewById(R.id.login_password_tv)!!
        loginBtn = view?.findViewById(R.id.loggin_connection_btn)!!

        loginBtn.setOnClickListener {
            if((login.text.toString() == "test") && (password.text.toString() == "test")) {
                Toast.makeText(this.context, "U made it gg!", Toast.LENGTH_LONG).show()
                (activity as NFCActivity).loggedIn()
            } else {
                login.setText("")
                password.setText("")
                Toast.makeText(this.context, "Try again", Toast.LENGTH_LONG).show()
            }
        }
    }
}
