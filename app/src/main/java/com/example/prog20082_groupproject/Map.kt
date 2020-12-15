

package com.example.prog20082_groupproject

import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.prog20082_groupproject.database.Booking
import com.example.prog20082_groupproject.database.BookingViewModel
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_map.*

class Map : Fragment(), OnMapReadyCallback, View.OnClickListener {
    private val TAG = this@Map.toString()
    private lateinit var locationManager: LocationManager
    private lateinit var location: Location
    private lateinit var currentLocation : LatLng
    private var map: GoogleMap? = null
    private val DEFAULT_ZOOM : Float = 8.0F  //1: world, 5: landmass/continent, 10: city, 15: streets, 20: building
    private lateinit var locationCallback: LocationCallback
    val davisCampus = LatLng(43.6560, -79.7387)
    val hazelMcCallionCampus = LatLng(43.4692, -79.6986)
    val trafalgarCampus = LatLng(43.5912, -79.6480)
    private lateinit var btnContinue: Button

    var selectedRadioButton: RadioButton? = null

    private var rootView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_map)

    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.activity_map, container, false)


//        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN


//        val animDrawable = activity_map.background as AnimationDrawable
//        animDrawable.setEnterFadeDuration(10)
//        animDrawable.setExitFadeDuration(5000)
//        animDrawable.start()


        this.locationManager = LocationManager(this.requireActivity())
        this.currentLocation = LatLng(43.6560, -79.7387)

        if (LocationManager.locationPermissionsGranted){
            this.getLastLocation()
        }
        val mapFragment  = childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
//                supportFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

        locationCallback = object : LocationCallback(){
            override fun onLocationResult(locationResult: LocationResult?){
                locationResult ?: return

                for(location in locationResult.locations){
                    currentLocation = LatLng(location.latitude, location.longitude)
                    addMarkerOnMap(currentLocation)
                }
            }
        }

        btnContinue = rootView!!.findViewById(R.id.btnContinue)
        btnContinue.setOnClickListener(this)

        return rootView
    }

    override fun onClick(v: View?){
        if(v != null){
            when(v.id){
                R.id.btnContinue -> {
                    if (validateAnswer()) {
                        val selectedOption: Int = radioGroup!!.checkedRadioButtonId
                        this.selectedRadioButton = rootView?.findViewById(selectedOption)

                        tempBooking.tempRoom = selectedRadioButton?.text.toString()
                        BookingViewModel(this.requireActivity().application).updateBooking(tempBooking)
                        findNavController().navigateUp()
                    }
                }
            }
        }
    }

    companion object{
        var tempBooking = Booking()
    }

    override fun onResume() {
        super.onResume()
        locationManager.requestLocationUpdates(locationCallback)
    }
    fun validateAnswer(): Boolean {
        if (radioGroup?.checkedRadioButtonId == -1) {
            val t = Toast.makeText(this.context, "Please select at least one answer", Toast.LENGTH_SHORT)
            t.show()
            return false
        }
        return true
    }

    override fun onPause() {
        super.onPause()
        locationManager.fusedLocationProviderClient?.removeLocationUpdates(locationCallback)
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            LocationManager.LOCATION_PERMISSION_REQUEST_CODE -> {
                LocationManager.locationPermissionsGranted = (grantResults.isNotEmpty() &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED)

                if (LocationManager.locationPermissionsGranted) {
                    //location available
                    //try to fetch the location

                    this.getLastLocation()
                }
                return
            }
        }
    }

    private fun getLastLocation(){
        this.locationManager.getLastLocation()?.observe(this.viewLifecycleOwner, { loc: Location? ->
            if (loc != null) {
                this.location = loc
                this.currentLocation = LatLng(location.latitude, location.longitude)

                Log.e(TAG, "current location : " + this.currentLocation.toString())

                //display the location on map
                this.addMarkerOnMap(this.currentLocation)
            }
        })
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        this.getLastLocation()

        if (googleMap != null){
            googleMap.mapType = GoogleMap.MAP_TYPE_HYBRID
            googleMap.uiSettings.isZoomControlsEnabled = true
            googleMap.uiSettings.isZoomGesturesEnabled = true
            googleMap.uiSettings.isMyLocationButtonEnabled = true
            googleMap.uiSettings.isScrollGesturesEnabled = true

            //     googleMap.addMarker(
            //       MarkerOptions().position(this.currentLocation).title("You're Here")
            googleMap.setOnMarkerClickListener { marker ->
                if (marker.isInfoWindowShown) {
                    marker.hideInfoWindow()
                    //      marker.s
                } else {
                    marker.showInfoWindow()
                }
                true
            }

            this.map = googleMap
        }else{
            Log.e(TAG, "Map not ready yet")
        }
    }

    private fun addMarkerOnMap(location: LatLng){
        if (this.map != null){
            ///        this.map!!.addMarker(
            ///            MarkerOptions().position(location).title("Current Location")
            ///         )
            this.map!!.addMarker(
                    MarkerOptions().position(davisCampus).title("Davis Campus (Brampton)").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
            )
            this.map!!.addMarker(
                    MarkerOptions().position(hazelMcCallionCampus).title("Hazel McCallion Campus (Mississauga)").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
            )
            this.map!!.addMarker(
                    MarkerOptions().position(trafalgarCampus).title("Trafalgar Campus (Oakville)").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
            )

            this.map!!.animateCamera(CameraUpdateFactory.newLatLngZoom(location, DEFAULT_ZOOM))

        }
    }
}