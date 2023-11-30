package com.example.petapp.service

import com.example.petapp.response.PenitipanRespon
import retrofit2.Call
import retrofit2.http.GET

interface PenitipanService {
    @GET("Penitipan")
    fun getData():Call<List<PenitipanRespon>>
}