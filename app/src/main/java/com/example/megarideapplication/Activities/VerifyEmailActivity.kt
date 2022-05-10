package com.example.megarideapplication.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.example.megarideapplication.R

class VerifyEmailActivity : AppCompatActivity() {
    private lateinit var verifyBtn: Button
    private lateinit var backPressed: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify_email)
        verifyBtn= findViewById(R.id.verifyemail)
        backPressed= findViewById(R.id.baacck)
        backPressed.setOnClickListener {
            finish()
        }
        verifyBtn.setOnClickListener {
            val intent = Intent(this,ChangepasswordActivity::class.java)
            startActivity(intent)
        }
    }
}