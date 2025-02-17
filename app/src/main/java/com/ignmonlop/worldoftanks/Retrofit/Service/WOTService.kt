package com.ignmonlop.worldoftanks.Retrofit.Service

import com.ignmonlop.worldoftanks.Retrofit.Common.Constants
import com.ignmonlop.worldoftanks.Retrofit.Data.Tank
import com.ignmonlop.worldoftanks.Retrofit.Data.Zone
import retrofit2.http.GET
import retrofit2.http.Headers

interface WOTService {
    @Headers(
        "apiKey: clave"
    )
    @GET(Constants.TANKS_PATH)
    suspend fun getTanks(): List<Tank>

    @Headers(
        "apiKey: clave"
    )
    @GET(Constants.ZONES_PATH)
    suspend fun getZones(): List<Zone>

}