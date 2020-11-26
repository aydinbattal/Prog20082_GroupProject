package com.example.prog20082_groupproject.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.prog20082_groupproject.database.BookingRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class BookingViewModel(application: Application) : AndroidViewModel(application) {

    private val bookingRepo : BookingRepo
    var allBooking: LiveData<List<Booking>>
    private var matchedBooking : MutableLiveData<Booking>?

    init {
        val bookingDao = BookingDataBase.getDataBase(application).bookingDao()
        bookingRepo = BookingRepo(bookingDao)
        allBooking = bookingRepo.allBooking
        matchedBooking = MutableLiveData()
    }

    fun insertAll(booking: Booking) = viewModelScope.launch(Dispatchers.IO) {
        bookingRepo.insertAll(booking)
    }

    fun deleteBooking(booking: Booking) = viewModelScope.launch(Dispatchers.IO) {
        bookingRepo.deleteBooking(booking)
    }

    private fun getBookingByStudIdC(studentID :String) = viewModelScope.launch(Dispatchers.IO) {
        val booking : Booking? = bookingRepo.getBookingByStudID(studentID)
        matchedBooking?.postValue(booking)
    }
    fun getUserByStudentID(studentID: String) : MutableLiveData<Booking>?{
        getBookingByStudIdC(studentID)
        return matchedBooking
    }



}