package com.ignmonlop.worldoftanks.Retrofit.Data

data class Tank(
    val id: Int,
    val model: String,
    val weigth: Int,
    val imageURL: String,
    val originCountry: String,
    val Manufacturer: String,
    val zone: MutableList<Zone>
){

}
