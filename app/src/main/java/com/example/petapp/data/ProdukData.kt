package com.example.petapp.data

import com.google.gson.annotations.SerializedName

data class ProdukDataWrapper(@SerializedName("data") val produkData: ProdukData)

data class ProdukData(
    val nama_produk: String,
    val harga: Int,
    val deskripsi_produk: String
)