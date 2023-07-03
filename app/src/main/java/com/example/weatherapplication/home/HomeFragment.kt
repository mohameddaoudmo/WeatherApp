package com.example.weatherapplication.home

import android.graphics.drawable.AnimationDrawable
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.designpattern.allproduct.viewModel.AllproductviewFactory
import com.example.designpattern.allproduct.viewModel.ForcastViewModel
import com.example.designpattern.db.ConLocalSource
import com.example.designpattern.model.Repostiory
import com.example.designpattern.network.ApiClient
import com.example.designpattern.network.NetworkState
import com.example.weatherapplication.SharedViewModel
import com.example.weatherapplication.databinding.FragmentHomeBinding
import com.example.weatherforecastapp.ui.home.model.Hourly
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
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
    private lateinit var hours: List<Hourly>


    private lateinit var recyclerAdapter:RecyclerAdapter
    private lateinit var myLayoutManager: LinearLayoutManager


    lateinit var viewModel: SharedViewModel
    lateinit var geocoder: Geocoder
    lateinit var binding: FragmentHomeBinding
    lateinit var forcastViewModel: ForcastViewModel
    lateinit var forcastViewModelFactory: AllproductviewFactory


    override fun onResume() {
        super.onResume()

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

        myLayoutManager = LinearLayoutManager(view.context,LinearLayoutManager.HORIZONTAL,false)
        recyclerAdapter =RecyclerAdapter(view.context)

        binding.rchour.apply { adapter= recyclerAdapter
            layoutManager = myLayoutManager }


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
            forcastViewModel.getAllProducts(latitude, longitude, language, unit)

        }
        viewModel.unitfortemp.observe(viewLifecycleOwner) {
            unit = it
        }
        viewModel.longitude.observe(viewLifecycleOwner) { longitude ->
            this.longitude = longitude

        }
        viewModel.latitude.observe(viewLifecycleOwner) { latitude ->
            this.latitude = latitude
            forcastViewModel.getAllProducts(latitude, longitude, language, unit)


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


        }
    }


}