package com.example.Labo3

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.widget.*
import com.example.Labo03.R
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.zxing.BarcodeFormat
import com.google.zxing.ResultPoint
import com.journeyapps.barcodescanner.*

/**
 * Project      : SYM_Labo3
 * Author       : Tailhades laurent, Werkle Johann, Zeller Corentin
 * Date         : 19.12.21
 * Description  : activité permettant de scanenr un barcode/qrcode
 */


class BarCodeActivity : AppCompatActivity() {

    private lateinit var barcodeView: DecoratedBarcodeView
    private lateinit var imageView: ImageView
    private var lastText = ""

    // Suivant l'exemple de https://github.com/journeyapps/zxing-android-embedded/tree/master/sample
    // et https://github.com/journeyapps/zxing-android-embedded/blob/master/sample/src/main/java/example/zxing/ContinuousCaptureActivity.java

    private val callback: BarcodeCallback = object : BarcodeCallback {
        override fun barcodeResult(result: BarcodeResult) {
            // check if already scanned or empty
            if (result.text == lastText || result.text == "") {
                return
            }
            lastText = result.text
            barcodeView.setStatusText(result.text)

            // Add image and resultPoint of scanned barcode
            imageView = findViewById(R.id.img_code_scanned)
            imageView.setImageBitmap(result.getBitmapWithResultPoints(Color.GREEN))
        }

        override fun possibleResultPoints(resultPoints: List<ResultPoint>) {}
    }


    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_barcode)

        // Ask for permission if never accepted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 1)
        }

        barcodeView = findViewById(R.id.code_scanner)

        val formats: Collection<BarcodeFormat> =
            listOf(BarcodeFormat.QR_CODE, BarcodeFormat.CODE_39)
        barcodeView.barcodeView.decoderFactory = DefaultDecoderFactory(formats)
        barcodeView.initializeFromIntent(intent)
        barcodeView.decodeContinuous(callback)
    }

    override fun onResume() {
        super.onResume()
        barcodeView.resume()
    }

    override fun onPause() {
        super.onPause()
        barcodeView.pause()
    }
}
