package com.example.weatherapplication.favarouite

import android.app.Activity
import android.content.Intent
import android.location.Geocoder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.designpattern.allproduct.viewModel.AllproductviewFactory
import com.example.designpattern.allproduct.viewModel.ForcastViewModel
import com.example.designpattern.db.ConLocalSource
import com.example.designpattern.model.Repostiory
import com.example.designpattern.network.ApiClient
import com.example.weatherapplication.FavHome
import com.example.weatherapplication.MapssActivity
import com.example.weatherapplication.databinding.FragmentFavBinding
import com.example.weatherapplication.home.HomeFragment
import com.example.weatherapplication.model.Favorite
import kotlinx.coroutines.launch


class FavFragment : Fragment() {
    lateinit var recyclerAdapter: FavRecycleAdapter

    lateinit var binding: FragmentFavBinding
    var longitude: Double? = 1.0
    private lateinit var myLayoutManager: LinearLayoutManager

    lateinit var geocoder: Geocoder
    lateinit var forcastViewModel: ForcastViewModel
    lateinit var forcastViewModelFactory: AllproductviewFactory

    var latitude: Double? = 1.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myLayoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)

        binding.floatingActionButton.setOnClickListener {

            val intent = Intent(requireContext(), MapssActivity::class.java)
            startActivityForResult(intent, 1)
        }
        forcastViewModelFactory =
            AllproductviewFactory(Repostiory(ApiClient, ConLocalSource(requireContext())))
        forcastViewModel = ViewModelProvider(
            this,
            forcastViewModelFactory
        ).get(ForcastViewModel::class.java)
        forcastViewModel.getSavedProducts()
        geocoder = Geocoder(requireContext())
//        recyclerAdapter = FavRecycleAdapter(requireContext(),) {
//            forcastViewModel.getSavedProducts()
//
//        }
        recyclerAdapter = FavRecycleAdapter(requireContext(), { favorite ->
            val intent = Intent(context, FavHome::class.java)
            intent.putExtra("lat",favorite.latitude )
            intent.putExtra("long",favorite.longitude)
            intent.putExtra("Place",favorite.place)
            startActivity(intent)
        }, { favorite ->
            println("$favorite in favroite")
            forcastViewModel.deleteFromFav(favorite)
                forcastViewModel.getSavedProducts()

        })
        binding.recyclerView.apply {
            adapter = recyclerAdapter
            layoutManager = myLayoutManager
        }
        lifecycleScope.launch {
            forcastViewModel.savedProductsStateFlow.collect { list ->

                    recyclerAdapter.submitList(list)

            }

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            val latitude = data?.getDoubleExtra("latitude", 0.0) ?: 0.0
            val longitude = data?.getDoubleExtra("longitude", 0.0) ?: 0.0

            this.longitude = longitude
            this.latitude = latitude
            println("long fav$longitude")
            println("loc $latitude")
            try {
                val x = geocoder.getFromLocation(latitude, longitude, 5)

                if (x != null && x.size > 0) {
                    var country = x[0].countryName
                    forcastViewModel.addToFavorites(Favorite(country, latitude, longitude))

                    println(x.size)
                }
            } catch (e: Exception) {
            }


        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentFavBinding.inflate(inflater, container, false)
        return binding.root
    }



}