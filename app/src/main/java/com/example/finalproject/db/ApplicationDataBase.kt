package com.example.finalproject.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.finalproject.User

@Database(entities = [User::class], version = 1)
abstract class ApplicationDataBase : RoomDatabase() {

    abstract fun userDao() : UsersDao

    companion object{
        @Volatile
        private var instance : ApplicationDataBase? = null

        fun getInstance(context: Context) :ApplicationDataBase{
            return instance ?: synchronized(this){
                instance ?:Room.databaseBuilder(
                    context,
                    ApplicationDataBase::class.java,
                    "application_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also {
                        createdInstance ->
                        instance = createdInstance
                    }
            }
        }
    }
}