package com.example.weatherapplication

import kotlinx.coroutines.flow.Flow

interface ConnectivityObserver {

    fun observe(): Flow<Status>

    sealed class Status {
        object Available: Status()
        object Losing: Status()
        object Lost: Status()
        object Unavailable: Status()
    }
}