package com.ignmonlop.worldoftanks.Retrofit.Common

import com.ignmonlop.worldoftanks.Retrofit.Service.WOTService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object Constants {
    // const val BASE_URL = "http://juguetes.navelsystems.com"
    const val BASE_URL = "https://juguetes.free.beeceptor.com"

    const val TANKS_PATH = "/tanks"
    const val ZONES_PATH = "/zones"

    val service: WOTService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WOTService::class.java)
    }
}