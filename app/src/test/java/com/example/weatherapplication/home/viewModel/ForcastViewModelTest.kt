package com.example.weatherapplication.home.viewModel

import app.cash.turbine.test
import com.example.designpattern.allproduct.viewModel.ForcastViewModel
import com.example.designpattern.network.NetworkState
import com.example.weatherapplication.MainDispatcherRule
import com.example.weatherapplication.model.Alert
import com.example.weatherapplication.model.FakeRepo
import com.example.weatherapplication.model.Favorite
import com.example.weatherforecastapp.ui.home.model.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ForcastViewModelTest(){
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
    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()
    lateinit var repo: FakeRepo
    lateinit var viewModel: ForcastViewModel
    @Before
    fun setUp() {
        repo = FakeRepo(listOfAlert,listOfFav,forcast)
        viewModel = ForcastViewModel(repo)
    }
    @Test
    fun `test getWeather`() = runBlockingTest {
        viewModel.getWeather(37.7749, -122.4194, "en", "metric")

        val forcast = viewModel.productsStateFlow.first()
        assertTrue(forcast is NetworkState.Success)
        assertNotNull((forcast as NetworkState.Success).myResponse)
    }
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testgetsavedProduct() = runBlockingTest{
        //given
        val fav44 = Favorite("france", 55.1, 44.1)

        viewModel.addToFavorites(fav44)
        viewModel.getSavedProducts()
        var result: List<Favorite> = listOf()
        viewModel.savedProductsStateFlow.test {
            result = this.awaitItem()
        }
        //when
        assertTrue(result.contains(fav44))

    }
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testAddToFavorites()= runBlockingTest {
        //when
        viewModel.addToFavorites(fav1)
        viewModel.getSavedProducts()
        var result: List<Favorite> = listOf()
        viewModel.savedProductsStateFlow.test {
            result = this.awaitItem()
        }
        //then
        assertTrue(result.contains(fav1))
    }
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testAddToAlert() = runBlockingTest{
        //when
        viewModel.addToAlert(alert1)
        viewModel.getsavedAlert()
        var result: List<Alert> = listOf()
        viewModel.savedalartStateFlow.test {
            result = this.awaitItem()
        }
        assertTrue(result.contains(alert1))

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testDeleteFromFav() = runBlockingTest {
        // given
        val fav1 = Favorite("Egypt", 55.1, 44.1)
        val fav2 = Favorite("england", 444.1, 8888.1)
        viewModel.addToFavorites(fav1)
        viewModel.addToFavorites(fav2)

        // when
        viewModel.deleteFromFav(fav1)
        viewModel.getSavedProducts()

        // then
        var savedProducts :List<Favorite> = listOf()
        viewModel.savedProductsStateFlow.test {
            savedProducts = this.awaitItem()
        }
        assertFalse(savedProducts.contains(fav1))
        assertTrue(savedProducts.contains(fav2))
    }
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testDeleteFromAlert() = runBlockingTest {
        // given
        val alert5 = Alert("555", "44", "france", "")
        val alert8 = Alert("444", "444", "Egypt", "")
        viewModel.addToAlert(alert5)
        viewModel.addToAlert(alert8)

        // when
        viewModel.deleteFromFav(alert5)
        viewModel.getsavedAlert()

        // then
        var savedAlerts :List<Alert> = listOf()
        viewModel.savedalartStateFlow.test {
            savedAlerts = this.awaitItem()
        }
        assertFalse(savedAlerts.contains(alert5))
        assertTrue(savedAlerts.contains(alert8))
    }
}