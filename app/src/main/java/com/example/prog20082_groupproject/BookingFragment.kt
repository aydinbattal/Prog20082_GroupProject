package com.example.prog20082_groupproject

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.fragment.findNavController
import java.text.DateFormat
import java.util.*

class BookingFragment : Fragment(), View.OnClickListener {
    private val TAG = this.toString()

    private lateinit var btnChooseLocation: Button
    private lateinit var edtStudentId: EditText
    private lateinit var edtStudentAmt: EditText
    private lateinit var edtTime: EditText
    private lateinit var spnDuration: Spinner
    private lateinit var tvBookedCampus: TextView
    private lateinit var tvBookedRoom: TextView
    private lateinit var btnBook: Button

    private var selectedDuration: Long = 1
    private val durationValues: Array<Long> = arrayOf(1, 2, 3)

    private lateinit var bookingViewModel: BookingViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_booking, container, false)

        edtStudentId = root.findViewById(R.id.edtStudentId)
        edtStudentAmt = root.findViewById(R.id.edtStudentAmt)
        tvBookedCampus = root.findViewById(R.id.tvBookedCampus)
        tvBookedRoom = root.findViewById(R.id.tvBookedRoom)
        edtTime = root.findViewById(R.id.edtTime)
        edtTime.isFocusable = false
        edtTime.setOnClickListener(this)
        spnDuration = root.findViewById(R.id.spnDuration)
        btnChooseLocation = root.findViewById(R.id.btnChooseLocation)
        btnChooseLocation.setOnClickListener(this)
        btnBook = root.findViewById(R.id.btnBook)
        btnChooseLocation.setOnClickListener(this)

        this.setCurrentDateTime()
        this.setUpSpinner()

        return root
    }

    companion object{
        var newBooking = Booking()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    private fun goToLocation(){
        val getLocationIntent = Intent(this.context, Map::class.java)
        startActivity(getLocationIntent)
        val receivedCampN = getLocationIntent.getStringExtra("")
        val receivedRoomN = getLocationIntent.getStringExtra("")
        tvBookedCampus.text = receivedCampN
        tvBookedRoom.text = receivedRoomN
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id){
                R.id.btnChooseLocation -> {
                    this.goToLocation()
                }
                R.id.edtTime -> {
                    this.fetchDateTime()
                }
                R.id.btnBook -> {
                    if (this.validateData() && this.checkRoom()) {
                        newBooking.studentID = edtStudentId.text.toString()
                        newBooking.studentAmount = edtStudentAmt.text.toString().toInt()
                        newBooking.campusName = tvBookedCampus.text.toString()
                        newBooking.roomNumber = tvBookedRoom.text.toString()
                        newBooking.duration = selectedDuration.toString()

                        Log.e(TAG, "New Booking : " + newBooking.toString())

                        this.saveToDB()

                        findNavController().navigateUp()
                    }
                }
            }
        }
    }

    private fun checkRoom() : Boolean{
        val tempCamp = tvBookedCampus.text.toString()
        val tempRoom = tvBookedRoom.text.toString()

        userViewModel.getBookingByCampusNandRoomN(tempCamp, tempRoom)?.observe(this, {matchedLoc ->
            if ( matchedLoc.studentID != ""){
                //invalid
                Toast.makeText(this, "This room is already booked. Please try booking another room!", Toast.LENGTH_LONG).show()
                return false
            } else {
                //valid
                return true
            }
    }


    private fun validateData() : Boolean{
        if (edtStudentId.text.isEmpty()){
            edtStudentId.setError("Please enter a student id!")
            return false
        }
        if (edtStudentAmt.text.isEmpty()){
            edtStudentAmt.setError("Please enter the number of students!")
            return false
        }
        if (tvBookedCampus.text.isEmpty()){
            tvBookedCampus.setError("Please choose a campus!")
            return false
        }
        if (tvBookedRoom.text.isEmpty()){
            tvBookedRoom.setError("Please choose a room!")
            return false
        }

        return true
    }


    private fun saveToDB(){
        BookingViewModel(this.requireActivity().application).updateBooking(newBooking)
    }

    private fun setUpSpinner(){
        val durationAdapter = ArrayAdapter(
                this.requireActivity(),
                android.R.layout.simple_spinner_item,
                resources.getStringArray(R.array.duration_array)
        )

        spnDuration.adapter = durationAdapter
        selectedDuration = this.durationValues[0]

        spnDuration.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
            ) {
                selectedDuration = durationValues[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                selectedDuration = durationValues[2]
            }
        }
    }

    private fun fetchDateTime() {
        val calendar= Calendar.getInstance()
        val year=calendar[Calendar.YEAR]
        val month=calendar[Calendar.MONTH]
        val day=calendar[Calendar.DAY_OF_MONTH]

        val hour = calendar[Calendar.HOUR_OF_DAY]
        val minute = calendar[Calendar.MINUTE]

        val datePickerDialog = DatePickerDialog(
                this.requireActivity(),
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
//                calendar.set(year, month, dayOfMonth)

                    //TO-DO TimePickerDialog()

                    TimePickerDialog(
                            this.requireActivity(),
                            TimePickerDialog.OnTimeSetListener{ view, hourOfDay, minute ->
                                calendar.set(year, month, dayOfMonth, hourOfDay, minute)

                                val bookingTime = calendar.time

                                newBooking.bookingDate = bookingTime.toString()

//                https://developer.android.com/reference/java/text/DateFormat
                                val df: DateFormat =
                                        DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.SHORT)
                                edtTime.setText(df.format(bookingTime).toString())
                            },
                            hour,
                            minute,
                            false
                    ).show()
                }, year, month, day
        )
        datePickerDialog.show()
    }

    private fun setCurrentDateTime(){
        val calendar = Calendar.getInstance()

        val df: DateFormat =
                DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.SHORT)
        edtTime.setText(df.format(calendar.time).toString())
    }

}