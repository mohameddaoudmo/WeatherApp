package com.example.weatherapplication.home

import android.graphics.drawable.AnimationDrawable
import android.location.Geocoder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.designpattern.allproduct.viewModel.AllproductviewFactory
import com.example.designpattern.allproduct.viewModel.ForcastViewModel
import com.example.designpattern.db.ConLocalSource
import com.example.designpattern.model.Repostiory
import com.example.designpattern.network.ApiClient
import com.example.designpattern.network.NetworkState
import com.example.weatherapplication.SharedViewModel
import com.example.weatherapplication.databinding.FragmentHomeBinding
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeFragment : Fragment(){
    private var longitude: Double = 0.0
    private var latitude: Double = 0.0

    lateinit var viewModel : SharedViewModel
    lateinit var geocoder: Geocoder
    lateinit var binding : FragmentHomeBinding
    private val weatherViewModel: ForcastViewModel by viewModels()
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




        binding= FragmentHomeBinding.inflate(inflater,container,false)

        // Inflate the layout for this fragment
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        forcastViewModelFactory =
            AllproductviewFactory(Repostiory(ApiClient, ConLocalSource(requireContext())))
        forcastViewModel = ViewModelProvider(
            this,
            forcastViewModelFactory
        ).get(ForcastViewModel::class.java)
        forcastViewModel.senddata(latitude,longitude,"en","")
        val animationDrawable :AnimationDrawable = binding.mainliner.background as AnimationDrawable
    animationDrawable.setEnterFadeDuration(2500)
        animationDrawable.setExitFadeDuration(5000)
        animationDrawable.start()

        viewModel.longitude.observe(viewLifecycleOwner){longitude ->
            this.longitude = longitude
        }
        viewModel.latitude.observe(viewLifecycleOwner){latitude ->
            this.latitude = latitude
            val x = geocoder.getFromLocation(latitude ,this.longitude, 5)

            if (x != null&&x.size>0) {
                binding.country.text =x[0].countryName
                binding.place.text = x[0].adminArea
                println("heyyyyyyyy"+this.latitude)
                println("heyyyyyyyy"+this.longitude)
                println(x.size)

            }



        }
        lifecycleScope.launch {
            forcastViewModel.productsStateFlow.collectLatest{result->
                when(result){
                    is NetworkState.Loading -> {
                    }
                    is NetworkState.Success -> {
                        binding.testtxt.text =result.myResponse.current.toString()

                    }
                    else ->{
                        Toast.makeText(requireContext(), "check your connection $result", Toast.LENGTH_SHORT).show()

                    }
                }

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