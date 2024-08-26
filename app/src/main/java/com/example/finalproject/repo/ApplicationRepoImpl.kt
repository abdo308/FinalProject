package com.example.finalproject.repo

import com.example.finalproject.models.User
import com.example.finalproject.db.LocalDataSource
import com.example.finalproject.network.Meal
import com.example.finalproject.network.RemoteDataSource

class ApplicationRepoImpl(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
    ) : ApplicationRepository {

    override suspend fun getUserByEmail(email: String): User? {
        return localDataSource.getUserByEmail(email)
    }

    override suspend fun insertNewUser(user: User) {
        localDataSource.insertNewUser(user)
    }

    override suspend fun getMealBySearch(search: String): List<Meal> {
        return remoteDataSource.getMealBySearch(search)
    }
}