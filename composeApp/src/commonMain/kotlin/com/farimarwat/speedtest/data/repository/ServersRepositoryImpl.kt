package com.farimarwat.speedtest.data.repository

import com.farimarwat.speedtest.data.remote.ServersApi
import com.farimarwat.speedtest.domain.model.ServersResponse
import com.farimarwat.speedtest.domain.repository.ServersRepository

class ServersRepositoryImpl(private val api:ServersApi):ServersRepository {
    override suspend fun fetchServers(
        onSuccess: (ServersResponse) -> Unit,
        onError: (Exception) -> Unit
    ) {
        api.fetchServers(onSuccess,onError)
    }

}