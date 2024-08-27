package com.example.finalproject.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.finalproject.models.User
import com.example.finalproject.models.UserFavourites

@Database(entities = [User::class,UserFavourites::class], version = 2, exportSchema = false)
@TypeConverters(MealConverter::class)
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