package com.example.asteroidradar.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.asteroidradar.domain.Asteroid
import com.example.asteroidradar.databinding.AsteroidItemBinding

class AsteroidAdapter(val clickListener: AsteroidListener): ListAdapter<Asteroid, AsteroidAdapter.ViewHolder>(AsteroidDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item,clickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class  ViewHolder private constructor(val binding: AsteroidItemBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(
            item: Asteroid,
            clickListener: AsteroidListener
        ) {
            binding.asteroid = item
            binding.clickListener = clickListener
            binding.executePendingBindings()

        }
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding= AsteroidItemBinding.inflate(layoutInflater,parent,false)
                return ViewHolder(binding)
            }
        }
    }
}

class AsteroidDiffCallback :DiffUtil.ItemCallback<Asteroid>(){
    override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
        return  oldItem == newItem
    }
}

class AsteroidListener(val clickListener: (asteroid:Asteroid)->Unit){
    fun onClick(asteroid: Asteroid) = clickListener(asteroid)
}