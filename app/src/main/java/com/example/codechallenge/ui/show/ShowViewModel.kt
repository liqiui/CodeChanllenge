package com.example.codechallenge.ui.show

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.codechallenge.data.Data
import com.example.codechallenge.data.Show
import com.example.codechallenge.data.sampleData
import com.example.codechallenge.network.Api
import com.example.codechallenge.network.ApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.launch

class ShowViewModel(val apiService: ApiService = Api.retrofitService) : ViewModel() {
    // The internal MutableLiveData Data that stores the most recent data
    private val _data = MutableLiveData<Data>()
    private val _selectedData = MutableLiveData<Show?>()
    var filteredData = Data(emptyList())

    // The external immutable LiveData for the response Data
    val data: LiveData<Data>
        get() = _data

    val selectedData: LiveData<Show?>
        get() = _selectedData

    init {
//        getDataFromSample()
        getDataFromNetworkCoroutine()
    }

    fun displayDataDetails(show: Show){
        _selectedData.value = show
    }

    fun displayDataDetailComplete(){
        _selectedData.value = null
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

    //Coroutine version of getting data
    private  fun getDataFromNetworkCoroutine(){
        viewModelScope.launch {
            try {
                val originalData = apiService.getDataCoroutine()
                filteredData = Data(payload = originalData.payload.filter { show -> show.drm && show.episodeCount > 0 },
                    skip = originalData.skip, take =  originalData.take, totalRecords = originalData.totalRecords)
                _data.value = filteredData
            }catch (e: Exception){
                _data.value = Data(emptyList())
            }
        }
    }

    fun updateKeyword( keyword: String) {
        val updatedData = Data(payload = filteredData.payload.filter { show -> show.title.contains(keyword, true) || show.description.contains(keyword, true) },
            skip = filteredData.skip, take =  filteredData.take, totalRecords = filteredData.totalRecords)
        _data.value = updatedData
    }

    fun reset(){
        _data.value = filteredData
    }
}
