package com.example.finalproject.network

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object API {
    private val gson=GsonBuilder().serializeNulls().create()
    val retrofit=Retrofit.Builder()
        .baseUrl("https://www.themealdb.com/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
    val retrofitService:APIInterface by lazy{
        retrofit.create(APIInterface::class.java)
    }
}