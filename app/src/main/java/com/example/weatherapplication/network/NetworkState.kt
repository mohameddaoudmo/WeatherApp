package com.example.designpattern.network

import com.example.designpattern.model.Response
import com.example.weatherforecastapp.ui.home.model.Forecast


sealed class NetworkState{
    class Success(val myResponse: Forecast): NetworkState()
    class Failure(val msg: String): NetworkState()
    object Loading: NetworkState()
}

