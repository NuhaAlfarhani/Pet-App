package com.example.petapp.response

import com.google.gson.annotations.SerializedName

class PemesananRespon {
    var id: Int = 0
    @SerializedName("attributes")
    var attributes: PemesananAttributes = PemesananAttributes()
}

class PemesananAttributes {
    @SerializedName("nama_pemesan")
    var namaPemesan: String = ""
    @SerializedName("total")
    var total: Int = 0
    @SerializedName("jam_pemesan")
    var jamPemesan: String = ""
    @SerializedName("produks")
    val produks: Produk<ProdukRespon>? = null
    @SerializedName("createdAt")
    var createdAt: String = ""
    @SerializedName("updatedAt")
    var updatedAt: String = ""
    @SerializedName("publishedAt")
    var publishedAt: String = ""
}

data class Pemesanan<T>(
    @SerializedName("data")
    val data: T?
)