package com.example.prog20082_groupproject.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertAll(vararg users:User)

    @Query("SELECT*FROM Users WHERE email LIKE:email")
    fun getUserByEmail(email: String) : User?

    @Query("SELECT * FROM Users WHERE email LIKE :email AND password LIKE :password")
    fun getUserByLogin(email: String,password:String): User?


}