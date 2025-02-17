package com.ignmonlop.worldoftanks.Retrofit.Data

data class Tank(
    val id: Int,
    val model: String,
    val weight: Int,
    val imageUrl: String,
    val originCountry: String,
    val manufacturer: String,
    val zone: MutableList<Zone>
)
