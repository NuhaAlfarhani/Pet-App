package com.example.petapp.service

import com.example.petapp.data.ProdukDataWrapper
import com.example.petapp.response.Produk
import com.example.petapp.response.ProdukRespon
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ProdukService {
    @POST("produks")
    fun createProduk(@Body produkData: ProdukDataWrapper): Call<ProdukRespon>

    @PUT("produks/{id}")
    fun save(@Path("id") id: String?, @Body body: ProdukDataWrapper): Call<ProdukRespon>

    @GET("produks")
    fun getData() : Call<Produk<List<ProdukRespon>>>

    @DELETE("produks/{id}")
    fun delete(@Path("id") id : Int) : Call<ProdukRespon>
}