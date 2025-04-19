package com.farimarwat.speedtest.domain.usecase

import com.farimarwat.speedtest.domain.model.ServersResponse
import com.farimarwat.speedtest.domain.repository.ServersRepository

class FetchServersUseCase(private val repository: ServersRepository) {
    operator suspend fun invoke(onSuccess:(ServersResponse)->Unit,
                                onError:(Exception)->Unit){
        repository.fetchServers(onSuccess,onError)
    }
}