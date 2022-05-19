package org.prater.prater.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://hidden-wave-77933.herokuapp.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api: PraterApi = retrofit.create(PraterApi::class.java)
}