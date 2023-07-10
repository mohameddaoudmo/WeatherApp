package com.example.weatherapplication.alart

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.designpattern.db.ConLocalSource
import com.example.designpattern.model.RepositioryInterface
import com.example.designpattern.model.Repostiory
import com.example.designpattern.network.Api
import com.example.designpattern.network.NetworkState
import com.example.designpattern.network.RemoteSource
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
        val sound = inputData.getBoolean("sound", true)
        val land = inputData.getString("land")
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