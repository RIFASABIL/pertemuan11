package com.industri.fintechbilling

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Locale

class InvoiceActivity : AppCompatActivity() {

    private val invoiceJson = """
        {
          "invoice_number": "INV-2026-FT9012",
          "transaction_date": "2026-09-23 10:15:00",
          "payment_status": "PAID_SETTLED",
          "payment_method": "QRIS_BCA",
          "merchant": {
            "merchant_id": "MCH-99201",
            "merchant_name": "Mega Elektrindo Ritel",
            "city_location": "Bandung",
            "is_verified": true
          },
          "customer": {
            "customer_id": "CUST-4412",
            "full_name": "Ahmad Fauzi",
            "phone": "081298765432"
          },
          "items": [
            {
              "item_id": "ITM-01",
              "item_name": "Kabel Type-C 65W Fast Charging",
              "qty": 2,
              "unit_price": 75000.0,
              "subtotal": 150000.0
            },
            {
              "item_id": "ITM-02",
              "item_name": "Adaptor GaN Charger 3-Port 100W",
              "qty": 1,
              "unit_price": 320000.0,
              "subtotal": 320000.0
            },
            {
              "item_id": "ITM-03",
              "item_name": "Mouse Wireless Silent Click Ergonomis",
              "qty": 1,
              "unit_price": 130000.0,
              "subtotal": 130000.0
            }
          ],
          "summary": {
            "subtotal_amount": 600000.0,
            "discount_amount": 50000.0,
            "tax_ppn_11": 60500.0,
            "service_fee": 2500.0,
            "total_paid": 613000.0,
            "voucher_code": null,
            "voucher_discount_percent": null
          }
        }
    """.trimIndent()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_invoice)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.invoiceScrollView)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        displayInvoiceData()
    }

    private fun displayInvoiceData() {
        val gson = Gson()
        val invoiceResponse = gson.fromJson(invoiceJson, InvoiceResponse::class.java)

        val tvInvoiceNo = findViewById<TextView>(R.id.tvInvoiceNo)
        val tvStatusBadge = findViewById<TextView>(R.id.tvStatusBadge)
        val tvTransactionDate = findViewById<TextView>(R.id.tvTransactionDate)
        val tvMerchantName = findViewById<TextView>(R.id.tvMerchantName)
        val tvCustomerName = findViewById<TextView>(R.id.tvCustomerName)
        val rvInvoiceItems = findViewById<RecyclerView>(R.id.rvInvoiceItems)
        val tvSubtotalAmt = findViewById<TextView>(R.id.tvSubtotalAmt)
        val tvDiscountAmt = findViewById<TextView>(R.id.tvDiscountAmt)
        val tvTaxAmt = findViewById<TextView>(R.id.tvTaxAmt)
        val tvFeeAmt = findViewById<TextView>(R.id.tvFeeAmt)
        val tvTotalPaid = findViewById<TextView>(R.id.tvTotalPaid)
        val tvVoucherInfo = findViewById<TextView>(R.id.tvVoucherInfo)

        val currencyFormat = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("id-ID"))

        // Header & Info
        tvInvoiceNo.text = invoiceResponse.invoiceNumber
        tvStatusBadge.text = invoiceResponse.paymentStatus
        tvTransactionDate.text = formatTransactionDate(invoiceResponse.transactionDate)

        // Merchant
        tvMerchantName.text = invoiceResponse.merchant.merchantName

        // Customer
        tvCustomerName.text = invoiceResponse.customer.fullName

        // Items RecyclerView
        rvInvoiceItems.layoutManager = LinearLayoutManager(this)
        rvInvoiceItems.adapter = InvoiceItemAdapter(invoiceResponse.items)

        // Payment Summary
        tvSubtotalAmt.text = currencyFormat.format(invoiceResponse.summary.subtotalAmount)
        tvDiscountAmt.text = "-${currencyFormat.format(invoiceResponse.summary.discountAmount)}"
        tvTaxAmt.text = currencyFormat.format(invoiceResponse.summary.taxPpn11)
        tvFeeAmt.text = currencyFormat.format(invoiceResponse.summary.serviceFee)
        tvTotalPaid.text = currencyFormat.format(invoiceResponse.summary.totalPaid)

        // Promo / Voucher (Tugas 1)
        val voucherCode = invoiceResponse.summary.voucherCode
        val voucherDiscount = invoiceResponse.summary.voucherDiscountPercent

        if (voucherCode == null) {
            tvVoucherInfo.text = "Tidak menggunakan promo"
        } else {
            tvVoucherInfo.text = "Voucher: $voucherCode (Diskon $voucherDiscount%)"
        }
    }

    private fun formatTransactionDate(dateString: String): String {
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.forLanguageTag("id-ID"))
            val date = inputFormat.parse(dateString)
            val outputFormat = SimpleDateFormat("EEEE, dd MMMM yyyy - 'Pukul' HH:mm 'WIB'", Locale.forLanguageTag("id-ID"))
            if (date != null) outputFormat.format(date) else dateString
        } catch (e: Exception) {
            dateString
        }
    }
}
