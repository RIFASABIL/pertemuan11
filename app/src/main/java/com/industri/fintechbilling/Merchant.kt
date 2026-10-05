package com.industri.fintechbilling

import com.google.gson.annotations.SerializedName

data class Merchant(
    @field:SerializedName("merchant_id")
    val merchantId: String,

    @field:SerializedName("merchant_name")
    val merchantName: String,

    @field:SerializedName("city_location")
    val cityLocation: String,

    @field:SerializedName("is_verified")
    val isVerified: Boolean = false
)
