package com.example.prog20082_groupproject

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

/**
 * Prog20082_GroupProject created by aydin
 * student ID : 991521740
 * on 24/11/20 */

object SharedPreferencesManager {
    private var sharedPreferences: SharedPreferences? = null

    val EMAIL = "KEY_EMAIL"
    val PASSWORD = "KEY_PASSWORD"

    fun init (context: Context) {
        if (sharedPreferences == null){
            sharedPreferences = context.getSharedPreferences(context.packageName,
                    Activity.MODE_PRIVATE)
        }
    }

    fun write(key: String?, value: String?){
        apply { sharedPreferences!!.edit().putString(key, value).apply() }
    }

    fun read(key: String?, defaultValue: String?): String?{
        with(sharedPreferences) {
            if (this!!.contains(key)){
                return sharedPreferences!!.getString(key,defaultValue)
            }
        }
        return defaultValue
    }

    fun remove(key: String?){
        if ( sharedPreferences != null && sharedPreferences!!.contains(key)) {
            apply { sharedPreferences?.edit()!!.remove(key).apply() }
        }
    }

    fun removeAll(){
        //takehome - modify the method to check if shared pref is not null and contains the keys

        with(sharedPreferences!!.edit()){
            remove(EMAIL)
            remove(PASSWORD)

            apply()  //asynchronously
//            commit() //synchronously
        }
    }

}