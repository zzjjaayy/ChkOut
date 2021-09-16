package com.jay.chkout.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jay.chkout.network.FakeStoreApi
import kotlinx.coroutines.launch

class NetworkViewModel : ViewModel() {

    private val _status = MutableLiveData<String>()
    val status: LiveData<String> = _status


    init {
        getProducts()
    }

    private fun getProducts() {
        viewModelScope.launch {
            try {
                val response = FakeStoreApi.retroFitService.getProducts()
                _status.value = response
            } catch (e : Exception) {
                _status.value = e.toString()
            }
        }
    }
}