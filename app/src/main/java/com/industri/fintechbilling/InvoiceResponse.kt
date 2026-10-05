package com.industri.fintechbilling

import com.google.gson.annotations.SerializedName

data class Customer(
    @field:SerializedName("customer_id")
    val customerId: String,

    @field:SerializedName("full_name")
    val fullName: String,

    @field:SerializedName("phone")
    val phone: String
)

data class InvoiceItem(
    @field:SerializedName("item_id")
    val itemId: String,

    @field:SerializedName("item_name")
    val itemName: String,

    @field:SerializedName("qty")
    val qty: Int,

    @field:SerializedName("unit_price")
    val unitPrice: Double,

    @field:SerializedName("subtotal")
    val subtotal: Double
)

data class BillingSummary(
    @field:SerializedName("subtotal_amount")
    val subtotalAmount: Double,

    @field:SerializedName("discount_amount")
    val discountAmount: Double = 0.0,

    @field:SerializedName("tax_ppn_11")
    val taxPpn11: Double,

    @field:SerializedName("service_fee")
    val serviceFee: Double,

    @field:SerializedName("total_paid")
    val totalPaid: Double,

    @field:SerializedName("voucher_code")
    val voucherCode: String? = null,

    @field:SerializedName("voucher_discount_percent")
    val voucherDiscountPercent: Int? = null
)

data class InvoiceResponse(
    @field:SerializedName("invoice_number")
    val invoiceNumber: String,

    @field:SerializedName("transaction_date")
    val transactionDate: String,

    @field:SerializedName("payment_status")
    val paymentStatus: String,

    @field:SerializedName("payment_method")
    val paymentMethod: String,

    @field:SerializedName("merchant")
    val merchant: Merchant,

    @field:SerializedName("customer")
    val customer: Customer,

    @field:SerializedName("items")
    val items: List<InvoiceItem>,

    @field:SerializedName("summary")
    val summary: BillingSummary
)
