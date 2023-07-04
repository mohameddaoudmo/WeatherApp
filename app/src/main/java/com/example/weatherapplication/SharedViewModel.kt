package com.example.weatherapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    var longitude:  MutableLiveData <Double> = MutableLiveData<Double>(0.0)
    var latitude:  MutableLiveData <Double> =MutableLiveData<Double>(0.0)
    var satusoflocation : MutableLiveData <String> = MutableLiveData<String>()
    var unitfortemp : MutableLiveData <String> = MutableLiveData<String>()
    var unitforwind : MutableLiveData <String> = MutableLiveData<String>()
    var language : MutableLiveData <String> = MutableLiveData<String>()




}