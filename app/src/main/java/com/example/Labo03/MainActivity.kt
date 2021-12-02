package com.example.Labo3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.Labo03.R


class MainActivity : AppCompatActivity() {

    private lateinit var switchToActivityBarCode: Button
    private lateinit var switchToActivityiBeacon: Button
    private lateinit var switchToActivityNFC: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        switchToActivityBarCode = findViewById(R.id.main_barcode_btn)
        switchToActivityBarCode.setOnClickListener {
            val intent = Intent(this@MainActivity, BarCodeActivity::class.java)
            startActivity(intent)
        }

        switchToActivityiBeacon = findViewById(R.id.main_iBeacon_btn)
        switchToActivityiBeacon.setOnClickListener {
            val intent = Intent(this@MainActivity, iBeaconActivity::class.java)
            startActivity(intent)
        }

        switchToActivityNFC = findViewById(R.id.main_nfc_btn)
        switchToActivityNFC.setOnClickListener {
            val intent = Intent(this@MainActivity, NFCActivity::class.java)
            startActivity(intent)
        }
    }
}