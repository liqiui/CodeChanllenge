package com.example.codechallenge

import com.example.codechallenge.data.Data
import com.example.codechallenge.network.ApiService

class MockApiService(private val data: Data): ApiService {
    override suspend fun getDataCoroutine() = data
}