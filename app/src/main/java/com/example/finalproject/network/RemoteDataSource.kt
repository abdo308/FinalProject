package com.example.finalproject.network

import retrofit2.http.Query

interface RemoteDataSource {
    suspend fun getRandomProduct():Meal
    suspend fun getMealBySearch(
        @Query("s") search: String
    ) : List<Meal>

}