package com.example.prog20082_groupproject

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.prog20082_groupproject.BookingViewModel
import com.example.prog20082_groupproject.database.User
import com.example.prog20082_groupproject.database.UserViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

//import kotlinx.android.synthetic.main.fragment_profile.view.*

class HomeFragment : Fragment(), View.OnClickListener {

    private val TAG = this.toString()

    lateinit var edtName: EditText
    lateinit var edtStudentId: EditText
    lateinit var edtEmail: EditText
    lateinit var btnSave: Button
    lateinit var fabEditProfile: FloatingActionButton

    lateinit var userViewModel: UserViewModel
    lateinit var existingUser: User

    var currentUserEmail = SharedPreferencesManager.read(SharedPreferencesManager.EMAIL, "")

    private lateinit var bookingViewModel : BookingViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        val fabBook: FloatingActionButton = root.findViewById(R.id.fabBook)

        fabBook.setOnClickListener {
            findNavController().navigate(R.id.action_nav_home_to_booking_fragment)
        }

        edtName = root.findViewById(R.id.edtName)
        edtStudentId = root.findViewById(R.id.edtStudentId)
        edtEmail = root.findViewById(R.id.edtEmail)
        btnSave = root.findViewById(R.id.btnSave)
        fabEditProfile = root.findViewById(R.id.fabEditProfile)

        fabEditProfile.setOnClickListener(this)
        btnSave.setOnClickListener(this)

        this.disableEdit()

        return root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bookingViewModel = BookingViewModel()
        userViewModel = UserViewModel(this.requireActivity().application)

        this.populateProfile()
    }

    override fun onResume() {
        super.onResume()

        bookingViewModel.getAllBookings()
    }

    fun disableEdit(){
        edtName.isEnabled = false
        edtStudentId.isEnabled = false
        edtEmail.isEnabled = false
    }

    fun enableEdit(){
        edtName.isEnabled = true
        edtStudentId.isEnabled = true
        edtEmail.isEnabled = true
    }

    fun populateProfile(){
        if (currentUserEmail != null){
            userViewModel.getUserByEmail(currentUserEmail!!)?.observe(
                this.requireActivity(),
                { matchedUser ->

                    if (matchedUser != null) {

                        this.existingUser = matchedUser

                        Log.d("Home Fragment", "Matched user : " + matchedUser.toString())

                        edtName.setText(matchedUser.firstname + matchedUser.lastname)
                        edtStudentId.setText(matchedUser.studentID)
                        edtEmail.setText(matchedUser.email)
                    }
                })
        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            fabEditProfile.id -> {
                this.enableEdit()
                fabEditProfile.visibility = View.GONE
                btnSave.visibility = View.VISIBLE
            }
            btnSave.id -> {
                this.disableEdit()
                fabEditProfile.visibility = View.VISIBLE
                btnSave.visibility = View.GONE

                this.saveToDB()
            }
        }
    }


    private fun saveToDB(){
        //todo: this is last thing done, fix errors and try to run app
        val firstSpace: Int = edtName.text.indexOf(" ") // detect the first space character
        val firstName: String = edtName.text.substring(0, firstSpace) // get everything upto the first space character
        val lastName: String = edtName.text.substring(firstSpace).trim() // get everything after the first space, trimming the spaces off

        this.existingUser.firstname = firstName
        this.existingUser.lastname = lastName
        this.existingUser.studentID = edtStudentId.text.toString()
        this.existingUser.email = edtEmail.text.toString()

        try{
            userViewModel.updateUser(existingUser)
        }catch (ex: Exception){
            Log.d("Home Fragment", ex.localizedMessage)
        }
    }
}