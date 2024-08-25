package com.example.finalproject.db

import android.content.Context
import com.example.finalproject.User

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
}