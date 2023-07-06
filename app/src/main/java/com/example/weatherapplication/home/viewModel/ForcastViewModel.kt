package com.example.designpattern.allproduct.viewModel

import androidx.lifecycle.ViewModel
import com.example.designpattern.model.Product
import kotlinx.coroutines.Dispatchers
import androidx.lifecycle.viewModelScope
import com.example.designpattern.model.RepositioryInterface
import com.example.designpattern.network.NetworkState
import com.example.weatherapplication.model.Favorite
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch


class ForcastViewModel(private val repoInterface: RepositioryInterface) : ViewModel() {
    private val _productsMutableStateFlow: MutableStateFlow<NetworkState> =
        MutableStateFlow(NetworkState.Loading)
    val productsStateFlow: StateFlow<NetworkState>
        get() = _productsMutableStateFlow
    private val _savedLocationMutableStateFlow: MutableStateFlow<List<Favorite>> =
        MutableStateFlow(emptyList())
    val savedProductsStateFlow: StateFlow<List<Favorite>>
        get() = _savedLocationMutableStateFlow
    init {

    }


     fun getWeather(lat: Double,
                    lon: Double,
                    lang: String,
                    unit: String,) {
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
        }
    }

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
}