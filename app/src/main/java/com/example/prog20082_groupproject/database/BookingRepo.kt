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
    suspend fun updateBooking(booking: Booking){
        bookingDao.updateBooking(booking)
    }

    suspend fun getBookingByCampusNandRoomN(campusName:String,roomNumber:String) : Booking?{
        return bookingDao.getBookingByCampusNandRoomN(campusName,roomNumber)
    }
}