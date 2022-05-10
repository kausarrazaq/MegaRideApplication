package com.example.megarideapplication.Fragments

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.megarideapplication.Activities.EditprofileActivity
import com.example.megarideapplication.Activities.LoginActivity
import com.example.megarideapplication.Activities.NotificationActivity
import com.example.megarideapplication.R
import com.example.megarideapplication.utilis.ShareMemory

class Profilefragment : Fragment() {
    private lateinit var editProflie: View
    private lateinit var logOutView: View
    private lateinit var notificationView: View
    private lateinit var userNameTv: TextView
    private lateinit var shareMemory: ShareMemory
    private lateinit var editProfileImage: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profilefragment, container, false)
        initilisation(view)
        return view

    }


    private fun initilisation(view: View) {
        editProflie= view.findViewById(R.id.editprofile)
        logOutView=view.findViewById(R.id.logoutview)
        userNameTv= view.findViewById(R.id.username)
        shareMemory = ShareMemory.mInstence
        shareMemory = ShareMemory.getmInstence()
        notificationView=view.findViewById(R.id.notificationview)
        editProfileImage= view.findViewById(R.id.editprofileimage)


        notificationView.setOnClickListener {
            activity?.let {
                val intent = Intent(it, NotificationActivity::class.java)
                it.startActivity(intent)
            }
        }
        editProflie.setOnClickListener {
            activity?.let {
                val intent = Intent(it, EditprofileActivity::class.java)
                it.startActivity(intent)
            }
        }

        logOutView.setOnClickListener {
            activity?.let {
                val intent: Intent = Intent(it, LoginActivity::class.java)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intent)
                shareMemory.deleteAllSharedPrefs()
            }
        }
    }
    override fun onResume() {
        super.onResume()
        userNameTv.text=shareMemory.userName
        Glide.with(this)
            .load(shareMemory.profileImageUrl)
            .placeholder(R.drawable.profileicon)
            .into(editProfileImage)

    }

}