package com.example.finalproject.repo

import com.example.finalproject.models.User
import com.example.finalproject.db.LocalDataSource
import com.example.finalproject.models.UserFavourites
import com.example.finalproject.network.Meal
import com.example.finalproject.network.RemoteDataSource

class ApplicationRepoImpl(
    private val localDataSource: LocalDataSource
    ) : ApplicationRepository {

    override suspend fun getUserByEmail(email: String): User? {
        return localDataSource.getUserByEmail(email)
    }

    override suspend fun insertNewUser(user: User) {
        localDataSource.insertNewUser(user)
    }


    override suspend fun getDataUser(email: String?): UserFavourites {
        return localDataSource.getDataUser(email)
    }

    override suspend fun addUser(userFavourite:UserFavourites) {
       localDataSource.addUser(userFavourite)
    }
    override suspend fun updateData(email: String,list:MutableList<Meal>){
        localDataSource.updateData(email,list)
    }

}