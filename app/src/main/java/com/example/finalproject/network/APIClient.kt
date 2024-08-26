package com.example.finalproject.network

object APIClient :RemoteDataSource{
    override suspend fun getRandomProduct(): Meal {
        return API.retrofitService.getRandomProduct()
    }
}