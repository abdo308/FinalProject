package com.example.finalproject.network

class MealRepoImpl(private val remoteDataSource: RemoteDataSource): MealRepo  {
    override suspend fun getRandom():RandomProduct{
        return remoteDataSource.getRandomProduct()
    }
}