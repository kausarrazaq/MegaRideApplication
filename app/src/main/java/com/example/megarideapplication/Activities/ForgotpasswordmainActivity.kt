package com.example.megarideapplication.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.example.megarideapplication.R
import com.example.megarideapplication.WebServices.ParameterService
import com.example.megarideapplication.utilis.AppURL
import com.irozon.sneaker.Sneaker
import org.json.JSONException
import org.json.JSONObject

class ForgotpasswordmainActivity : AppCompatActivity(),ParameterService.ResponseInterfaces, ParameterService.ResponseErrorInterface  {
    private lateinit var nextBtn: Button
    private lateinit var backPressed: ImageView
    private lateinit var emailedittext: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgotpasswordmain)
        nextBtn = findViewById(R.id.nextbtn)
        backPressed = findViewById(R.id.backpresseed)
        emailedittext = findViewById(R.id.emailEDITText)
        backPressed.setOnClickListener {
            finish()
        }
        nextBtn.setOnClickListener {
                forgotpasswordApi()
            }

    }

    private fun setValidation(): Boolean {
        // Check for a valid email address.
        if (emailedittext.text.toString()=="") {
            Sneaker.with(this) // Activity, Fragment or ViewGroup
                .setMessage("Please Enter Your Email")
                .setDuration(2000)
                .autoHide(true)
                .sneakError()
            return false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailedittext.text.toString()).matches()) {
            Sneaker.with(this) // Activity, Fragment or ViewGroup
                .setMessage("Please Enter Valid Email")
                .setDuration(2000)
                .autoHide(true)
                .sneakError()
            return false

        } else {
            return true
        }
    }

    // code for forgotpasswordApi
    private fun forgotpasswordApi() {
        if (setValidation()) {
            val jsonObject = JSONObject()
            val email = emailedittext.text.toString()

            jsonObject.put("email", email)

            val parameterService = ParameterService(this, this, this)
            parameterService.getData(jsonObject, AppURL.FORGOTPASSWORD_URL)
        }
    }

    override  fun getResponses(o: Any?) {
        val `object` = o as JSONObject
        try {
            val status = `object`.getBoolean("status")
            val message = `object`.getString("message")
            if (status) {
                if (message=="Email sent successfully"){
                }
                val email =emailedittext.text.toString()
                val intent1 = Intent(this, VerifyEmailActivity::class.java)
                intent1.putExtra("email", email)
                startActivity(intent1)

            }
            else {
                val message = `object`.getString("message")
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    override fun getError(o: Any?) {
    }

}