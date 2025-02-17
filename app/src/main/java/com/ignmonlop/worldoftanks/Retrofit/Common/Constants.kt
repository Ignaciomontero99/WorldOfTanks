package com.ignmonlop.worldoftanks.Retrofit.Common

import com.ignmonlop.worldoftanks.Retrofit.Service.WOTService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object Constants {
    // const val BASE_URL = "http://juguetes.navelsystems.com"
    const val BASE_URL = "https://juguetes.free.beeceptor.com"
    //const val BASE_URL = "http://172.30.77.16:8000"

    const val TANKS_PATH = "/tanks"
    //const val TANKS_PATH = "/rest/v1/rpc/get_tanks_with_zones"
    //const val ZONES_PATH = "/rest/v1/rpc/get_zones_with_tanks"
    const val ZONES_PATH = "/zones"

    val service: WOTService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WOTService::class.java)
    }
}