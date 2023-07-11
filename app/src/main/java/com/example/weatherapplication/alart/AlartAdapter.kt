package com.example.weatherapplication.alart

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapplication.databinding.CustomalartBinding
import com.example.weatherapplication.databinding.CustomcountryBinding
import com.example.weatherapplication.model.Alert
import com.example.weatherapplication.model.Favorite


class AlartAdapter(private val context: Context, val delete: (Alert)->Unit) : ListAdapter<Alert, AlartAdapter.ProductViewHolder>(ProductDiff()) {


    lateinit var binding: CustomalartBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = CustomalartBinding.inflate(inflater)
        return ProductViewHolder(binding)

    }



    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val currentItem: Alert = getItem(position)
        binding.tvEndTime.text ="End time is ${currentItem.endTime.toString()}"
        binding.tvStartTime.text ="Start time is ${currentItem.startTime}"
        binding.tvLandName.text = "Country ${currentItem.land}"
        binding.tvNotificationType.text =currentItem.type
holder.binding.deleteAlart.setOnClickListener {
    delete(currentItem)
}



    }

    inner class ProductViewHolder(var binding: CustomalartBinding) : RecyclerView.ViewHolder(binding.root)
}

class ProductDiff : DiffUtil.ItemCallback<Alert>() {
    override fun areItemsTheSame(oldItem: Alert, newItem: Alert): Boolean {
        return oldItem.startTime == newItem.startTime
    }

    override fun areContentsTheSame(oldItem: Alert, newItem: Alert): Boolean {
        return oldItem == newItem
    }

}