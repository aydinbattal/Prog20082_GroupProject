package com.example.prog20082_groupproject.database

import androidx.room.ColumnInfo
import androidx.room.Entity


@Entity(tableName = "Users",primaryKeys = arrayOf("email"))
data class User(
    @ColumnInfo(name = "firstName") var firstname:String?,
    @ColumnInfo(name = "LastName") var lastname:String?,
    @ColumnInfo(name = "email") var email:String?,
    @ColumnInfo(name="password") var password:String?,
    @ColumnInfo(name = "studentID") var studentID:String?
) {
    constructor() : this(
        "",
        "",
        "",
        "",
        ""
    )

}