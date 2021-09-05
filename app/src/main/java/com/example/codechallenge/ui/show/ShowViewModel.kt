package com.example.codechallenge.ui.show

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.codechallenge.data.Data
import com.example.codechallenge.data.sampleData
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.launch

class ShowViewModel : ViewModel() {
    // The internal MutableLiveData Data that stores the most recent data
    private val _data = MutableLiveData<Data>()

    // The external immutable LiveData for the response Data
    val data: LiveData<Data>
        get() = _data

    init {
        getDataFromSample()
    }

    //getting data from local data for testing
    private fun getDataFromSample() {

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        val jsonAdapter = moshi.adapter(Data::class.java)
        val sampleData = jsonAdapter.fromJson(sampleData)

        _data.value = sampleData ?:Data(emptyList(), 0, 0, 0,)
    }
}