package com.example.prog20082_groupproject.database

import androidx.lifecycle.LiveData

class BookingRepo(
    private val bookingDao: BookingDao
) {
    val allBooking : LiveData<List<Booking>> = bookingDao.getAllBooking()


    suspend fun insertAll(booking: Booking){
        bookingDao.insertAll(booking)
    }

    suspend fun deleteBooking(booking: Booking){
        bookingDao.deleteBooking(booking)
    }

    suspend fun getBookingByStudID(studentID:String) : Booking?{
        return bookingDao.getBookingByStudID(studentID)
    }
}