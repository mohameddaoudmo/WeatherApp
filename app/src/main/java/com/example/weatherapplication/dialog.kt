package com.example.weatherapplication

import android.Manifest
import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.view.View
import android.view.WindowManager
import android.widget.RadioButton
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.weatherapplication.databinding.ActivityDialogBinding
import com.google.android.gms.location.*
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

const val My_LOCATION_PERMISSION_ID =44

class dialog : AppCompatActivity() {
    private lateinit var connectivityObserver: ConnectivityObserver
    private var isconnected :Boolean=true

    lateinit var map :RadioButton
    private var longitude: Double? = 0.0
    private var latitude: Double ?= 0.0
    lateinit var gps :RadioButton
    lateinit var binding: ActivityDialogBinding
    lateinit var fusedClient: FusedLocationProviderClient
    lateinit var viewModel: SharedViewModel


    override fun onResume() {
        super.onResume()

    }
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        fusedClient = LocationServices.getFusedLocationProviderClient(this)
        viewModel = ViewModelProvider(this).get(SharedViewModel::class.java)

        binding.gpsradiobut.setOnClickListener {


            println("isssssssss")
            viewModel.satusoflocation.value ="gps"

        }
        binding.mapradiobutton.setOnClickListener {
            val intent = Intent(this, MapssActivity::class.java)
            startActivityForResult(intent, 1)
        }
        binding.ok.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
            finish() // Finish SplashActivity

        }

    }


}