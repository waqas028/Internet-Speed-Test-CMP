package com.farimarwat.speedtest.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farimarwat.speedtest.data.remote.ApiStatus
import com.farimarwat.speedtest.domain.model.ServersResponse
import com.farimarwat.speedtest.domain.usecase.FetchServersUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val fetchServersUseCase: FetchServersUseCase
):ViewModel() {
    private var _fetchServerStatus:MutableStateFlow<ApiStatus<ServersResponse>> = MutableStateFlow(ApiStatus.Empty)
    val fetchServerStatus = _fetchServerStatus.asStateFlow()

    private var fetchServersJob:Job? = null

     fun fetchServers() {
         if (fetchServerStatus.value is ApiStatus.Success &&
             ((fetchServerStatus.value as ApiStatus.Success<ServersResponse>)
                 .data.servers?.isNotEmpty() == true)) {
             return
         }
        fetchServersJob?.cancel()
        fetchServersJob = viewModelScope.launch(Dispatchers.IO) {
            _fetchServerStatus.value = ApiStatus.Loading
            fetchServersUseCase(
                onSuccess = {
                    _fetchServerStatus.value = ApiStatus.Success(it)
                    println(it)
                },
                onError = {
                    println(it)
                    _fetchServerStatus.value = ApiStatus.Error(it.toString())
                }
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        fetchServersJob?.cancel()
        fetchServersJob = null
    }
}