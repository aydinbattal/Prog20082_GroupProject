package com.example.prog20082_groupproject.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.prog20082_groupproject.R

@Database(entities = arrayOf(User::class),version = 1)
abstract class LearninCommonsDatabase : RoomDatabase() {

    abstract fun userDao() : UserDao
    companion object{

        @Volatile
        private var INSTANCE: LearninCommonsDatabase? = null


        fun getDatabase(context: Context) : LearninCommonsDatabase{
            val tempI = INSTANCE

            if(tempI != null){
                return tempI
            }

            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LearninCommonsDatabase::class.java,
                    R.string.database_name.toString()
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }


        }
    }
}