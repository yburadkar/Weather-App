package com.yb.corethree.features.weather.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yb.corethree.databinding.ItemDateBinding
import com.yb.corethree.databinding.ItemForecastBinding
import com.yb.corethree.models.ForecastDate
import com.yb.corethree.models.ForecastEntry
import com.yb.corethree.models.ForecastItem
import com.yb.corethree.models.ForecastItem.Companion.FORECAST_DATE
import com.yb.corethree.models.ForecastItem.Companion.FORECAST_ENTRY

class ForecastAdapter : ListAdapter<ForecastItem, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            FORECAST_DATE -> DateViewHolder(ItemDateBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            FORECAST_ENTRY -> ForecastViewHolder(ItemForecastBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            else -> throw Exception("Unknown view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is ForecastDate -> (holder as DateViewHolder).bind(item)
            is ForecastEntry -> (holder as ForecastViewHolder).bind(item)
        }
    }

    override fun getItemViewType(position: Int): Int = getItem(position).type

    class DateViewHolder(private val binding: ItemDateBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(forecastDate: ForecastDate) {
            binding.tvDate.text = forecastDate.date
        }
    }

    class ForecastViewHolder(private val binding: ItemForecastBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(forecast: ForecastEntry) {
            with(binding) {
                tvTime.text = forecast.time
                tvTemp.text = forecast.temp
                tvDescription.text = forecast.description
                tvWindSpeed.text = forecast.windSpeed
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ForecastItem>() {
            override fun areItemsTheSame(oldItem: ForecastItem, newItem: ForecastItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ForecastItem, newItem: ForecastItem): Boolean {
                return oldItem == newItem
            }

        }
    }
}