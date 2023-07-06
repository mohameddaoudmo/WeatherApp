package com.example.weatherapplication

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.animation.ObjectAnimator;
import com.example.weatherapplication.databinding.ActivityDialogBinding
import com.example.weatherapplication.databinding.ActivityMapssBinding


import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class MapssActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private lateinit var mMap: GoogleMap
lateinit var binding : ActivityMapssBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.map)
        

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
        setResult(Activity.RESULT_OK, returnIntent)
        finish()
    }
}
