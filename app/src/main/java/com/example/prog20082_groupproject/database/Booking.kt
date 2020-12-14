package com.example.prog20082_groupproject.database

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "Bookings",primaryKeys = arrayOf("studentID"))
data class Booking(
    @ColumnInfo(name = "roomNumber") var roomNumber: String?,
    @ColumnInfo(name = "campusName") var campusName: String?,
    @ColumnInfo(name = "studentID") var studentID: String,
    @ColumnInfo(name = "studentAmount") var studentAmount: Int?,
    @ColumnInfo(name = "duration") var duration: String?,
    @ColumnInfo(name = "timeSlot") var timeSlot: String?,
    @ColumnInfo(name = "bookingDate") var bookingDate: String?,
    @ColumnInfo(name = "tempCampus") var tempCampus: String?,
    @ColumnInfo(name = "tempRoom") var tempRoom: String?
) {
    constructor() : this(
        "",
        "",
        "",
        0,
        "",
        "",
        "",
            "",
            ""
    )
}