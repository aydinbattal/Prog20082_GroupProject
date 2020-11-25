package com.example.prog20082_groupproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
    }

    fun onClick(v: View?){
        if(v!=null){
            when(v.id){
                btnSignup.id ->{
                    if(validateInfo()){

                    }

                }
            }
        }
    }
    fun validateInfo() : Boolean{
        if(edtfirstName.text.toString().isEmpty()){
            edtfirstName.error = "first name cannot be empty"
            return false
        }

        if(edtlastName.text.toString().isEmpty()){
            edtlastName.error = "Last name cannot be empty"
            return false
        }

        if(edtEmail.text.toString().isEmpty()){
            edtEmail.error = "email cannot be empty"
            return false
        }
        else if(!DataValidations().validateEmail(edtEmail.text.toString())){
            edtEmail.error = "Not valid or is not sheridan email(@sheridancollege.ca)"
            return false
        }

        if(edtPassword.text.toString().isEmpty()){
            edtPassword.error = "Password cannot be empty"
            return false
        }

        if(edtStudentId.text.toString().isEmpty()){
            edtStudentId.error = "Student ID cannot be empty"
            return false
        }
        else if(edtStudentId.text.toString().length !=9){
            edtStudentId.error = "invalid student ID, must be 9 digits"
            return false
        }
        return true
    }
}
