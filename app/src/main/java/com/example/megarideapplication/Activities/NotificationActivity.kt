package com.example.megarideapplication.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.megarideapplication.Adapter.NotificationAdapter
import com.example.megarideapplication.Models.NotificationModel
import com.example.megarideapplication.R

class NotificationActivity : AppCompatActivity() {
    private lateinit var notificationAdapter: NotificationAdapter
    private val notificationList = ArrayList<NotificationModel>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var backPressed: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)

        backPressed= findViewById(R.id.back)
        backPressed.setOnClickListener {
            finish()
        }

        recyclerView = findViewById(R.id.recyclerview)
        notificationAdapter = NotificationAdapter(notificationList)

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = notificationAdapter
        var notify = NotificationModel("Ride available for Lahore", R.drawable.next)
        notificationList.add(notify)
        notify = NotificationModel("Ride available for Karachi", R.drawable.next)
        notificationList.add(notify)
        notify = NotificationModel("Ride available for Lahore", R.drawable.next)
        notificationList.add(notify)
        notificationAdapter.notifyDataSetChanged()



    }
    }
