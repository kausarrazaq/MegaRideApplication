package com.example.megarideapplication.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.megarideapplication.Models.HistoryModel
import com.example.megarideapplication.R

class HistoryAdapter(private var context: Context,private val mList: List<HistoryModel>): RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.historylayout, parent, false)
        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val ItemsViewModel = mList[position]
        holder.imageView.setImageResource(ItemsViewModel.image)
        holder.nametv.text=ItemsViewModel.nametv
        holder.locationtv.text=ItemsViewModel.locationtv
        holder.fromtv.text=ItemsViewModel.fromtv
        holder.totv.text=ItemsViewModel.totv
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        var imageView: ImageView = itemView.findViewById(R.id.locationtrace)
        var nametv: TextView = itemView.findViewById(R.id.nametv)
        var locationtv: TextView= itemView.findViewById(R.id.locationtv)
        var fromtv: TextView= itemView.findViewById(R.id.fromtv)
        var totv:TextView= itemView.findViewById(R.id.totv)
    }

}