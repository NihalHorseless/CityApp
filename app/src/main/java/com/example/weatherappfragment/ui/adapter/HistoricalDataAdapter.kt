package com.example.weatherappfragment.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherappfragment.R
import com.example.weatherappfragment.model.City
import com.example.weatherappfragment.model.roomEntity.Favorites
import com.example.weatherappfragment.model.roomEntity.Forecast
import com.example.weatherappfragment.util.MyNavigationListener

class HistoricalDataAdapter(private var forecasts: List<Forecast>) :
    RecyclerView.Adapter<HistoricalDataAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val logDate: TextView = itemView.findViewById(R.id.log_item_date)
        val logCondition: TextView = itemView.findViewById(R.id.log_item_condition)
        val logTemp: TextView = itemView.findViewById(R.id.log_item_temp)

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HistoricalDataAdapter.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.log_item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoricalDataAdapter.ViewHolder, position: Int) {
        val item = forecasts[position]
        holder.logTemp.text = item.temp.toString()
        holder.logCondition.text = item.condition
        holder.logDate.text = item.dateTime
    }

    override fun getItemCount() = forecasts.size

    fun setData(newLogs: List<Forecast>) {
        forecasts = newLogs
        notifyDataSetChanged()
    }
}