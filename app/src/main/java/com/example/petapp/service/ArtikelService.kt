package com.example.petapp.service

import com.example.petapp.data.RegisterData
import com.example.petapp.data.UpdateData
import com.example.petapp.response.ArtikelRespon
import com.example.petapp.response.LoginRespon
import com.example.petapp.response.UserRespon
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ArtikelService {
    @GET("artikels")
    fun getData() : Call<List<ArtikelRespon>>

}