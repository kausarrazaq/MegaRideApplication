package com.example.megarideapplication.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.*
import com.example.megarideapplication.Models.SignUpApiModel
import com.example.megarideapplication.R
import com.example.megarideapplication.WebServices.ParameterService
import com.example.megarideapplication.utilis.AppURL
import com.example.megarideapplication.utilis.ShareMemory
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.irozon.sneaker.Sneaker
import org.json.JSONException
import org.json.JSONObject

class SignUpActivity : AppCompatActivity() , ParameterService.ResponseInterfaces, ParameterService.ResponseErrorInterface {
    private lateinit var signInTv: TextView
    private lateinit var signUpBtn: Button
    private lateinit var backPressed :ImageView
    private lateinit var SignupEmailEditText: EditText
    private lateinit var signupPasswordEditText: EditText
    private lateinit var signupUsernameEditText: EditText
    private lateinit var signupPhoneNoEditText: EditText
    private lateinit var shareMemory: ShareMemory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        signInTv= findViewById(R.id.signintv)
        signUpBtn= findViewById(R.id.signupbtn)
        backPressed= findViewById(R.id.backpressed)
        SignupEmailEditText= findViewById(R.id.emailEdittext)
        signupUsernameEditText= findViewById(R.id.fullnameedittext)
        signupPasswordEditText= findViewById(R.id.password2)
        signupPhoneNoEditText= findViewById(R.id.phoneedittext)
        shareMemory = ShareMemory.mInstence
        shareMemory = ShareMemory.getmInstence()

        backPressed.setOnClickListener {
            finish()
        }
        signUpBtn.setOnClickListener {
           signUpApifun()
        }
        signInTv.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
    private fun setValidation(): Boolean {
        if (SignupEmailEditText.text.toString() == "") {
            Sneaker.with(this) // Activity, Fragment or ViewGroup
                .setMessage("Please Enter Your Email")
                .setDuration(2000)
                .autoHide(true)
                .sneakError()
            //etName.requestFocus()
            return false
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(SignupEmailEditText.text.toString()).matches()) {
            Sneaker.with(this) // Activity, Fragment or ViewGroup
                .setMessage("Please Enter Valid Email")
                .setDuration(2000)
                .autoHide(true)
                .sneakError()
            return false
        }
        if (signupPasswordEditText.text.toString()=="") {
            Sneaker.with(this) // Activity, Fragment or ViewGroup
                .setMessage("Please Enter Your Password")
                .setDuration(2000)
                .autoHide(true)
                .sneakError()
            return false
        }
        if (signupPasswordEditText.text.length < 6) {
            Sneaker.with(this) // Activity, Fragment or ViewGroup
                .setMessage("Please Enter Greater then 6 Digit Password")
                .setDuration(2000)
                .autoHide(true)
                .sneakError()
            return false
        }
        if (signupPhoneNoEditText.text.toString()=="") {
            Sneaker.with(this) // Activity, Fragment or ViewGroup
                .setMessage("Please Enter Your Phone No")
                .setDuration(2000)
                .autoHide(true)
                .sneakError()
            return false
        }
        if (signupUsernameEditText.text.toString()=="") {
            Sneaker.with(this) // Activity, Fragment or ViewGroup
                .setMessage("Please Enter Your Full Name")
                .setDuration(2000)
                .autoHide(true)
                .sneakError()
            return false
        }


        return true
    }
    private fun signUpApifun() {
        if (setValidation()) {


            val email = SignupEmailEditText.text.toString()
            val name = signupUsernameEditText.text.toString()
            val password = signupPasswordEditText.text.toString()
            val phone= signupPhoneNoEditText.text.toString()

            val jsonObject = JSONObject()
            jsonObject.put("name", name)
            jsonObject.put("email", email)
            jsonObject.put("password", password)
            jsonObject.put("phone",phone)


            val parameterService = ParameterService(this, this, this)
            parameterService.getData(jsonObject, AppURL.SIGNUP_URL)


        }
    }

    override fun getResponses(o: Any?) {
        val `object` = o as JSONObject
        try {
            val status = `object`.getBoolean("status")
            if (status) {
                val gsonBuilder = GsonBuilder()
                val gson: Gson = gsonBuilder.create()
                val userData = gson.fromJson(`object`.toString(), SignUpApiModel::class.java)
                shareMemory.userId = userData.data.id
                shareMemory.userName = userData.data.name
                shareMemory.userEmail = userData.data.email
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finishAffinity()
            }
            else {
                Sneaker.with(this) // Activity, Fragment or ViewGroup
                    .setMessage("Email must a unique value")
                    .setDuration(2000)
                    .autoHide(true)
                    .sneakError()
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    override fun getError(o: Any?) {
    }
}