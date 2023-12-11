package com.example.petapp.service

import com.example.petapp.response.Artikel
import com.example.petapp.response.ArtikelRespon
import retrofit2.Call
import retrofit2.http.GET

interface ArtikelService {
    @GET("artikels")
    fun getData() : Call<Artikel<List<ArtikelRespon>>>
}