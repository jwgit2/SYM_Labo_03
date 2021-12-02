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
}