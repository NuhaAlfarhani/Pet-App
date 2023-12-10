package com.example.petapp.response

import com.google.gson.annotations.SerializedName

class ArtikelRespon {
    var id: Int = 0
    @SerializedName("attributes")
    var attributes: ArtikelAttributes = ArtikelAttributes()
}

class ArtikelAttributes {
    @SerializedName("judul_artikel")
    var judulArtikel: String = ""
    @SerializedName("deskripsi_artikel")
    var deskripsiArtikel: String = ""
    @SerializedName("tgl_publikasi")
    var tglPublikasi: String = ""
    @SerializedName("createdAt")
    var createdAt: String = ""
    @SerializedName("updatedAt")
    var updatedAt: String = ""
    @SerializedName("publishedAt")
    var publishedAt: String = ""
}

data class Artikel<T>(
    @SerializedName("data")
    val data: T?
)