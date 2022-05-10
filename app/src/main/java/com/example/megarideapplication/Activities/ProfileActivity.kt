package com.example.megarideapplication.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.example.megarideapplication.Models.UpdateUserNameModel
import com.example.megarideapplication.R
import com.example.megarideapplication.WebServices.POSTService
import com.example.megarideapplication.utilis.AppURL
import com.example.megarideapplication.utilis.ShareMemory
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.irozon.sneaker.Sneaker
import org.json.JSONException
import org.json.JSONObject

class ProfileActivity : AppCompatActivity() , POSTService.ResponseInterface{
    private lateinit var crossIcon: ImageView
    private lateinit var tickIcon: ImageView
    private lateinit var changeUserNamePasswordEditText: EditText
    private lateinit var shareMemory: ShareMemory
    private var id: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        crossIcon=findViewById(R.id.cross)
        tickIcon= findViewById(R.id.tick)
        changeUserNamePasswordEditText= findViewById(R.id.changeUsername)
        val intent: Intent = intent
        id = intent.getStringExtra("id").toString()
        shareMemory = ShareMemory.mInstence
        shareMemory = ShareMemory.getmInstence()
        tickIcon.setOnClickListener {
            changeusernameFun()
        }
        crossIcon.setOnClickListener {
            finish()
        }
    }

    fun setValidation(): Boolean {
        if (changeUserNamePasswordEditText.text.toString() == "") {
            Sneaker.with(this) // Activity, Fragment or ViewGroup
                .setMessage("Please Enter Your Full Name")
                .setDuration(2000)
                .autoHide(true)
                .sneakError()
            return false
        }

        return true
    }
    private fun changeusernameFun() {
        if (setValidation()) {
            val jsonObject = JSONObject()
            val username = changeUserNamePasswordEditText.text.toString()


            jsonObject.put("user_id",shareMemory.userId)
            jsonObject.put("name", username)



            val postService = POSTService(this, this)
            postService.putData(jsonObject, AppURL.UPDATEUSERNAME_URL)
        }
    }


    override fun getResponse(o: Any?) {
        val `object` = o as JSONObject
        try {
            val status = `object`.getBoolean("status")
            if (status == true) {
                val gsonBuilder = GsonBuilder()
                val gson: Gson = gsonBuilder.create()
                val userData = gson.fromJson(`object`.toString(), UpdateUserNameModel::class.java)
                shareMemory.userId = userData.data.id
                shareMemory.userName = userData.data.name
                finish()
            }
            else {
                val message = `object`.getString("message")
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }
}