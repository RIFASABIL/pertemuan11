package com.industri.fintechbilling

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {

    private val merchantJson = """
        {
          "merchant_id": "MCH-99201",
          "merchant_name": "Mega Elektrindo Ritel",
          "city_location": "Bandung",
          "is_verified": true
        }
    """.trimIndent()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnParseBasic = findViewById<Button>(R.id.btnParseBasic)
        val tvOutputBasic = findViewById<TextView>(R.id.tvOutputBasic)
        val btnOpenInvoice = findViewById<Button>(R.id.btnOpenInvoice)

        btnParseBasic.setOnClickListener {
            val gson = Gson()
            val merchant = gson.fromJson(merchantJson, Merchant::class.java)

            val verifiedStatus = if (merchant.isVerified) "RESMI (Ya)" else "TIDAK (Tidak)"

            val resultText = """
                BERHASIL DI-PARSING:

                • ID Mitra : ${merchant.merchantId}
                • Nama Toko: ${merchant.merchantName}
                • Kota : ${merchant.cityLocation}
                • Terverifikasi: $verifiedStatus
            """.trimIndent()

            tvOutputBasic.text = resultText
        }

        btnOpenInvoice.setOnClickListener {
            val intent = Intent(this, InvoiceActivity::class.java)
            startActivity(intent)
        }
    }
}
