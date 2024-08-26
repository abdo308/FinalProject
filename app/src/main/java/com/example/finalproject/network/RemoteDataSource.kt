package com.example.finalproject.network

interface RemoteDataSource {
    suspend fun getRandomProduct():Meal

}