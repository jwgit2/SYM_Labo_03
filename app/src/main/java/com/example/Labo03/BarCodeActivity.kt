package com.example.Labo3

import android.content.Intent
import android.os.Bundle
import com.example.Labo03.R
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class BarCodeActivity : AppCompatActivity() {

    /**
     * Sur création de l'activité
     */
    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_barcode)
    }
}