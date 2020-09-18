package com.yb.corethree.features.weather.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yb.corethree.databinding.ItemCityBinding
import com.yb.corethree.models.City

class CitiesAdapter(
    private val resultClickAction: (City) -> Unit
) : ListAdapter<City, CitiesAdapter.CityViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val binding = ItemCityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CityViewHolder(private val binding: ItemCityBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: City) {
            with(binding) {
                tvName.text = item.name
                tvDescription.text = item.description
                tvTemp.text = item.temp
                tvWindSpeed.text = item.speed
                ivWindDirection.setImageResource(item.windIcon)
                root.setOnClickListener { resultClickAction.invoke(item) }
            }
        }
    }

    companion object {

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<City>() {

            override fun areItemsTheSame(oldItem: City, newItem: City): Boolean = oldItem === newItem
            override fun areContentsTheSame(oldItem: City, newItem: City): Boolean = oldItem == newItem

        }
    }

}