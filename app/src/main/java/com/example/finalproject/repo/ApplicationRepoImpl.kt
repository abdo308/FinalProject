package com.example.finalproject.repo

import com.example.finalproject.models.User
import com.example.finalproject.db.LocalDataSource

class ApplicationRepoImpl(private val localDataSource: LocalDataSource) : ApplicationRepository {

    override suspend fun getUserByEmail(email: String): User? {
        return localDataSource.getUserByEmail(email)
    }

    override suspend fun insertNewUser(user: User) {
        localDataSource.insertNewUser(user)
    }
}