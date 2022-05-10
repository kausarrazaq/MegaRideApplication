package com.example.megarideapplication.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.example.megarideapplication.R

class ChangepasswordActivity : AppCompatActivity() {
    private lateinit var changepasswordBtn: Button
    private lateinit var backPressed: ImageView
    private lateinit var oldPasswordEditText: EditText
    private lateinit var newPasswordEditText: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_changepassword)
        changepasswordBtn = findViewById(R.id.changepasswordbtn)
        backPressed = findViewById(R.id.bckprsd)
        oldPasswordEditText = findViewById(R.id.oldpasswordedittext)
        newPasswordEditText = findViewById(R.id.newpassword)
        backPressed.setOnClickListener {
            finish()
        }
        changepasswordBtn.setOnClickListener {
            if (setValidation()) {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(
                    applicationContext,
                    "Please Enter Your Old and New Password",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun setValidation(): Boolean {
        if (oldPasswordEditText.text.toString().isEmpty()) {
            oldPasswordEditText.error = "Enter Your Password"
            return false
        } else if (oldPasswordEditText.text.length < 6) {
            oldPasswordEditText.error = "Password Must Be Greater Then 6 Character"
            return false
        } else if (newPasswordEditText.text.toString().isEmpty()) {
            newPasswordEditText.error = "Enter Your New Password"
            return false

        } else {
            return true
        }
    }
}