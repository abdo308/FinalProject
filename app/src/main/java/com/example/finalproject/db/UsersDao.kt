package com.example.finalproject.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.finalproject.models.User
import com.example.finalproject.models.UserFavourites
import com.example.finalproject.network.Meal

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
    @Query("select * from users_favourites where email=:email")
    suspend fun getDataUser(email:String?):UserFavourites
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(userFavourites: UserFavourites)

    @Query("update users_favourites set favourites=:list where email=:email")
    suspend fun updateData(email: String,list:MutableList<Meal>)


}