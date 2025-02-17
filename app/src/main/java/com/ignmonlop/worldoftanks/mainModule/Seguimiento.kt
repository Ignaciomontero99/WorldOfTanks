package com.ignmonlop.worldoftanks.mainModule

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.ignmonlop.worldoftanks.TanksApplication
import com.ignmonlop.worldoftanks.databinding.ActivitySeguimientoBinding
import com.ignmonlop.worldoftanks.mainModule.Adapter.TankAdapter

class Seguimiento : AppCompatActivity() {
    private lateinit var binding: ActivitySeguimientoBinding
    private lateinit var adapter: TankAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySeguimientoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar RecyclerView
        adapter = TankAdapter { tank ->
            TanksApplication.eliminarFavorito(tank)
            adapter.submitList(TanksApplication.obtenerFavoritos().toList())
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        // Cargar solo los favoritos
        adapter.submitList(TanksApplication.obtenerFavoritos().toList())

        // FAB para volver a la actividad principal
        binding.fab.setOnClickListener {
            finish() // Volver sin usar onBackPressed()
        }
    }

}