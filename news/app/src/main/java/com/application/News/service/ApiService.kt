package com.application.News.service

import com.application.News.ui.model.CategoryResponse
import com.application.News.ui.model.HomeResponse
import com.application.News.ui.model.LogoResponse
import retrofit2.http.GET

interface ApiService {

    /*@POST(ApiUrl.REGISTER)
    suspend fun register(@Body inputParams: InputParams): CommonResponse*/

    @GET(ApiUrl.LOGO)
    suspend fun getLogo(): LogoResponse

    @GET(ApiUrl.CATEGORY)
    suspend fun getCategory(): CategoryResponse

    @GET(ApiUrl.HOME)
    suspend fun getHome(): HomeResponse
}