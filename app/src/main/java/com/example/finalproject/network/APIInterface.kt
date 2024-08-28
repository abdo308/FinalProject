package com.example.finalproject.network

import android.icu.text.StringSearch
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface APIInterface {
    @GET("api/json/v1/1/random.php")
    suspend fun getRandomProduct():RandomProduct

    @GET("api/json/v1/1/search.php")
    suspend fun getMealBySearch(
        @Query("s") search: String
    ) : Response<RandomProduct>


}