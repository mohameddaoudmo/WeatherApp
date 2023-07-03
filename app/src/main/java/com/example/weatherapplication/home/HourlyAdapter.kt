package com.example.weatherapplication.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherapplication.R
import com.example.weatherforecastapp.ui.home.model.Hourly
import com.google.type.DateTime
import java.text.SimpleDateFormat
import java.util.*

class HourlyAdapter {
}
class RecyclerAdapter(private val context: Context):ListAdapter<Hourly,RecyclerAdapter.HourViewHolder>(ProductDiff()) {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourViewHolder {
        return HourViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.hourlycustomitem, parent, false)
        )
    }



    override fun onBindViewHolder(holder: HourViewHolder, position: Int) {
        val hourly = getItem(position)
        val sdf = SimpleDateFormat("hh:00 aaa")
        val date = Date(hourly.dt.toLong() * 1000)
        holder.hour.text =sdf.format(date)
        holder.temprature.text= hourly.temp.toString()
        when (hourly.weather[0].icon) {
            "01d" -> holder.Image.setImageResource(com.example.weatherapplication.R.drawable.ic_clear_day)
            "02d" -> holder.Image.setImageResource(com.example.weatherapplication.R.drawable.ic_few_clouds)
            "03d" -> holder.Image.setImageResource(com.example.weatherapplication.R.drawable.ic_cloudy_weather)
            "09d" -> holder.Image.setImageResource(com.example.weatherapplication.R.drawable.rainy)
            "10d" -> holder.Image.setImageResource(com.example.weatherapplication.R.drawable.rain_svgrepo_com)
            "11d" ->holder.Image.setImageResource(com.example.weatherapplication.R.drawable.ic_storm_weather)
            "13d" -> holder.Image.setImageResource(com.example.weatherapplication.R.drawable.ic_snow_weather)
            "01n" -> holder.Image.setImageResource(com.example.weatherapplication.R.drawable.ic_clear_day)
            "03n" -> holder.Image.setImageResource(com.example.weatherapplication.R.drawable.ic_mostly_cloudy)
            "04d" -> holder.Image.setImageResource(com.example.weatherapplication.R.drawable.ic_mostly_cloudy)

            "09n" -> holder.Image.setImageResource(com.example.weatherapplication.R.drawable.rainy)

        }







    }

    inner class HourViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val Image: ImageView = itemView.findViewById(R.id.imagehourly)
        val hour: TextView = itemView.findViewById(R.id.timeTextView)
        val temprature: TextView = itemView.findViewById(R.id.temperatureTextView)

    }
}

class ProductDiff : DiffUtil.ItemCallback<Hourly>() {
    override fun areItemsTheSame(oldItem: Hourly, newItem: Hourly): Boolean {
        return oldItem.dt == newItem.dt
    }

    override fun areContentsTheSame(oldItem: Hourly, newItem: Hourly): Boolean {
        return oldItem == newItem
    }

}