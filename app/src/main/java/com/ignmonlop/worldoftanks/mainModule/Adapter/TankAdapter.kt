package com.ignmonlop.worldoftanks.mainModule.Adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.ignmonlop.worldoftanks.R
import com.ignmonlop.worldoftanks.Retrofit.Data.Tank
import com.ignmonlop.worldoftanks.TanksApplication
import com.ignmonlop.worldoftanks.databinding.ItemTankBinding

class TankAdapter(
    private val onFavorite: (Tank) -> Unit
) : ListAdapter<Tank, TankAdapter.TankViewHolder>(JoyDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TankViewHolder {
        val binding = ItemTankBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TankViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TankViewHolder, position: Int) {
        holder.bind(getItem(position), onFavorite)
    }

    class TankViewHolder(private val binding: ItemTankBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(tank: Tank, onFavorite:(Tank) -> Unit) {
            binding.tvTankModel.text = tank.model
            binding.tvTankPeso.text = tank.weight.toString()
            binding.tvTankOrigin.text = tank.originCountry
            binding.tvTankManufacturer.text = tank.manufacturer

            Log.i("prueba123", "${tank}")

            Glide.with(binding.ivTankImg.context)
                .load(tank.imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .placeholder(R.drawable.ic_preview)
                .error(R.drawable.ic_android)
                .into(binding.ivTankImg)


            val isFavorite= TanksApplication.favoritos.contains(tank)
            binding.ivFavorite.isChecked = isFavorite

            binding.ivFavorite.setOnCheckedChangeListener { _, isChecked ->
                if(isChecked){
                    TanksApplication.agregarFavorito(tank)
                }else{
                    TanksApplication.eliminarFavorito(tank)
                }
                onFavorite(tank)
            }
        }
    }
}

class JoyDiffCallback: DiffUtil.ItemCallback<Tank>() {
    override fun areItemsTheSame(oldItem: Tank, newItem: Tank): Boolean = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Tank, newItem: Tank): Boolean = oldItem == newItem
}

