package com.example.finalproject.network

import retrofit2.Response

object APIClient :RemoteDataSource{
    override suspend fun getRandomProduct(): RandomProduct {
        return API.retrofitService.getRandomProduct()
    }

    override suspend fun getMealBySearch(search: String): Response<RandomProduct> {
        return  API.retrofitService.getMealBySearch(search)
    }
}