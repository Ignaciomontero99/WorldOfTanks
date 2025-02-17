package com.ignmonlop.worldoftanks.mainModule

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ignmonlop.worldoftanks.Retrofit.Data.Tank
import com.ignmonlop.worldoftanks.Retrofit.Data.Zone
import com.ignmonlop.worldoftanks.TanksApplication
import com.ignmonlop.worldoftanks.databinding.ActivityMainBinding
import com.ignmonlop.worldoftanks.mainModule.Adapter.TankAdapter
import com.ignmonlop.worldoftanks.mainModule.Adapter.ZoneAdapter
import com.ignmonlop.worldoftanks.mainModule.viewModel.TankViewModel
import com.ignmonlop.worldoftanks.mainModule.viewModel.ZoneViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    private lateinit var tankAdapter: TankAdapter
    private lateinit var zoneAdapter: ZoneAdapter
    private lateinit var viewModelTank: TankViewModel
    private lateinit var viewModelZone: ZoneViewModel
    private lateinit var sharedPreferences: SharedPreferences
    private val gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        sharedPreferences = getSharedPreferences("WOT_prefs", MODE_PRIVATE)

        setupRecyclerViews()

        val favoritosGuardados = getFavorites()
        TanksApplication.favoritos.clear()
        TanksApplication.favoritos.addAll(favoritosGuardados)

        tankAdapter.submitList(TanksApplication.favoritos)

        viewModelTank = ViewModelProvider(this)[TankViewModel::class.java]
        viewModelTank.fetchTanks()

        viewModelZone = ViewModelProvider(this)[ZoneViewModel::class.java]
        viewModelZone.fetchZones()

        viewModelTank.tanks.observe(this) { tanks ->
            if(tanks.isEmpty()){
                Log.d("MainActivity", "Lista de Tanques vacía")
            } else {
                Log.d("MainActivity", "Tanques cargados: ${tanks.size}")
            }
            tankAdapter.submitList(tanks)
        }

        viewModelZone.zones.observe(this) { zones ->
            if (zones.isEmpty()) {
                Log.d("MainActivity", "Lista de Zonas vacía")
            } else {
                Log.d("MainActivity", "Zonas cargadas: ${zones.size}")
                zoneAdapter.submitList(zones)
            }
        }

        viewModelTank.errorMessage.observe(this) {
            Log.e("MainActivity", "Error: $it")
        }

        viewModelZone.errorMessage.observe(this) {
            Log.e("MainActivity", "Error: $it")
        }

        // FAB para navegar a favoritos
        mBinding.fab.setOnClickListener {
            val intent = Intent(this, Seguimiento::class.java)
            startActivity(intent)
        }
    }

    private fun setupRecyclerViews() {
        // Configuración del RecyclerView de tanques
        tankAdapter = TankAdapter (
            { tank -> onFavoriteClicked(tank) },
            { tank -> onTankClicked(tank) }
        )
        mBinding.recyclerViewTank.layoutManager = LinearLayoutManager(this)
        mBinding.recyclerViewTank.adapter = tankAdapter

        // Configuración del RecyclerView de zonas
        zoneAdapter = ZoneAdapter { zone -> onZoneClicked(zone) }
        mBinding.recyclerViewZone.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        mBinding.recyclerViewZone.adapter = zoneAdapter
    }

    private fun onTankClicked(tank: Tank) {
        Log.d("onTankClicked", "Tank ID: ${tank.id}")  // Verifica si el ID es correcto aquí
        TanksApplication.agregarFavorito(tank)
        val intent = Intent(this, DetailTankActivity::class.java)
        intent.putExtra("id", tank.id)
        startActivity(intent)
    }


    private fun onZoneClicked(zone: Zone) {
        val intent = Intent(this, DetailZoneActivity::class.java).apply {
            putExtra("ZONE_ID", zone.id)
        }
        startActivity(intent)
    }


    private fun onFavoriteClicked(tank: Tank) {
        if (TanksApplication.favoritos.contains(tank)) {
            TanksApplication.eliminarFavorito(tank)
        } else {
            TanksApplication.agregarFavorito(tank)
        }

        // Guardar los favoritos actualizados en SharedPreferences
        saveFavorites(TanksApplication.favoritos)

        // Asegurar actualización visual en RecyclerView sin causar errores
        val position = tankAdapter.currentList.indexOf(tank)
        if (position != -1) {
            mBinding.recyclerViewTank.post {
                tankAdapter.notifyItemChanged(position)
            }
        }
    }

    fun saveFavorites(favorites: List<Tank>) {
        val json = gson.toJson(favorites)
        sharedPreferences.edit().putString("favorites_list", json).apply()
        Log.d("PreferencesManager", "Favoritos guardados: $json")
    }

    fun getFavorites(): MutableList<Tank> {
        val json = sharedPreferences.getString("favorites_list", null)
        Log.d("PreferencesManager", "Recuperando favoritos: $json")
        if (json == null) {
            Log.d("PreferencesManager", "No hay favoritos guardados.")
        }
        val type = object : TypeToken<MutableList<Tank>>() {}.type
        return gson.fromJson(json, type) ?: mutableListOf()
    }


}