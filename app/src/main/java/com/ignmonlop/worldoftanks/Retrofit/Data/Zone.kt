package com.ignmonlop.worldoftanks.Retrofit.Data

data class Zone(
    val id: Int,
    val name: String,
    val tank: MutableList<Tank>
){

}
