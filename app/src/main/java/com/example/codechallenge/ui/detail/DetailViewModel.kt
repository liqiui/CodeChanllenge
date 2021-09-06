package com.example.codechallenge.ui.detail

import android.app.Application
import androidx.lifecycle.*
import com.example.codechallenge.data.Show

class DetailViewModel(show: Show, app: Application): AndroidViewModel(app){

    private  val _show = MutableLiveData<Show>()
    val show: LiveData<Show>
        get() = _show

    init{
        _show.value = show
    }
    class Factory( private val show:Show,
                   private  val app: Application
    ): ViewModelProvider.Factory{
        @Suppress("UNCHEKED_CAST")
        override fun <T: ViewModel?> create(modelClass: Class<T>):T{
            if (modelClass.isAssignableFrom(DetailViewModel::class.java)){
                return DetailViewModel(show, app) as T
            }
            throw IllegalAccessException("Unknow View class")
        }
    }
}