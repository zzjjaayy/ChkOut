package com.jay.chkout.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jay.chkout.network.FakeStoreApi
import com.jay.chkout.network.Product
import kotlinx.coroutines.launch

class NetworkViewModel : ViewModel() {

    private val _productsList = MutableLiveData<List<Product>>()
    val productsList : LiveData<List<Product>> = _productsList

    init {
        getProducts()
    }

    private fun getProducts() {
        viewModelScope.launch {
            try {
                val response = FakeStoreApi.retroFitService.getProducts()
                _productsList.value = response
            } catch (e : Exception) {
                Log.d("jayischecking", e.toString())
            }
        }
    }
}