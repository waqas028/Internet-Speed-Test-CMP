package com.farimarwat.speedtest.domain.repository

import com.farimarwat.speedtest.domain.model.ServersResponse

interface ServersRepository {
    suspend fun fetchServers(
        onSuccess:(ServersResponse)->Unit,
        onError:(Exception)->Unit
    )
}