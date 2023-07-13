package com.example.weatherapplication

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.animation.ObjectAnimator;
import android.content.Context
import android.location.Geocoder
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.weatherapplication.databinding.ActivityDialogBinding
import com.example.weatherapplication.databinding.ActivityMapssBinding
import com.google.android.gms.common.api.Status


import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener


class MapssActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private lateinit var mMap: GoogleMap
lateinit var binding : ActivityMapssBinding
lateinit var autocomplete :AutocompleteSupportFragment
    lateinit var geocoder: Geocoder
    var language:String?=""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mapss)
        val sharedPref = this.getSharedPreferences("shared_pref", Context.MODE_PRIVATE)
         language =sharedPref?.getString("language", "")
        geocoder = Geocoder(this)

        Places.initialize(applicationContext,"AIzaSyB2ABcZ9Yg_54ZEJgy0ZxcOTRgPbPSpu4M")
        autocomplete= supportFragmentManager.findFragmentById(R.id.autoComplete)as AutocompleteSupportFragment
        autocomplete.setPlaceFields(listOf(Place.Field.ID,Place.Field.ADDRESS,Place.Field.LAT_LNG) )
        autocomplete.setOnPlaceSelectedListener(object :PlaceSelectionListener{
            override fun onError(p0: Status) {
println(p0.statusMessage)
            }

            override fun onPlaceSelected(place: Place) {

                val latLng=place.latLng
                zoomOnMap(latLng )
            }
        })


        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val sydney = LatLng(26.82, 30.80)
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        mMap.setOnMapClickListener(this)

    }

    override fun onMapClick(latLng: LatLng) {
        val returnIntent = Intent()
        returnIntent.putExtra("latitude", latLng.latitude)
        returnIntent.putExtra("longitude", latLng.longitude)
        val lat = latLng.latitude
        val lng = latLng.longitude

        // Add a marker to the clicked location
        val marker = mMap.addMarker(MarkerOptions().position(latLng))
        val x = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 5)
        var contry:String =""
        if (x != null && x.size > 0) {
            contry= x[0].countryName
            returnIntent.putExtra("land", contry)

            println(x.size)
        }
        if (language=="en"){
        AlertDialog.Builder(this)
            .setTitle("Confirm selection")
            .setMessage("Do you want to choose this $contry?")
            .setPositiveButton("Yes") { dialog, which ->
                val selectedLocation = LatLng(lat, lng)
                Log.d("MapsActivity", "Selected location: $selectedLocation")
                setResult(Activity.RESULT_OK, returnIntent)
                finish()
            }
            .setNegativeButton("No") { dialog, which ->
                // Remove the marker from the map
                marker?.remove()
            }
            .show()}
        else{
            AlertDialog.Builder(this)
                .setTitle("اكد اختيارك")
                .setMessage("هل تريد اختيار هذه الدوله $contry؟")
                .setPositiveButton("نعم") { dialog, which ->
                    val selectedLocation = LatLng(lat, lng)
                    Log.d("MapsActivity", "Selected location: $selectedLocation")
                    setResult(Activity.RESULT_OK, returnIntent)
                    finish()
                }
                .setNegativeButton("لا") { dialog, which ->
                    // Remove the marker from the map
                    marker?.remove()
                }
                .show()
        }


    }
    private fun zoomOnMap(latLng: LatLng){
        val latlngzoom= CameraUpdateFactory.newLatLngZoom(latLng,12f)
        mMap?.animateCamera(latlngzoom)
    }
}
