package com.example.prog20082_groupproject.database

import androidx.room.ColumnInfo
import androidx.room.Entity


@Entity(tableName = "Users",primaryKeys = arrayOf("email"))
data class User(
    @ColumnInfo(name = "email") var email:String?,
    @ColumnInfo(name="password") var password:String?,
    @ColumnInfo(name = "studentID") var studentID:Int?
) {
    constructor() : this(
        "",
        "",
        0
    )

}