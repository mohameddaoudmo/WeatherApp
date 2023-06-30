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
import com.example.weatherapplication.databinding.FragmentSettingBinding
import java.util.*


class SettingFragment : Fragment() {
    var longitude: Double? = 1.0
    var latitude : Double? =1.0
    lateinit var viewModel :SharedViewModel

    lateinit var binding :FragmentSettingBinding
    companion object {
        private const val REQUEST_CODE_SELECT_LOCATION = 1
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)




        binding.mapRadioButton
            .setOnClickListener{
            val intent = Intent(requireContext(), MapssActivity::class.java)
            startActivityForResult(intent, 1)
        }
        binding.arabicRadioButton.setOnClickListener {
         setLocale("ar")
        }
        binding.englishRadioButton
            .setOnClickListener { setLocale("en") }
        binding.gpsRadioButton.setOnClickListener { }
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

