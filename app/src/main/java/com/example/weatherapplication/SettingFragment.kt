package com.example.weatherapplication

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.core.app.ActivityCompat.recreate
import com.example.weatherapplication.databinding.FragmentSettingBinding
import com.google.android.gms.location.places.Places
import com.google.android.gms.location.places.ui.PlacePicker
import java.util.*


class SettingFragment : Fragment() {

    lateinit var binding :FragmentSettingBinding
    companion object {
        private const val REQUEST_CODE_SELECT_LOCATION = 1
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
println("long $longitude")
            println("loc $latitude")
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

