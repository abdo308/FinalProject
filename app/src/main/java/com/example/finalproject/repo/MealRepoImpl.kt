package com.example.finalproject.repo

import com.example.finalproject.network.Meal
import com.example.finalproject.network.RandomProduct
import com.example.finalproject.network.RemoteDataSource

class MealRepoImpl(private val remoteDataSource: RemoteDataSource): MealRepo {
    override suspend fun getRandom(): RandomProduct {
        return remoteDataSource.getRandomProduct()
    }
    override suspend fun getMealBySearch(search: String): List<Meal> {
        return remoteDataSource.getMealBySearch(search)
    }
}