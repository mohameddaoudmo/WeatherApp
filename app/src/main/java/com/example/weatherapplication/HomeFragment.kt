package com.example.weatherapplication

import android.location.Geocoder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapplication.databinding.FragmentHomeBinding
import com.example.weatherapplication.databinding.FragmentSettingBinding
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult

class HomeFragment : Fragment(){
    private var longitude: Double = 0.0
    private var latitude: Double = 0.0

    lateinit var viewModel :SharedViewModel
    lateinit var geocoder: Geocoder
    lateinit var binding : FragmentHomeBinding



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




        binding= FragmentHomeBinding.inflate(inflater,container,false)

        // Inflate the layout for this fragment
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.longitude.observe(viewLifecycleOwner){longitude ->
            binding.locationtxtviewinmain.text = longitude.toString()
            this.longitude = longitude
        }
        viewModel.latitude.observe(viewLifecycleOwner){latitude ->
            this.latitude = latitude
            val x = geocoder.getFromLocation(this.latitude ,this.longitude, 5)

            if (x != null) {
                binding.locationtxtviewinmain.text =x[0].countryName
                println("heyyyyyyyy"+this.latitude)
                println("heyyyyyyyy"+this.longitude)
                println(x.size)

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