package com.example.prog20082_groupproject

import android.text.TextUtils
import android.util.Base64
import android.util.Patterns
import java.security.MessageDigest

class DataValidations {

    fun validateEmail(email : String) : Boolean{
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches() &&
                email.endsWith("sheridancollege.ca")
    }

    fun encryptPassword(password :String) : String{
        val md = MessageDigest.getInstance("SHA-256")
        md.update(password.toByteArray())
        val hashPass = md.digest()
        return Base64.encodeToString(hashPass,Base64.DEFAULT)

    }

}