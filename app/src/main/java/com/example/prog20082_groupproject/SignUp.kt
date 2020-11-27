package com.example.prog20082_groupproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.prog20082_groupproject.database.User
import com.example.prog20082_groupproject.database.UserViewModel
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUp : AppCompatActivity() {
    lateinit var userViewModel :UserViewModel
    var user = User()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        userViewModel = UserViewModel(this.application)

    }

    fun onClick(v: View?){
        if(v!=null){
            when(v.id){
                btnSignup.id ->{
                    if(validateInfo()){
                        addToDB()
                    }
                }
            }
        }
    }
    fun validateInfo() : Boolean{
        val tempEmail = edtEmail.text.toString()
        var tempB = false
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
        else{
            userViewModel.getUserByEmail(tempEmail)?.observe(this@SignUp,{matchedUser ->
                tempB = matchedUser == null
            })
            if(!tempB){
                edtEmail.error = "Email is already used"
                return false
            }
            else if(tempB){
                return true
            }
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

    fun addToDB(){
        user.firstname = edtfirstName.text.toString()
        user.lastname = edtlastName.text.toString()
        user.email = edtEmail.text.toString()
        user.password = DataValidations().encryptPassword(edtPassword.text.toString())
        user.studentID = edtStudentId.text.toString()

        var userViewModel = UserViewModel(this.application)
        userViewModel.insertAll(user)

        //val homeIntent = Intent(this,HomeActivity::class.java)
        //startActivity(homeIntent)
        //this@SignUp.finishAffinity()



    }

}
