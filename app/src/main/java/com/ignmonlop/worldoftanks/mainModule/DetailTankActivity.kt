package com.ignmonlop.worldoftanks.mainModule

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.ignmonlop.worldoftanks.R
import com.ignmonlop.worldoftanks.Retrofit.Data.Tank
import com.ignmonlop.worldoftanks.TanksApplication
import com.ignmonlop.worldoftanks.databinding.ActivityDetailTanksBinding
import com.ignmonlop.worldoftanks.mainModule.Adapter.ZoneAdapter
import com.ignmonlop.worldoftanks.mainModule.viewModel.ZoneViewModel

class DetailTankActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailTanksBinding
    private lateinit var zoneAdapter: ZoneAdapter
    private lateinit var viewModelZone: ZoneViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailTanksBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val tankId = intent.getIntExtra("id", -1)
        val tank = getTankById(tankId)

        Log.d("DetailTankActivity", "ID recibido: $tankId")

        if (tank != null) {
            displayTankDetails(tank)
        } else {
            Log.e("DetailTankActivity", "Tanque no encontrado con ID: $tankId")
            finish()  // O muestra un mensaje al usuario
        }

        setupRecyclerViews()

        viewModelZone = ViewModelProvider(this)[ZoneViewModel::class.java]
        viewModelZone.fetchZones()

        viewModelZone.zones.observe(this) { zones ->
            if (zones.isEmpty()) {
                Log.d("MainActivity", "Lista de Zonas vacía")
            } else {
                Log.d("MainActivity", "Zonas cargadas: ${zones.size}")
                zoneAdapter.submitList(zones)
            }
        }

        binding.fab.setOnClickListener {
            finish()
        }
    }

    private fun setupRecyclerViews() {
        // Configuración del RecyclerView de zonas
        zoneAdapter = ZoneAdapter(
            onZoneClick = { zone ->
                Log.d("DetailTankActivity", "Zona clickeado: ${zone.name}")
            }
        )
        binding.recyclerViewZone.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewZone.adapter = zoneAdapter
    }

    private fun getTankById(tankId: Int): Tank? {
        val tankList: List<Tank> = TanksApplication.favoritos
        return tankList.find { it.id == tankId }
    }

    private fun displayTankDetails(tank: Tank) {
        binding.tvTankModel.text = tank.model
        binding.tvTankPeso.text = tank.weight.toString()
        binding.tvTankOrigin.text = tank.originCountry
        binding.tvTankManufacturer.text = tank.manufacturer

        //Log.i("prueba123", "${tank}")

        Glide.with(binding.ivTankImg.context)
            .load(tank.imageUrl)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .placeholder(R.drawable.ic_preview)
            .error(R.drawable.ic_android)
            .into(binding.ivTankImg)
    }


}
