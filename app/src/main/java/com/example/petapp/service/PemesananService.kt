package com.example.petapp.service

import com.example.petapp.data.PemesananDataWrapper
import com.example.petapp.response.Pemesanan
import com.example.petapp.response.PemesananRespon
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface PemesananService {
    @POST("pemesanans")
    fun createPemesanan(@Body pemesananData: PemesananDataWrapper): Call<PemesananRespon>

    @PUT("pemesanans/{id}")
    fun save(@Path("id") id: String?, @Body body: PemesananDataWrapper): Call<PemesananRespon>

    @GET("pemesanans")
    fun getData(@Query("filters[namaPemesan][\$contains]") search: String?,@Query("populate") queryParameter: String): Call<Pemesanan<List<PemesananRespon>>>

    @DELETE("pemesanans/{id}")
    fun delete(@Path("id") id: Int): Call<PemesananRespon>

}