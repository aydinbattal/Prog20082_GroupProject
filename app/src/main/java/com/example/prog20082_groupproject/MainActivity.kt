package com.example.prog20082_groupproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.prog20082_groupproject.database.Booking
import com.example.prog20082_groupproject.database.BookingViewModel
import com.example.prog20082_groupproject.database.User

class MainActivity : AppCompatActivity() {
    lateinit var bookingViewModel : BookingViewModel
    private val SPLASH_TIME_OUT:Long = 3000 // 1 sec
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bookingViewModel = BookingViewModel(this.application)
        prePopulateBooking()


        Handler().postDelayed({
            // This method will be executed once the timer is over
            // Start your app main activity

            startActivity(Intent(this,MainActivity::class.java))

            // close this activity
            finish()
        }, SPLASH_TIME_OUT)

    }

    private fun prePopulateBooking(){
        bookingViewModel.allBooking.observe(this@MainActivity,{
            var num = 0;
            for(booking in it){
                num++
            }
            if(num != 30){
                bookingViewModel.insertAll(Booking("100","Trafalgar","",0,"","",""))
                bookingViewModel.insertAll(Booking("101","Trafalgar","",0,"","",""))
                bookingViewModel.insertAll(Booking("102","Trafalgar","",0,"","",""))
                bookingViewModel.insertAll(Booking("103","Trafalgar","",0,"","",""))
                bookingViewModel.insertAll(Booking("104","Trafalgar","",0,"","",""))
                bookingViewModel.insertAll(Booking("105","Trafalgar","",0,"","",""))
                bookingViewModel.insertAll(Booking("106","Trafalgar","",0,"","",""))
                bookingViewModel.insertAll(Booking("107","Trafalgar","",0,"","",""))
                bookingViewModel.insertAll(Booking("108","Trafalgar","",0,"","",""))
                bookingViewModel.insertAll(Booking("109","Trafalgar","",0,"","",""))

                bookingViewModel.insertAll(Booking("100","Davis","",0,"","",""))
                bookingViewModel.insertAll(Booking("101","Davis","",0,"","",""))
                bookingViewModel.insertAll(Booking("102","Davis","",0,"","",""))
                bookingViewModel.insertAll(Booking("103","Davis","",0,"","",""))
                bookingViewModel.insertAll(Booking("104","Davis","",0,"","",""))
                bookingViewModel.insertAll(Booking("105","Davis","",0,"","",""))
                bookingViewModel.insertAll(Booking("106","Davis","",0,"","",""))
                bookingViewModel.insertAll(Booking("107","Davis","",0,"","",""))
                bookingViewModel.insertAll(Booking("108","Davis","",0,"","",""))
                bookingViewModel.insertAll(Booking("109","Davis","",0,"","",""))

                bookingViewModel.insertAll(Booking("100","Hazel McCallion","",0,"","",""))
                bookingViewModel.insertAll(Booking("101","Hazel McCallion","",0,"","",""))
                bookingViewModel.insertAll(Booking("102","Hazel McCallion","",0,"","",""))
                bookingViewModel.insertAll(Booking("103","Hazel McCallion","",0,"","",""))
                bookingViewModel.insertAll(Booking("104","Hazel McCallion","",0,"","",""))
                bookingViewModel.insertAll(Booking("105","Hazel McCallion","",0,"","",""))
                bookingViewModel.insertAll(Booking("106","Hazel McCallion","",0,"","",""))
                bookingViewModel.insertAll(Booking("107","Hazel McCallion","",0,"","",""))
                bookingViewModel.insertAll(Booking("108","Hazel McCallion","",0,"","",""))
                bookingViewModel.insertAll(Booking("109","Hazel McCallion","",0,"","",""))
            }
        })
    }
}