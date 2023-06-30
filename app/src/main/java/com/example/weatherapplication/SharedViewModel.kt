package com.example.weatherapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    var longitude:  MutableLiveData <Double> = MutableLiveData<Double>(0.0)
    var latitude:  MutableLiveData <Double> =MutableLiveData<Double>(0.0)

}