package com.ignmonlop.worldoftanks.Retrofit.Service

import com.ignmonlop.worldoftanks.Retrofit.Common.Constants
import com.ignmonlop.worldoftanks.Retrofit.Data.Tank
import com.ignmonlop.worldoftanks.Retrofit.Data.Zone
import retrofit2.http.GET

interface WOTService {
    @GET(Constants.TANKS_PATH)
    suspend fun getTanks(): List<Tank>

    @GET(Constants.ZONES_PATH)
    suspend fun getZones(): List<Zone>

}