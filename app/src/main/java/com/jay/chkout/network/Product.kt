package com.jay.chkout.network

import com.squareup.moshi.Json

data class Product(
    val id: Int,
    @Json(name = "title") val productTitle: String,
    val price : Double,
    val description: String,
    val category: String,
    @Json(name = "image") val imageUrl : String
)