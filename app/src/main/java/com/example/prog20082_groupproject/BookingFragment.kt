package com.example.prog20082_groupproject

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

//todo: finish booking fragment
class BookingFragment : Fragment(), View.OnClickListener {
    private lateinit var btnChooseLocation: Button

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_booking, container, false)

        btnChooseLocation = root.findViewById(R.id.btnChooseLocation)
        btnChooseLocation.setOnClickListener(this)

        return root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    private fun goToLocation(){
        val getLocationIntent = Intent(this, Map::class.java)
        startActivity(getLocationIntent)
    }

    override fun onClick(v: View?) {
        if (v != null) {
            if (v.id == btnChooseLocation.id){
                this.goToLocation()
            }
        }
    }

}