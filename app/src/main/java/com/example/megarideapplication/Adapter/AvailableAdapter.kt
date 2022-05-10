package com.example.megarideapplication.Adapter

import android.content.Context
import android.content.Intent
import android.transition.Slide
import android.transition.TransitionManager
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.megarideapplication.Activities.AnimationActivity
import com.example.megarideapplication.Activities.RidesDetailsCarActivity
import com.example.megarideapplication.Models.AvailableModel
import com.example.megarideapplication.R


class AvailableAdapter(private var context: Context, private val mList: List<AvailableModel>): RecyclerView.Adapter<AvailableAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.availablelayout, parent, false)
        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val ItemsViewModel = mList[position]
        holder.imageView.setImageResource(ItemsViewModel.image)
        holder.datetv.text=ItemsViewModel.date
        holder.timetv.text=ItemsViewModel.time
        holder.fromtv.text=ItemsViewModel.from
        holder.totv.text=ItemsViewModel.to
        holder.locationfrom.text=ItemsViewModel.fromlocation
        holder.locationto.text= ItemsViewModel.tolocation
        holder.itemView.setOnClickListener {
            context.let {
                val i = Intent(context, RidesDetailsCarActivity::class.java)
                i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(i)

//                 context.applicationContext.startActivity(intent)

            }
        }
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        var imageView: ImageView = itemView.findViewById(R.id.location)
        var datetv: TextView = itemView.findViewById(R.id.datetv)
        var locationfrom: TextView = itemView.findViewById(R.id.textfrom)
        var fromtv: TextView = itemView.findViewById(R.id.fromtextview)
        var totv: TextView = itemView.findViewById(R.id.totextview)
        var timetv: TextView= itemView.findViewById(R.id.timetv)
        var locationto: TextView= itemView.findViewById(R.id.textto)
    }

}