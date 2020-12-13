

package com.example.prog20082_groupproject

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.AnimationDrawable
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
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

class Map : AppCompatActivity(), OnMapReadyCallback {
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN


        val animDrawable = activity_map.background as AnimationDrawable
        animDrawable.setEnterFadeDuration(10)
        animDrawable.setExitFadeDuration(5000)
        animDrawable.start()


        this.locationManager = LocationManager(this@Map)
        this.currentLocation = LatLng(43.6560, -79.7387)

        if (LocationManager.locationPermissionsGranted){
            this.getLastLocation()
        }
        val mapFragment  = supportFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
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
    }
    fun onClick(v:View?){
        if(v != null){
            when(v.id){
                btnContinue.id ->{
                    if (validateAnswer()){
                        this.sendRoomSelection()
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        locationManager.requestLocationUpdates(locationCallback)
    }
    fun validateAnswer(): Boolean {
        if (radioGroup.checkedRadioButtonId == -1) {
            val t = Toast.makeText(this@Map, "Please select at least one answer", Toast.LENGTH_SHORT)
            t.show()
            return false
        }
        return true
    }
    private fun sendRoomSelection(){
      //  val sendRoomIntent = Intent(this, BookingFragment::class.java)
      //  startActivity(sendRoomIntent)
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

                if (LocationManager.locationPermissionsGranted){
                    //location available
                    //try to fetch the location

                    this.getLastLocation()
                }
                return
            }
        }
    }

    private fun getLastLocation(){
        this.locationManager.getLastLocation()?.observe(this, {loc: Location? ->
            if (loc != null){
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