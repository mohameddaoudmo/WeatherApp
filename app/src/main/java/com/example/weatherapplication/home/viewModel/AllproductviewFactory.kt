package com.example.designpattern.allproduct.viewModel

import android.content.Context
import androidx.lifecycle.*
import com.example.designpattern.model.RepositioryInterface

class AllproductviewFactory(private val _repo: RepositioryInterface) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass::class.java.isInstance(ForcastViewModel::class.java)) {
            ForcastViewModel(_repo ) as T
        } else {
            throw IllegalArgumentException("View Model class not found")
        }
    }
}