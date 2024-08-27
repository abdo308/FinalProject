package com.example.finalproject.network

object APIClient :RemoteDataSource{
    override suspend fun getRandomProduct(): RandomProduct {
        return API.retrofitService.getRandomProduct()
    }

    override suspend fun getMealBySearch(search: String): RandomProduct {
        return  API.retrofitService.getMealBySearch(search)
    }
}