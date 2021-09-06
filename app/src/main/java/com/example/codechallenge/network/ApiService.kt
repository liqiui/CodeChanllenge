package com.example.codechallenge.network

import com.example.codechallenge.data.Data
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private val BASE_URL = "http://codingchallenge.nine.com.au/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()


private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()


interface ApiService{

    //Coroutine version of getting data
    @GET("sample_request.json")
    suspend fun getDataCoroutine(): Data

}


object  Api{
    val retrofitService: ApiService by lazy{
        retrofit.create(ApiService::class.java)
    }
}