package com.example.megarideapplication.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.megarideapplication.R

class RidesDetailsCarActivity : AppCompatActivity() {
    private lateinit var backPress: ImageView
    private lateinit var profileImage: ImageView
    private lateinit var phoneImage: ImageView
    private lateinit var nameTextView: TextView
    private lateinit var carModel: TextView
    private lateinit var numberPlate: TextView
    private lateinit var availableSeats: TextView
    private lateinit var carModelTextview: TextView
    private lateinit var numberPlateTextView: TextView
    private lateinit var availableSeatsTextView: TextView
    private lateinit var carIcon: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rides_details_car)

        backPress= findViewById(R.id.backpress)
        profileImage= findViewById(R.id.hideimage)
        phoneImage= findViewById(R.id.hidephone)
        nameTextView= findViewById(R.id.hidename)
        carModel= findViewById(R.id.hidecarmodel)
        numberPlate= findViewById(R.id.hidenumberplate)
        availableSeats= findViewById(R.id.hideavailseats)
        carModelTextview= findViewById(R.id.hidetextviewformodel)
        numberPlateTextView= findViewById(R.id.hidetvfornoplate)
        availableSeatsTextView= findViewById(R.id.hidetvforavailseats)
        carIcon= findViewById(R.id.caricon)
        backPress.setOnClickListener {
            finish()
        }
//        val timer = object : CountDownTimer(500, 500) {
//            override fun onTick(millisUntilFinished: Long) {
//
//                carIcon.visibility = View.VISIBLE
//                profileImage.visibility = View.GONE
//                phoneImage.visibility = View.GONE
//                nameTextView.visibility = View.GONE
//                carModel.visibility = View.GONE
//                numberPlate.visibility = View.GONE
//                availableSeats.visibility = View.GONE
//                carModelTextview.visibility = View.GONE
//                numberPlateTextView.visibility = View.GONE
//                availableSeatsTextView.visibility = View.GONE
//            }
//
//            override fun onFinish() {
//
//                carIcon.visibility = View.GONE
//                profileImage.visibility = View.VISIBLE
//                phoneImage.visibility = View.VISIBLE
//                nameTextView.visibility = View.VISIBLE
//                carModel.visibility = View.VISIBLE
//                numberPlate.visibility = View.VISIBLE
//                availableSeats.visibility = View.VISIBLE
//                carModelTextview.visibility = View.VISIBLE
//                numberPlateTextView.visibility = View.VISIBLE
//                availableSeatsTextView.visibility = View.VISIBLE
//            }
//        }
//        timer.start()
    }
}