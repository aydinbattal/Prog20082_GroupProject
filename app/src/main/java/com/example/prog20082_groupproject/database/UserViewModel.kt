package com.example.prog20082_groupproject.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val userRepo : UserRepo
    private var matchedUser : MutableLiveData<User>?

    init {
        val userDao = LearninCommonsDatabase.getDatabase(application).userDao()
        userRepo = UserRepo(userDao)

        matchedUser = MutableLiveData()
    }

    fun insertAll(user: User) = viewModelScope.launch(Dispatchers.IO){
        userRepo.insertAll(user)
    }

    private fun getUserByLoginC(email: String,password: String) = viewModelScope.launch(Dispatchers.IO) {
        val user : User? = userRepo.getUserByLogin(email, password)
        matchedUser?.postValue(user)
    }
    fun getUserByLogin(email: String,password:String): MutableLiveData<User>?{
        getUserByLoginC(email, password)
        return matchedUser
    }

    private fun getUserByEmailC(email: String) = viewModelScope.launch(Dispatchers.IO) {
        val user: User? = userRepo.getUserByEmail(email)
        matchedUser?.postValue(user)
    }

    fun getUserByEmail(email: String) : MutableLiveData<User>?{
        getUserByEmailC(email)
        return matchedUser
    }



}