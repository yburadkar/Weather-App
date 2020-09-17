package com.yb.corethree.features.weather.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yb.corethree.databinding.ItemCityBinding

class CitiesAdapter(): ListAdapter<CitiesAdapter.CityItem, CitiesAdapter.CityViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val binding = ItemCityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class CityViewHolder(private val binding: ItemCityBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CityItem) {
            with(binding) {
                tvName.text = item.name
                tvDescription.text = item.description
                tvTemp.text = item.temp
                tvWindSpeed.text = item.speed
            }
        }
    }

    data class CityItem(
        val name: String,
        val temp: String,
        val description: String,
        val speed: String,
        val direction: Float
    )

    companion object {

        private val DIFF_CALLBACK = object: DiffUtil.ItemCallback<CityItem>() {

            override fun areItemsTheSame(oldItem: CityItem, newItem: CityItem): Boolean = oldItem === newItem
            override fun areContentsTheSame(oldItem: CityItem, newItem: CityItem): Boolean = oldItem == newItem

        }
    }

}