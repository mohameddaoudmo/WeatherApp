package com.example.weatherapplication.favarouite

import android.graphics.drawable.AnimationDrawable
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.designpattern.allproduct.viewModel.AllproductviewFactory
import com.example.designpattern.allproduct.viewModel.ForcastViewModel
import com.example.designpattern.db.ConLocalSource
import com.example.designpattern.model.Repostiory
import com.example.designpattern.network.ApiClient
import com.example.designpattern.network.NetworkState
import com.example.weatherapplication.R
import com.example.weatherapplication.SharedViewModel
import com.example.weatherapplication.databinding.ActivityFavHomeBinding
import com.example.weatherapplication.home.DayAdatper
import com.example.weatherapplication.home.ItemspaceDco
import com.example.weatherapplication.home.RecyclerAdapter
import com.example.weatherforecastapp.ui.home.model.Daily
import com.example.weatherforecastapp.ui.home.model.Hourly
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class FavHome : AppCompatActivity() {
    private var longitude: Double = 0.0
    private var latitude: Double = 0.0
    lateinit var timeZone: TimeZone
    var timeZoneS: String? = ""
    var language: String = ""
    var unit: String = ""

    var land: String? = ""
    var gps: Boolean = true
    lateinit var fusedClient: FusedLocationProviderClient
    private lateinit var hours: List<Hourly>
    private lateinit var day: List<Daily>
    private lateinit var recyclerAdapter: RecyclerAdapter
    private lateinit var dayAdatper: DayAdatper

    private lateinit var myLayoutManager: LinearLayoutManager
    private lateinit var dayLayoutManager: LinearLayoutManager

    private lateinit var binding: ActivityFavHomeBinding

    lateinit var viewModel: SharedViewModel
    lateinit var geocoder: Geocoder
    lateinit var forcastViewModel: ForcastViewModel
    lateinit var forcastViewModelFactory: AllproductviewFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavHomeBinding.inflate(layoutInflater)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        setContentView(binding.root)
        longitude = getIntent().getDoubleExtra("long", 1.5)
        latitude = getIntent().getDoubleExtra("lat", 1.5)
        land = getIntent().getStringExtra("place")

        geocoder = Geocoder(this)

        viewModel = ViewModelProvider(this).get(SharedViewModel::class.java)
        dayLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        fusedClient = LocationServices.getFusedLocationProviderClient(this)

        myLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerAdapter = RecyclerAdapter(this)
        dayAdatper = DayAdatper(this) {

        }



        binding.rchour.apply {
            adapter = recyclerAdapter
            layoutManager = myLayoutManager
        }
        binding.rcday.apply {
            adapter = dayAdatper
            layoutManager = dayLayoutManager

        }

        val spacing = resources.getDimensionPixelSize(R.dimen.item_spacing)
        binding.rcday.addItemDecoration(ItemspaceDco(spacing))
        forcastViewModelFactory =
            AllproductviewFactory(Repostiory(ApiClient, ConLocalSource(this)))
        forcastViewModel = ViewModelProvider(
            this,
            forcastViewModelFactory
        ).get(ForcastViewModel::class.java)
        val animationDrawable: AnimationDrawable = binding.mainliner.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(2500)
        animationDrawable.setExitFadeDuration(5000)
        animationDrawable.start()
        forcastViewModel.getWeather(latitude, longitude, language, unit,true)

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
                            "01d" -> binding.imageforweather.setImageResource(R.drawable.ic_clear_day)
                            "02d" -> binding.imageforweather.setImageResource(R.drawable.ic_few_clouds)
                            "03d" -> binding.imageforweather.setImageResource(R.drawable.ic_cloudy_weather)
                            "09d" -> binding.imageforweather.setImageResource(R.drawable.ic_rainy_weather)
                            "10d" -> binding.imageforweather.setImageResource(R.drawable.ic_rainy_weather)
                            "11d" -> binding.imageforweather.setImageResource(R.drawable.ic_storm_weather)
                            "13d" -> binding.imageforweather.setImageResource(R.drawable.ic_snow_weather)
                            "01n" -> binding.imageforweather.setImageResource(R.drawable.ic_clear_day)
                            "03n" -> binding.imageforweather.setImageResource(R.drawable.ic_mostly_cloudy)
                            "04d" -> binding.imageforweather.setImageResource(R.drawable.ic_mostly_cloudy)

                            "09n" -> binding.imageforweather.setImageResource(R.drawable.rainy)

                        }

                    }
                    else -> {
//                        Toast.makeText(
//                            this,
//                            "check your connection $result",
//                            Toast.LENGTH_SHORT
//                        ).show()

                    }
                }

            }
        }
        try {
            val x = geocoder.getFromLocation(latitude, this.longitude, 5)
            binding.country.text = land
            if (x != null && x.size > 0) {
                binding.country.text = x[0].countryName
                binding.place.text = x[0].adminArea

                println(x.size)
            }
        } catch (e: Exception) {
        }
    }
}