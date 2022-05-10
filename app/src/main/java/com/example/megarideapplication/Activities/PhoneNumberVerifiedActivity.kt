package com.example.megarideapplication.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.example.megarideapplication.R

class PhoneNumberVerifiedActivity : AppCompatActivity() {
    private lateinit var backPressed: ImageView
    private lateinit var verifyBtn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone_number_verified)
        backPressed= findViewById(R.id.pressedback)
        verifyBtn= findViewById(R.id.verifyphone)
        verifyBtn.setOnClickListener {
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
            }
        backPressed.setOnClickListener {
            finish()
        }
        }
    }
