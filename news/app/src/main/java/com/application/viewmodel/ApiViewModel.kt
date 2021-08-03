package com.application.viewmodel

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.liveData
import com.application.News.data.ApiRepository
import com.application.News.service.Resource
import com.application.News.ui.model.CategoryResponse
import com.application.News.ui.model.HomeResponse
import com.application.News.ui.model.LogoResponse
import com.application.News.util.UtilsDefault
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.collect

class ApiViewModel @ViewModelInject constructor(
    application: Application,
    private val repository: ApiRepository
) : AndroidViewModel(application) {

    val getLogo = liveData<Resource<LogoResponse>> {
        if (UtilsDefault.isOnline()) {
            repository.getLogo()
                .onStart {
                    emit(Resource.loading(data = null))
                }
                .catch {
                    emit(Resource.error(data = null, msg = "Cannot reach server..try again"))
                }
                .collect {
                    emit(Resource.success(it))
                }
        } else {
            emit(Resource.error(data = null, msg = "No internet connection"))
        }

    }

    val getCategory = liveData<Resource<CategoryResponse>> {
        if (UtilsDefault.isOnline()) {
            repository.getCategory()
                .onStart {
                    emit(Resource.loading(data = null))
                }
                .catch {
                    emit(Resource.error(data = null, msg = "Cannot reach server..try again"))
                }
                .collect {
                    emit(Resource.success(it))
                }
        } else {
            emit(Resource.error(data = null, msg = "No internet connection"))
        }

    }

    val getHome = liveData<Resource<HomeResponse>> {
        if (UtilsDefault.isOnline()) {
            repository.getHome()
                .onStart {
                    emit(Resource.loading(data = null))
                }
                .catch {
                    emit(Resource.error(data = null, msg = "Cannot reach server..try again"))
                }
                .collect {
                    emit(Resource.success(it))
                }
        } else {
            emit(Resource.error(data = null, msg = "No internet connection"))
        }

    }

}