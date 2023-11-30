package com.example.petapp.service

import com.example.petapp.data.RegisterData
import com.example.petapp.response.LoginRespon
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterService {
    @POST("auth/local/register")
    fun saveData(@Body body: RegisterData) : Call<LoginRespon>
}