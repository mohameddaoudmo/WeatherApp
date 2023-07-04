package com.example.weatherapplication

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation.findNavController
import com.example.designpattern.allproduct.viewModel.AllproductviewFactory
import com.example.designpattern.allproduct.viewModel.ForcastViewModel
import com.example.designpattern.db.ConLocalSource
import com.example.designpattern.model.Repostiory
import com.example.designpattern.network.ApiClient
import com.example.weatherapplication.databinding.FragmentSettingBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import java.util.*


class SettingFragment : Fragment() {
    var longitude: Double? = 1.0
    var latitude : Double? =1.0
    lateinit var viewModel :SharedViewModel
    lateinit var forcastViewModel: ForcastViewModel
    lateinit var forcastViewModelFactory: AllproductviewFactory

    lateinit var binding :FragmentSettingBinding
    companion object {
        private const val REQUEST_CODE_SELECT_LOCATION = 1
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        forcastViewModelFactory =
            AllproductviewFactory(Repostiory(ApiClient, ConLocalSource(requireContext())))
        forcastViewModel = ViewModelProvider(
            this,
            forcastViewModelFactory
        ).get(ForcastViewModel::class.java)
        viewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)




        binding.mapRadioButton
            .setOnClickListener{
            val intent = Intent(requireContext(), MapssActivity::class.java)
            startActivityForResult(intent, 1)
                viewModel.satusoflocation.value="map"

        }
        binding.celsuis.setOnClickListener { viewModel.unitfortemp.value ="metric" }
        binding.fahrenheit.setOnClickListener { viewModel.unitfortemp.value ="imperial" }
        binding.kelvin.setOnClickListener { viewModel.unitfortemp.value ="" }
        binding.arabicRadioButton.setOnClickListener {
         setLocale("ar")
            viewModel.language.value ="ar"
        }
        binding.englishRadioButton
            .setOnClickListener { setLocale("en")
                viewModel.language.value ="en"}
        binding.gpsRadioButton.setOnClickListener {viewModel.satusoflocation.value="gps" }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding= FragmentSettingBinding.inflate(inflater,container,false)


        return binding.root
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
    private fun setLocale(lang: String) {
        val locale = Locale(lang)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        activity?.baseContext?.resources?.updateConfiguration(config, activity?.baseContext?.resources?.displayMetrics)
        activity?.recreate()
    }
}

