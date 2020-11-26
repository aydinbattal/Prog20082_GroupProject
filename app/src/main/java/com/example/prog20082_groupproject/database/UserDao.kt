package com.example.prog20082_groupproject.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertAll(vararg users:User)

    @Query("SELECT*FROM Users WHERE email LIKE:email")
    fun getUserByEmail(email: String) : User?

    @Query("SELECT * FROM Users WHERE email LIKE :email AND password LIKE :password")
    fun getUserByLogin(email: String,password:String): User?

    @Update
    suspend fun updateUser(user: User)

    @Query("DELETE FROM Users WHERE email LIKE :email")
    fun deleteUserByEmail(email: String)

    @Query("SELECT * FROM Users")
    fun getAllUsers(): LiveData<List<User>>


}