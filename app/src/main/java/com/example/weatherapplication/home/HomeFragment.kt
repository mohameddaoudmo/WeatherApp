package com.example.weatherapplication.home

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.AnimationDrawable
import android.location.Geocoder
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.example.designpattern.allproduct.viewModel.AllproductviewFactory
import com.example.designpattern.allproduct.viewModel.ForcastViewModel
import com.example.designpattern.db.ConLocalSource
import com.example.designpattern.model.Repostiory
import com.example.designpattern.network.ApiClient
import com.example.designpattern.network.NetworkState
import com.example.weatherapplication.My_LOCATION_PERMISSION_ID
import com.example.weatherapplication.R
import com.example.weatherapplication.SharedViewModel
import com.example.weatherapplication.alart.MyWorker
import com.example.weatherapplication.databinding.FragmentHomeBinding
import com.example.weatherforecastapp.ui.home.model.Daily
import com.example.weatherforecastapp.ui.home.model.Hourly
import com.google.android.gms.location.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : Fragment() {
    private var longitude: Double = 0.0
    private var latitude: Double = 0.0
    lateinit var timeZone: TimeZone
    var timeZoneS: String? = ""
    var language: String = ""
    var unit: String = ""
    var gps:Boolean =true
    lateinit var fusedClient: FusedLocationProviderClient

    private lateinit var hours: List<Hourly>
    private lateinit var day: List<Daily>



    private lateinit var recyclerAdapter:RecyclerAdapter
    private lateinit var dayAdatper: DayAdatper

    private lateinit var myLayoutManager: LinearLayoutManager
    private lateinit var dayLayoutManager: LinearLayoutManager


    lateinit var viewModel: SharedViewModel
    lateinit var geocoder: Geocoder
    lateinit var binding: FragmentHomeBinding
    lateinit var forcastViewModel: ForcastViewModel
    lateinit var forcastViewModelFactory: AllproductviewFactory


    override fun onResume() {
        super.onResume()
        if(gps){
        getLastLocation()

        }


    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        geocoder = Geocoder(requireContext())

        viewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)




        binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dayLayoutManager = LinearLayoutManager(view.context,LinearLayoutManager.VERTICAL,false)
        fusedClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        myLayoutManager = LinearLayoutManager(view.context,LinearLayoutManager.HORIZONTAL,false)
        recyclerAdapter =RecyclerAdapter(view.context)
                dayAdatper = DayAdatper(view.context){

                }



                binding.rchour.apply { adapter= recyclerAdapter
            layoutManager = myLayoutManager }
        binding.rcday.apply { adapter= dayAdatper
            layoutManager = dayLayoutManager

        }

        val spacing = resources.getDimensionPixelSize(R.dimen.item_spacing)
        binding.rcday.addItemDecoration(ItemspaceDco(spacing))
        forcastViewModelFactory =
            AllproductviewFactory(Repostiory(ApiClient, ConLocalSource(requireContext())))
        forcastViewModel = ViewModelProvider(
            this,
            forcastViewModelFactory
        ).get(ForcastViewModel::class.java)
        val animationDrawable: AnimationDrawable = binding.mainliner.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(2500)
        animationDrawable.setExitFadeDuration(5000)
        animationDrawable.start()

        lifecycleScope.launch {

            forcastViewModel.productsStateFlow.collectLatest { result ->
                when (result) {
                    is NetworkState.Loading -> {
                        println("isloading")
                    }
                    is NetworkState.Success -> {
                        hours = result.myResponse.hourly
                        day = result.myResponse.daily
                       dayAdatper.submitList(day)
                        recyclerAdapter.submitList(hours)

                        binding.timetv.visibility = View.VISIBLE

                        timeZone = TimeZone.getTimeZone(result.myResponse.timezone)

                        val currentTimeMillis = System.currentTimeMillis()


                        val currentDate = Date(currentTimeMillis)


                        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")


                        sdf.timeZone = timeZone


                        val timeZoneTime = sdf.format(currentDate)

                        binding.timetv.text = timeZoneTime
                        timeZoneS = result.myResponse.timezone

                        binding.tvHumidityTemp.text = result.myResponse.current.humidity.toString()
                        binding.tvWindSpeedUnit.text =
                            result.myResponse.current.wind_speed.toString()
                        binding.temparaturetxtview.text = result.myResponse.current.temp.toString()
                        binding.statustxview.text = result.myResponse.current.weather[0].description
                        binding.tvCloudsUnit.text =
                            result.myResponse.current.clouds.toString() + " %"
                        binding.tvPressureUnit.text = result.myResponse.current.pressure.toString()




                        when (result.myResponse.current.weather[0].icon) {
                            "01d" -> binding.imageforweather.setImageResource(com.example.weatherapplication.R.drawable.ic_clear_day)
                            "02d" -> binding.imageforweather.setImageResource(com.example.weatherapplication.R.drawable.ic_few_clouds)
                            "03d" -> binding.imageforweather.setImageResource(com.example.weatherapplication.R.drawable.ic_cloudy_weather)
                            "09d" -> binding.imageforweather.setImageResource(com.example.weatherapplication.R.drawable.rainy)
                            "10d" -> binding.imageforweather.setImageResource(com.example.weatherapplication.R.drawable.rain_svgrepo_com)
                            "11d" -> binding.imageforweather.setImageResource(com.example.weatherapplication.R.drawable.ic_storm_weather)
                            "13d" -> binding.imageforweather.setImageResource(com.example.weatherapplication.R.drawable.ic_snow_weather)
                            "01n" -> binding.imageforweather.setImageResource(com.example.weatherapplication.R.drawable.ic_clear_day)
                            "03n" -> binding.imageforweather.setImageResource(com.example.weatherapplication.R.drawable.ic_mostly_cloudy)
                            "04d" -> binding.imageforweather.setImageResource(com.example.weatherapplication.R.drawable.ic_mostly_cloudy)

                            "09n" -> binding.imageforweather.setImageResource(com.example.weatherapplication.R.drawable.rainy)

                        }

                    }
                    else -> {
                        Toast.makeText(
                            requireContext(),
                            "check your connection $result",
                            Toast.LENGTH_SHORT
                        ).show()

                    }
                }

            }
        }
        viewModel.language.observe(viewLifecycleOwner) {
            language = it
            forcastViewModel.getWeather(latitude, longitude, language, unit)

        }
        viewModel.unitfortemp.observe(viewLifecycleOwner) {
            unit = it
        }
        viewModel.longitude.observe(viewLifecycleOwner) { longitude ->
            this.longitude = longitude

        }
        viewModel.satusoflocation.observe(viewLifecycleOwner){
println(it)
            if (it=="gps"){
                gps=true
            }
            else{
                gps=false}

        }
        viewModel.latitude.observe(viewLifecycleOwner) { latitude ->
            this.latitude = latitude
            forcastViewModel.getWeather(latitude, longitude, language, unit)


            try {
                val x = geocoder.getFromLocation(latitude, this.longitude, 5)

                if (x != null && x.size > 0) {
                    binding.country.text = x[0].countryName
                    binding.place.text = x[0].adminArea

                    println(x.size)
                }
            } catch (e: Exception) {
            }


        }


    }

    private val locationCallBack: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)
            val lastLocation = locationResult.lastLocation
            println("gps ${lastLocation?.latitude}")
            println(lastLocation?.longitude)

           viewModel.longitudegps.value =lastLocation?.longitude ?:0.0
            viewModel.latitudegps.value =lastLocation?.latitude ?:0.0


            if (gps){
          longitude =lastLocation?.longitude ?:0.0
            latitude = lastLocation?.latitude?:0.0}
            forcastViewModel.getWeather(lastLocation?.latitude?:0.0, lastLocation?.longitude?:0.0, language, unit)


            try {
                val x = geocoder.getFromLocation(locationResult.lastLocation?.latitude?:0.0, lastLocation?.longitude?:0.0, 5)

                if (x != null && x.size > 0) {
                    binding.country.text = x[0].countryName
                    binding.place.text = x[0].adminArea

                    println(x.size)
                }
            } catch (e: Exception) {
            }



        }
    }



    private fun getLastLocation() {
        if (checkPermission()) {
            if (isLocationIsEnabled()) {
                requestNewLocationData()
            } else {
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }

    private fun checkPermission(): Boolean {
        var result = false
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            result = true
        }
        return result
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            requireActivity(), arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            My_LOCATION_PERMISSION_ID
        )
    }

    private fun isLocationIsEnabled(): Boolean {
        val locationManager: LocationManager
        = requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }


    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {
        val locationRequest = LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 0
        fusedClient.requestLocationUpdates(locationRequest, locationCallBack, Looper.myLooper())
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            val latitude = data?.getDoubleExtra("latitude", 0.0) ?: 0.0
            val longitude = data?.getDoubleExtra("longitude", 0.0) ?: 0.0

            this.longitude = longitude
            this.latitude =latitude
            println("long $longitude")
            println("loc $latitude")
            viewModel.longitude.value = longitude
            viewModel.latitude.value = latitude
//            forcastViewModel.getAllProducts(latitude,longitude,"en","")





        }
    }



}