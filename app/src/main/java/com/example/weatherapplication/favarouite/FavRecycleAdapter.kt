package com.example.weatherapplication.favarouite

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherapplication.databinding.CustomcountryBinding
import com.example.weatherapplication.model.Favorite


class FavRecycleAdapter(private val context: Context, val myListener: (Favorite)->Unit,val delete: (Favorite)->Unit) : ListAdapter<Favorite, FavRecycleAdapter.ProductViewHolder>(ProductDiff()) {


    lateinit var binding: CustomcountryBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = CustomcountryBinding.inflate(inflater)
        return ProductViewHolder(binding)

    }



    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val currentItem: Favorite = getItem(position)
        binding.countryName.text=currentItem.place

        holder.binding.layout.setOnClickListener {
            myListener(currentItem)
        }
        holder.binding.button.setOnClickListener {
            delete(currentItem)
        }
    }

    inner class ProductViewHolder(var binding: CustomcountryBinding) : RecyclerView.ViewHolder(binding.root)
}

class ProductDiff : DiffUtil.ItemCallback<Favorite>() {
    override fun areItemsTheSame(oldItem: Favorite, newItem: Favorite): Boolean {
        return oldItem.place == newItem.place
    }

    override fun areContentsTheSame(oldItem: Favorite, newItem: Favorite): Boolean {
        return oldItem == newItem
    }

}