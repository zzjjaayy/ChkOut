package com.jay.chkout.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jay.chkout.network.FakeStoreApi
import com.jay.chkout.network.Product
import kotlinx.coroutines.launch

enum class ApiStatus{
    DONE, ERROR, LOADING
}
class NetworkViewModel : ViewModel() {

    private val _productsList = MutableLiveData<List<Product>>()
    val productsList : LiveData<List<Product>> = _productsList

    private val _status = MutableLiveData<ApiStatus>()
    val status : LiveData<ApiStatus> = _status

    init {
        getProducts()
    }

    private fun getProducts() {
        viewModelScope.launch {
            _status.value = ApiStatus.LOADING
            try {
                val response = FakeStoreApi.retroFitService.getProducts()
                _productsList.value = response
                _status.value = ApiStatus.DONE
            } catch (e : Exception) {
                _status.value = ApiStatus.ERROR
                Log.d("jayischecking", e.toString())
            }
        }
    }
}