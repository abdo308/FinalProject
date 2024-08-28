package com.example.finalproject.network

import retrofit2.Response
import retrofit2.http.Query

interface RemoteDataSource {
    suspend fun getRandomProduct():RandomProduct
    suspend fun getMealBySearch(
        @Query("s") search: String
    ) : Response<RandomProduct>

}