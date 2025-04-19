package com.farimarwat.speedtest.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farimarwat.speedtest.data.remote.ApiStatus
import com.farimarwat.speedtest.domain.model.STProvider
import com.farimarwat.speedtest.domain.model.STServer
import com.farimarwat.speedtest.domain.model.ServersResponse
import com.farimarwat.speedtest.domain.usecase.FetchServersUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val fetchServersUseCase: FetchServersUseCase
):ViewModel() {
    private var _fetchServerStatus:MutableStateFlow<ApiStatus<ServersResponse>> = MutableStateFlow(ApiStatus.Empty)
    val fetchServerStatus = _fetchServerStatus.asStateFlow()

    private var _servers:MutableStateFlow<List<STServer>> = MutableStateFlow(emptyList())
    val servers = _servers.asStateFlow()

    private var _selectedProvider:MutableStateFlow<STProvider?> = MutableStateFlow(null)
    val selectedProvider = _selectedProvider.asStateFlow()

    private var _selectedServer:MutableStateFlow<STServer?> = MutableStateFlow(null)
    val selectedServer = _selectedServer.asStateFlow()

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
                    _selectedProvider.value = it.provider
                    _selectedServer.value = it.servers?.first()
                    _servers.value = it.servers ?: emptyList()
                },
                onError = {
                    _fetchServerStatus.value = ApiStatus.Error(it.toString())
                }
            )
        }
    }

    fun changeSelectedServer(stServer: STServer){
        _selectedServer.value = stServer
    }
    override fun onCleared() {
        super.onCleared()
        fetchServersJob?.cancel()
        fetchServersJob = null
    }
}