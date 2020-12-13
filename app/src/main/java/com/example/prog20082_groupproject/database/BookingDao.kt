package com.example.prog20082_groupproject.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface BookingDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertAll(vararg bookings:Booking)

    @Update
    fun updateBooking(booking: Booking)

    @Delete
    fun deleteBooking(booking: Booking)

    @Query("SELECT * FROM Bookings")
    fun getAllBooking() : LiveData<List<Booking>>

    @Query("SELECT * FROM Bookings WHERE campusName LIKE :campusName AND roomNumber LIKE :roomNumber")
    fun getBookingByCampusNandRoomN(campusName:String, roomNumber:String) : Booking?
}