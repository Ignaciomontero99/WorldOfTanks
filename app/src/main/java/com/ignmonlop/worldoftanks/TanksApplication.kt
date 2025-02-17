package com.ignmonlop.worldoftanks

import android.app.Application
import com.ignmonlop.worldoftanks.Retrofit.Data.Tank

class TanksApplication: Application() {
    companion object{
        var favoritos: MutableList<Tank> = mutableListOf()
        private lateinit var instance: TanksApplication

        fun agregarFavorito(tank: Tank) {
            if (!favoritos.contains(tank)) {
                favoritos.add(tank)
            }
        }

        fun eliminarFavorito(tank: Tank) {
            favoritos.remove(tank)
        }

        fun obtenerFavoritos(): List<Tank> {
            return favoritos
        }
    }
}