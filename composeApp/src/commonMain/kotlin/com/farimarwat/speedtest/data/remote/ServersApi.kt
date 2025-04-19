package com.farimarwat.speedtest.data.remote

import com.farimarwat.speedtest.domain.model.LocationCoordinates
import com.farimarwat.speedtest.domain.model.STProvider
import com.farimarwat.speedtest.domain.model.STServer
import com.farimarwat.speedtest.domain.model.ServersResponse
import com.farimarwat.speedtest.utils.Utils
import com.fleeksoft.ksoup.Ksoup
import com.fleeksoft.ksoup.parser.Parser
import com.fleeksoft.ksoup.select.Elements
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.url

class ServersApi(private val client: HttpClient) {

    suspend fun fetchServers(
        onSuccess:(ServersResponse)->Unit,
        onError:(Exception)->Unit
    ){
       try{
           val body:String = client.get {
               url(BASE_URL_SPEEDTEST+"api/android/config.php")
           }.body()
           val doc = Ksoup.parse(body, Parser.xmlParser())
           val client = doc.select("client")
           val stProvider = STProvider(
               client.attr("isp"),
               client.attr("providerName"),
               client.attr("lat"),
               client.attr("lon")
           )
           val servers = doc.getElementsByTag("server")
           if(servers.isNotEmpty()){
               val list = getServers(servers, stProvider)
               val response = ServersResponse(
                   stProvider,
                   list
               )
               onSuccess(response)
           } else {
               onError(Exception("No servers found"))
           }
       }catch (ex:Exception){
           onError(ex)
       }

    }

    private fun getServers(servers: Elements, stProvider: STProvider?): List<STServer> {
        val list = mutableListOf<STServer>()
        for (item in servers) {
            val server = item.select("server")
            var url = server.attr("url")
            if(!url.contains("8080")){
                url = url.replace(":80", ":8080")
            }
            val stServer = STServer(
                url,
                server.attr("lat"),
                server.attr("lon"),
                server.attr("name"),
                server.attr("sponsor")
            )
            stProvider?.let {
                val from = LocationCoordinates(
                    stProvider.lat?.toDouble()!!,
                    stProvider.lon?.toDouble()!!
                )
                val to = LocationCoordinates(
                    stServer.lat?.toDouble()!!,
                    stServer.lon?.toDouble()!!
                )

                stServer.distance = Utils.calculateDistance(from,to)
            }
            list.add(stServer)
        }
        return list
    }

    companion object {
        val BASE_URL_SPEEDTEST = "https://www.speedtest.net/"
    }
}