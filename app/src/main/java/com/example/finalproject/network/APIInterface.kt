package com.example.finalproject.network

import retrofit2.http.GET

interface APIInterface {
    @GET("api/json/v1/1/random.php")
    suspend fun getRandomProduct():Meal
}