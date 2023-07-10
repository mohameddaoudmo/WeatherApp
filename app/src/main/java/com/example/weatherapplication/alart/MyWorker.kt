package com.example.weatherapplication.alart

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.designpattern.network.Api
import com.example.designpattern.network.NetworkState
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOf

class MyWorker(context: Context, workerParams: WorkerParameters) :  CoroutineWorker(context, workerParams) {
  val notficationService:NotficationService = NotficationService(context)
    var  weatherStatus : String=""
    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun doWork(): Result {
        val inputData = inputData
        val long = inputData.getDouble("long",0.0)
        val lat = inputData.getDouble("lat", 0.0)
        val sound = inputData.getBoolean("sound",false)
        val land = inputData.getString("land")
        val starttime =inputData.getDouble("startTime",0.0)
        val endtime =inputData.getDouble("endTime",0.0)
        val currentTime = System.currentTimeMillis()
//        if (currentTime >= starttime && currentTime <= endtime) {
        val x = Api.apiService.getCurrentWeatherByLatAndLon(lat,long,"en","")
        flowOf(x).catch {
                NetworkState.Failure(it.message!!)
            }.collect {

                if (it.isSuccessful) {
                 weatherStatus=   NetworkState.Success(it.body()!!).myResponse.current.weather[0].description

                } else {
                    val errorBody = it.errorBody()?.string()
                }

            }


        notficationService.createNotficicationChannal()
        notficationService.showNotification(weatherStatus,sound,land?:"" )
        return Result.success();    }


}