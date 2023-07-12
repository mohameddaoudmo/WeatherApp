package com.example.designpattern.allproduct.viewModel

import android.content.Context
import android.net.ConnectivityManager
import androidx.lifecycle.ViewModel
import com.example.designpattern.model.Product
import kotlinx.coroutines.Dispatchers
import androidx.lifecycle.viewModelScope
import com.example.designpattern.model.RepositioryInterface
import com.example.designpattern.network.NetworkState
import com.example.weatherapplication.ConnectivityObserver
import com.example.weatherapplication.ConnectivtyManger
import com.example.weatherapplication.model.Alert
import com.example.weatherapplication.model.Favorite
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch


class ForcastViewModel(private val repoInterface: RepositioryInterface) : ViewModel() {

var flagconnect:Boolean=true
    private val _productsMutableStateFlow: MutableStateFlow<NetworkState> =
        MutableStateFlow(NetworkState.Loading)
    val productsStateFlow: StateFlow<NetworkState>
        get() = _productsMutableStateFlow
    private val _savedLocationMutableStateFlow: MutableStateFlow<List<Favorite>> =
        MutableStateFlow(emptyList())
    val savedProductsStateFlow: StateFlow<List<Favorite>>
        get() = _savedLocationMutableStateFlow
    private val _savedalartMutableStateFlow: MutableStateFlow<List<Alert>> =
        MutableStateFlow(emptyList())
    val savedalartStateFlow: StateFlow<List<Alert>>
        get() = _savedalartMutableStateFlow
    init {

    }

     fun getWeather(lat: Double,
                    lon: Double,
                    lang: String,
                    unit: String) {
        println("$lat $lang in view model")

        viewModelScope.launch {
            repoInterface.getFromNetwork(lat,lon,lang,unit).catch {
                NetworkState.Failure(it.message!!)
            }.collect {

                if (it.isSuccessful) {

                    _productsMutableStateFlow.value = NetworkState.Success(it.body()!!)
                } else {
                    val errorBody = it.errorBody()?.string()
                    _productsMutableStateFlow.value = NetworkState.Failure(errorBody!!)
                }

            }
        }}


    fun addToFavorites(favorite: Favorite){
        viewModelScope.launch (Dispatchers.IO){
            repoInterface.addToFavorites(favorite)
        }
    }

    fun getSavedProducts() {
        viewModelScope.launch {
            repoInterface.getFromDatabase().collect {
                _savedLocationMutableStateFlow.value  = it
            println("impir$it.get(0).place")
            }

        }
    }

    fun deleteFromFav(favorite: Favorite) {
        viewModelScope.launch {
            repoInterface.removeFromFavorites(favorite)
        }
    }
fun addToAlert(alert:  Alert){
    viewModelScope.launch (Dispatchers.IO){
        repoInterface.addToAlart(alert)
    }
}

    fun getsavedAlert() {
        viewModelScope.launch {
            repoInterface.getFromDatabaseAlart().collect {
                _savedalartMutableStateFlow.value  = it
                println("impir$it.get(0).place")
            }

        }
    }

    fun deleteFromFav(alert: Alert) {
        viewModelScope.launch {
            repoInterface.removeFromAlart(alert)
        }


}
    fun isNetworkConnected(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}