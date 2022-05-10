package com.example.megarideapplication.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.transition.Slide
import android.transition.TransitionManager
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import com.example.megarideapplication.R

class AnimationActivity : AppCompatActivity() {
    private lateinit var caricon: ImageView
    private lateinit var realtivelayout: RelativeLayout
    private lateinit var button: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animation)
        caricon= findViewById(R.id.cariicon)
        realtivelayout= findViewById(R.id.relative)
        button= findViewById(R.id.button)
        button.setOnClickListener {
            val slide= Slide ()
            slide.slideEdge= Gravity.END
            TransitionManager.beginDelayedTransition(realtivelayout, slide)
            caricon.visibility= View.VISIBLE
            Handler().postDelayed({
                val intent = Intent(this, RidesDetailsCarActivity::class.java)
                startActivity(intent)
                finish()
            },500)
        }

    }
}