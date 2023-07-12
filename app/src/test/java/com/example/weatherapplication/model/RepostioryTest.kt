package com.example.weatherapplication.model

import com.example.designpattern.db.LocalSource
import com.example.designpattern.model.Repostiory
import com.example.designpattern.network.NetworkState
import com.example.weatherapplication.db.ConLocalSource
import com.example.weatherapplication.network.ApiClient
import com.example.weatherforecastapp.ui.home.model.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.core.IsEqual
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class RepostioryTest {
    val alert1 = Alert("555", "44", "france", "")
    val alert2 = Alert("444", "444", "Egypt", "")
    val fav1 = Favorite("Egypt", 55.1, 44.1)
    val fav2 = Favorite("england", 444.1, 8888.1)
    val listOfFav = mutableListOf<Favorite>()
    val listOfAlert = mutableListOf(alert1, alert2)
    val rain = Rain(1.1)
    val current = Current(
        1, 1.5, 1, 1.1, 1,
        1, rain, 11, 1, 1.1, 1.1, 1,
        listOf(Weather("1", "", 1, "")), 1, 1.5
    )
    val feellike = FeelsLike(1.1, 1.1, 1.1, 1.1)
    val listOfDay = mutableListOf(
        Daily(
            1,
            1.1,
            1,
            feellike,
            1,
            1.1,
            1,
            1,
            1.1,
            1,
            1.1,
            1.1,
            1,
            1,
            Temp(1.1, 1.1, 1.1, 1.1, 1.1, 1.1),
            1.1,
            listOf(Weather("", "", 1, "")),
            1,
            1.0,
            1.1
        )
    )
    val listofhout = mutableListOf(
        Hourly(
            1, 1.1, 1, 1.1, 1, 1.1, 1,
            RainX(1.1), Snow(1.1), 1.1, 1.1, 1, listOf(Weather("", "", 1, "")), 1, 1.1, 1.1
        )
    )
    val forcast = Forecast(
        listOfAlert,
        current,
        listOfDay,
        listofhout,
        1.1,
        1.1,
        listOf(Minutely(1, 1.1)),
        "s",
        1
    )
    lateinit var repo: Repostiory

    lateinit var fakeRemote :ApiClient
lateinit var fakeLocalSource: ConLocalSource
//    val remoteSource = ApiClient(forcast)
//    val fakeLocalSource = ConLocalSource(listOfAlert, listOfFav)
@Before
fun setup(){
    fakeLocalSource= ConLocalSource(listOfAlert, listOfFav)
    fakeRemote= ApiClient(forcast)
    repo= Repostiory(fakeRemote,fakeLocalSource)
}
    @Test
    fun getFromNetwork_apiPrameters_succeeds() = runBlockingTest(){
        val responseFlow = repo.getFromNetwork(0.0, 0.0, "en", "metric")
        val responseList = responseFlow.toList()
        assertEquals(1, responseList.size)
        assertEquals(forcast, responseList[0].body())
    }
    @Test
    fun getFromDataBase_favList()=runBlockingTest {

        fakeLocalSource.insert(fav1)
        val favoritesFlow = repo.getFromDatabase()
        val favoritesList = favoritesFlow.toList()
        assertEquals(1, favoritesList.size)
        assertEquals(fav1, favoritesList[0][0])
    }
    @Test
    fun RemoveFromDataBase()=runBlockingTest {

        repo.removeFromFavorites(fav1)
        val removedFavoritesFlow = repo.getFromDatabase()
        val removedFavoritesList = removedFavoritesFlow.toList()
        assertEquals(0, removedFavoritesList[0].size)
    }

    @Test
    fun getFromDatabaseAlart_AlertList()=runBlockingTest {

        fakeLocalSource.insertAlart(alert1)
        val alertsFlow = repo.getFromDatabaseAlart()
        val alertsList = alertsFlow.toList()
        assertEquals(1, alertsList.size)
        assertEquals(alert1, alertsList[0][0])
    }
    @Test
    fun addtoAlert()= runBlockingTest{

        repo.addToAlart(alert1)
        repo.addToAlart(alert2)
        val newAlertsFlow = repo.getFromDatabaseAlart()
        val newAlertsList = newAlertsFlow.toList()
        assertEquals(1, newAlertsList.size)
        assertEquals(alert1, newAlertsList[0][2])
        assertEquals(alert2, newAlertsList[0][3])


    }

}