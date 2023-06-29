package com.example.designpattern.network

import com.example.designpattern.model.Response


sealed class NetworkState{
    class Success(val myResponse: Response): NetworkState()
    class Failure(val msg: String): NetworkState()
    object Loading: NetworkState()
}

