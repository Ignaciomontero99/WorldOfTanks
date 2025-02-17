package com.ignmonlop.worldoftanks.mainModule.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ignmonlop.worldoftanks.Retrofit.Data.Tank
import com.ignmonlop.worldoftanks.Retrofit.Data.Zone
import com.ignmonlop.worldoftanks.databinding.ItemZoneBinding


class ZoneAdapter(
    private val onZoneClick: (Zone) ->Unit
) : ListAdapter<Zone, ZoneAdapter.ZoneViewHolder>(ZoneDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ZoneViewHolder {
        val binding = ItemZoneBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ZoneViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ZoneViewHolder, position: Int) {
        holder.bind(getItem(position), onZoneClick)
    }

    class ZoneViewHolder(private val binding: ItemZoneBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(zone: Zone, onZoneClick: (Zone) -> Unit) {
            binding.tvZoneName.text = zone.name

            binding.root.setOnClickListener {
                onZoneClick(zone) // Llamar al manejador de clic en el tanque
            }
        }


    }

    class ZoneDiffCallback : DiffUtil.ItemCallback<Zone>() {
        override fun areItemsTheSame(oldItem: Zone, newItem: Zone): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Zone, newItem: Zone): Boolean = oldItem == newItem
    }
}
