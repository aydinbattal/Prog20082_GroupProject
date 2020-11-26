package com.example.prog20082_groupproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.widget.SwitchCompat
import com.example.prog20082_groupproject.database.UserViewModel

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    val TAG : String = this@LoginActivity.toString()

    lateinit var tvCreateAccount: TextView
    lateinit var edtEmail: EditText
    lateinit var edtPassword: EditText
    lateinit var btnLogIn: Button
    lateinit var swtRemember: SwitchCompat

    lateinit var userViewModel : UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        SharedPreferencesManager.init(applicationContext)
        this.fetchPreferences()

        tvCreateAccount = findViewById(R.id.tvCreateAccount)
        edtEmail = findViewById(R.id.edtEmail)
        edtPassword = findViewById(R.id.edtPassword)
        btnLogIn = findViewById(R.id.btnLogIn)
        swtRemember = findViewById(R.id.swtRemember)

        tvCreateAccount.setOnClickListener(this)
        btnLogIn.setOnClickListener(this)

        userViewModel = UserViewModel(this.application)
        this.fetchAllUsers()
    }

    private fun fetchPreferences(){
        edtEmail.setText(SharedPreferencesManager.read(SharedPreferencesManager.EMAIL, ""))
        edtPassword.setText(SharedPreferencesManager.read(SharedPreferencesManager.PASSWORD,""))
    }

    private fun fetchAllUsers(){
        userViewModel.allUsers.observe(this@LoginActivity, {
            for(user in it){
                Log.d(TAG, user.toString())
            }

            //show the data in UI
            //call user defined methods
        })
    }

    override fun onClick(v: View?) {
        if (v != null) {
            if (v.id == tvCreateAccount.id){
                this.goToCreateAccount()
            }else if (v.id == btnLogIn.id){
                if (this.validateData()) {
                    this.validateUser()
                }
            }
        }
    }

    private fun validateData() : Boolean{
        if (edtEmail.text.isEmpty()){
            edtEmail.setError("Please enter an email address!")
            return false
        }

        if (edtPassword.text.isEmpty()){
            edtPassword.setError("Please enter a password!")
            return false
        }

//        if (!Patterns.EMAIL_ADDRESS.matcher(edtEmail.text).matches()){
//            edtEmail.setError("Please provide a valid email address")
//            return false
//        }

        return true
    }

    private fun validateUser(){

        val email = edtEmail.text.toString()
        val password = DataValidations().encryptPassword(edtPassword.text.toString())

        userViewModel.getUserByLogin(email, password)?.observe(this@LoginActivity, {matchedUser ->
            if ( matchedUser != null){
                //valid login
                this.checkRemember()
                this@LoginActivity.finishAndRemoveTask()
                this.goToHome()
            }else{
                //invalid login
                Toast.makeText(this, "Incorrect email or password. Try again!", Toast.LENGTH_LONG).show()
            }
        })

    }

    private fun checkRemember(){
        if (swtRemember.isChecked){
            //save the credentials in shared preferences
            SharedPreferencesManager.write(SharedPreferencesManager.EMAIL, edtEmail.text.toString())
            SharedPreferencesManager.write(SharedPreferencesManager.PASSWORD, edtPassword.text.toString())
        }else{
            //remove the credentials from shared preferences
            SharedPreferencesManager.remove(SharedPreferencesManager.EMAIL)
            SharedPreferencesManager.remove(SharedPreferencesManager.PASSWORD)
        }
    }

    private fun goToHome(){
        val homeIntent = Intent(this, HomeActivity::class.java)
        startActivity(homeIntent)
        this@LoginActivity.finishAffinity()
    }

    private fun goToCreateAccount(){
        val createAccountIntent = Intent(this, SignUp::class.java)
        startActivity(createAccountIntent)
    }
}