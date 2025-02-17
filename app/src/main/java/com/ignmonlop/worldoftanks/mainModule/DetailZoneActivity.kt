package com.ignmonlop.worldoftanks.mainModule

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ignmonlop.worldoftanks.Retrofit.Data.Zone
import com.ignmonlop.worldoftanks.databinding.ActivityDetailZoneBinding
import com.ignmonlop.worldoftanks.mainModule.Adapter.TankAdapter
import com.ignmonlop.worldoftanks.mainModule.Adapter.ZoneAdapter
import com.ignmonlop.worldoftanks.mainModule.viewModel.TankViewModel
import com.ignmonlop.worldoftanks.mainModule.viewModel.ZoneViewModel

class DetailZoneActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailZoneBinding
    private lateinit var zoneAdapter: ZoneAdapter
    private lateinit var tankAdapter: TankAdapter
    private lateinit var viewModelZone: ZoneViewModel
    private lateinit var viewModelTank: TankViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailZoneBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtener el id de la zona desde el Intent
        val zoneId = intent.getIntExtra("id", -1)
        val zone = viewModelZone.zones.value?.find { it.id == zoneId }

        if (zone != null) {
            displayZoneDetails(zone)
        } else {
            Log.e("DetailTankActivity", "Tanque no encontrado con ID: $zoneId")
            finish()  // O muestra un mensaje al usuario
        }

        // Instanciar el ViewModel
        viewModelZone = ViewModelProvider(this).get(ZoneViewModel::class.java)
        viewModelZone.fetchZones()
        // Observador para las zonas
        viewModelZone.zones.observe(this) { zones ->
            if (zones.isEmpty()) {
                Log.d("DetailZoneActivity", "Lista de zonas vacía")
            } else {
                Log.d("DetailZoneActivity", "Zonas cargadas: ${zones.size}")
                zoneAdapter.submitList(zones)
                // Aquí puedes mostrar las zonas en la UI si es necesario
            }
        }
        // Llamar a la función fetchTanks para obtener los tanques
        viewModelTank = ViewModelProvider(this).get(TankViewModel::class.java)
        viewModelTank.fetchTanks()
        // Observador para los tanques
        viewModelTank.tanks.observe(this) { tanks ->
            if (tanks.isEmpty()) {
                Log.d("DetailZoneActivity", "Lista de tanques vacía")
            } else {
                Log.d("DetailZoneActivity", "Tanques cargados: ${tanks.size}")
                tankAdapter.submitList(tanks)
            }
        }

        setupRecyclerView()

        binding.fab.setOnClickListener {
            finish()
        }
    }

    private fun displayZoneDetails(zone: Zone) {
        binding.tvTextZone.text = zone.name

    }

    private fun setupRecyclerView() {
        tankAdapter = TankAdapter(
            onFavorite = { tank ->
                Log.d("DetailZoneActivity", "Favorito clickeado: ${tank.model}")
            },
            onTankClick = { tank ->
                Log.d("DetailZoneActivity", "Tanque clickeado: ${tank.model}")
            }
        )
        binding.recyclerViewTank.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewTank.adapter = tankAdapter
    }

}

