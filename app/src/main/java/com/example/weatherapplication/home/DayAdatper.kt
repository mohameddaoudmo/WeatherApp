package com.example.weatherapplication.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherapplication.R
import com.example.weatherapplication.databinding.DaycustomitemBinding
import com.example.weatherforecastapp.ui.home.model.Daily
import java.text.SimpleDateFormat
import java.util.*


class DayAdatper(val context: Context, var myListener:(Daily)->Unit):
    ListAdapter<Daily, ProductsViewHolder>(ProductsDiffUtil()) {
    lateinit var binding: DaycustomitemBinding


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {

        val inflater: LayoutInflater =parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding= DaycustomitemBinding.inflate(inflater,parent,false)
        return ProductsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        val currentObject =getItem(position)
        val sdf = SimpleDateFormat("EEEE")
        val date = Date(currentObject.dt.toLong() * 1000)
        holder.binding.dayTextview.text = sdf.format(date)
//        holder.binding.temperatureTextview.text = currentObject.temp.toString()
        holder.binding.weatherTextview.text = currentObject.weather[0].description
        when (currentObject.weather[0].icon) {
//        "10d"->    Glide.with(context).load(R.drawable.rain2gif).into(holder.binding.photoImageview)

                "01d" -> holder.binding.photoImageview.setImageResource(com.example.weatherapplication.R.drawable.ic_clear_day)
            "02d" -> holder.binding.photoImageview.setImageResource(com.example.weatherapplication.R.drawable.ic_few_clouds)
            "03d" -> holder.binding.photoImageview.setImageResource(com.example.weatherapplication.R.drawable.ic_cloudy_weather)
            "09d" -> holder.binding.photoImageview.setImageResource(com.example.weatherapplication.R.drawable.rainy)
            "10d" -> holder.binding.photoImageview.setImageResource(com.example.weatherapplication.R.drawable.iconsrain)
            "11d" ->holder.binding.photoImageview.setImageResource(com.example.weatherapplication.R.drawable.ic_storm_weather)
            "13d" -> holder.binding.photoImageview.setImageResource(com.example.weatherapplication.R.drawable.ic_snow_weather)
            "01n" ->holder.binding.photoImageview.setImageResource(com.example.weatherapplication.R.drawable.ic_clear_day)
            "03n" -> holder.binding.photoImageview.setImageResource(com.example.weatherapplication.R.drawable.ic_mostly_cloudy)
            "04d" -> holder.binding.photoImageview.setImageResource(com.example.weatherapplication.R.drawable.ic_mostly_cloudy)

            "09n" -> holder.binding.photoImageview.setImageResource(com.example.weatherapplication.R.drawable.rainy)

        }




        holder.binding.customday.setOnClickListener{
            myListener(currentObject)

        }

    }
}
class ProductsViewHolder(var binding: DaycustomitemBinding): RecyclerView.ViewHolder(binding.root)
//{
//    val title:TextView=view.findViewById(R.id.title_item)
//    val img :ImageView=view.findViewById(R.id.image_item)
//    val constraintLayout:ConstraintLayout = itemView.findViewById(R.id.cons_item)
//
//}
class ProductsDiffUtil: DiffUtil.ItemCallback<Daily>(){
    override fun areItemsTheSame(oldItem: Daily, newItem: Daily): Boolean {
        return oldItem.dt==newItem.dt
    }

    override fun areContentsTheSame(oldItem: Daily, newItem: Daily): Boolean {
        return oldItem==newItem
    }

}