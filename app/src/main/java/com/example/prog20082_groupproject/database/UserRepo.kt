package com.example.prog20082_groupproject.database

class UserRepo(private val userDao:UserDao) {

    suspend fun insertAll(user: User){
        userDao.insertAll(user)
    }

    suspend fun getUserByEmail(email:String) : User?{
        return userDao.getUserByEmail(email)
    }

    suspend fun getUserByLogin(email: String,password: String) :User?{
        return userDao.getUserByLogin(email,password)
    }

    suspend fun updateUser(user: User){
        userDao.updateUser(user)
    }

    suspend fun deleteUserByEmail(email: String){
        userDao.deleteUserByEmail(email)
    }



}