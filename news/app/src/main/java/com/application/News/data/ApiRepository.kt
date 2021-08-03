package com.application.News.data

import com.application.News.service.ApiService
import com.application.News.ui.model.CategoryResponse
import com.application.News.ui.model.HomeResponse
import com.application.News.ui.model.LogoResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ApiRepository @Inject constructor(val apiService: ApiService){

    fun getLogo(): Flow<LogoResponse> {
        return flow {
            val response = apiService.getLogo()
            emit(response)
        }.flowOn(Dispatchers.IO)
    }

    fun getCategory(): Flow<CategoryResponse> {
        return flow {
            val response = apiService.getCategory()
            emit(response)
        }.flowOn(Dispatchers.IO)
    }

    fun getHome(): Flow<HomeResponse> {
        return flow {
            val response = apiService.getHome()
            emit(response)
        }.flowOn(Dispatchers.IO)
    }

}