package com.example.petapp.service

import com.example.petapp.response.ArtikelRespon
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.GET

interface ArtikelService {
    @GET("artikels")
    fun getData() : Call<List<ArtikelRespon>>

}

data class ArtikelData(
    val data: ArtikelBody
)

data class ArtikelBody(
    @SerializedName("judul_artikel")
    val judulArtikel: String,
    val id: Int
)