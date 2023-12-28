package com.example.petapp.response

import com.google.gson.annotations.SerializedName

class ProdukRespon {
    var id: Int = 0
    @SerializedName("attributes")
    var attributes: ProdukAttributes = ProdukAttributes()
}

class ProdukAttributes {
    @SerializedName("nama_produk")
    var namaProduk: String = ""
    @SerializedName("harga")
    var harga: Int = 0
    @SerializedName("deskripsi_produk")
    var deskripsiProduk: String = ""
    @SerializedName("createdAt")
    var createdAt: String = ""
    @SerializedName("updatedAt")
    var updatedAt: String = ""
    @SerializedName("publishedAt")
    var publishedAt: String = ""
}

data class Produk<T>(
    @SerializedName("data")
    val data: T?
)