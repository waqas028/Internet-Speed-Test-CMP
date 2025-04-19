package com.farimarwat.speedtest.domain.model

data class ServersResponse(
    var provider:STProvider?,
    var servers:List<STServer>?
)