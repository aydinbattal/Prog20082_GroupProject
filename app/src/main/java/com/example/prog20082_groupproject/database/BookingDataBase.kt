package com.example.prog20082_groupproject.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.prog20082_groupproject.R


@Database(entities = arrayOf(Booking::class),version = 1)
abstract class BookingDataBase : RoomDatabase() {

    abstract fun bookingDao() : BookingDao
    companion object{

        @Volatile
        private var INSTANCE: BookingDataBase? = null

        fun getDataBase(context: Context) : BookingDataBase{
            val tempI = INSTANCE

            if(tempI != null){
                return tempI
            }

            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BookingDataBase::class.java,
                    R.string.database_BookingName.toString()
                )
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                return instance
            }
        }

    }


}