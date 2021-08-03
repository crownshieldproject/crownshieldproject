package com.application.News.di

import android.util.Log
import com.application.News.BuildConfig
import com.application.News.service.ApiService
import com.application.News.util.Constants
import com.application.News.util.UtilsDefault
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class ApiModule {

    @Provides
    @Singleton
    internal fun provideInterceptor(): Interceptor {
        return Interceptor { chain ->
            val original = chain.request()
            var request: Request? = null
            try {

                if (UtilsDefault.getSharedPreferenceString(Constants.JWT_TOKEN) != null) {
                    val token = UtilsDefault.getSharedPreferenceString(Constants.JWT_TOKEN)!!
                    request = chain.request().newBuilder()
                        .addHeader("Content-Type", "application/x-www-form-urlencoded")
                        .addHeader("authorization", token)
                        .method(original.method, original.body)
                        .build()
                    Log.d("authorization", ""
                            + UtilsDefault.getSharedPreferenceString(Constants.JWT_TOKEN))
                } else {
                    request = chain.request().newBuilder()
                        .addHeader("Content-Type", "application/x-www-form-urlencoded")
                        .method(original.method, original.body)
                        .build()

                }


            } catch (authFailureError: Exception) {
                authFailureError.printStackTrace()
            }

            chain.proceed(request!!)
        }
    }

    @Provides
    @Singleton
    internal fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
        return gsonBuilder.create()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(interceptor: Interceptor): OkHttpClient {

        val okHttpBuilder = OkHttpClient.Builder()
        okHttpBuilder.interceptors().add(interceptor)
        okHttpBuilder.readTimeout(60, TimeUnit.SECONDS)
        okHttpBuilder.connectTimeout(60, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            okHttpBuilder.interceptors().add(logging)
        }

        return okHttpBuilder.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BuildConfig.APP_BASE_URL)
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)
}