package com.example.weatherappfragment.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherappfragment.R
import com.example.weatherappfragment.model.roomEntity.Favorites
import com.example.weatherappfragment.util.MyNavigationListener

class FavoriteCitiesAdapter(private var favoriteCities: List<Favorites>, private val listener: MyNavigationListener<Favorites>) :
    RecyclerView.Adapter<FavoriteCitiesAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cityName: TextView = itemView.findViewById(R.id.cityNameItem)

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteCitiesAdapter.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.city_item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteCitiesAdapter.ViewHolder, position: Int) {
        val item = favoriteCities[position]
        holder.cityName.text = item.cityName
        holder.itemView.setOnClickListener {
            listener.navigateToDetail(item)
        }
    }

    override fun getItemCount() = favoriteCities.size

    fun setData(newCities: List<Favorites>) {
        favoriteCities = newCities
        notifyDataSetChanged()
    }

}