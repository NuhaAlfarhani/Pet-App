package com.example.petapp.data

import com.google.gson.annotations.SerializedName

data class PemesananDataWrapper(@SerializedName("data") val pemesananData: PemesananData)

data class PemesananData(
    val nama_pemesan: String,
    val total: Int,
    val jam_pemesan: String,
    val produk: Int
)