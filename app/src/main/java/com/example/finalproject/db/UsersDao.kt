package com.example.finalproject.db

import android.provider.ContactsContract.CommonDataKinds.Email
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.finalproject.User

@Dao
interface UsersDao {

    @Query("SELECT * FROM users")
     fun getAllUsers() : List<User>

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String) : User?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNewUser(user: User)

    @Update
    suspend fun updateUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)
}