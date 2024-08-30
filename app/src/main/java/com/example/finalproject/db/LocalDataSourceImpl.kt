package com.example.finalproject.db

import android.content.Context
import com.example.finalproject.models.User
import com.example.finalproject.models.UserFavourites
import com.example.finalproject.network.Meal

class LocalDataSourceImpl(context: Context) :LocalDataSource {
     var usersDao : UsersDao

    init {
        val db = ApplicationDataBase.getInstance(context)
        usersDao = db.userDao()
    }
    override suspend fun getUserByEmail(email: String): User? {
        return usersDao.getUserByEmail(email)
    }

    override suspend fun insertNewUser(user: User) {
        usersDao.insertNewUser(user)
    }

    override suspend fun getDataUser(email: String?): UserFavourites {
        return usersDao.getDataUser(email)
    }

    override suspend fun addUser(userFavourites: UserFavourites) {
        usersDao.addUser(userFavourites)
    }
    override suspend fun updateData(email: String,list:MutableList<Meal>){
        usersDao.updateData(email,list)
    }

}