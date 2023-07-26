package com.example.taladapp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.taladapp.R

class WeeklyPassdownActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weekly_passdown)

        val backButton: Button = findViewById(R.id.buttonBack)

        backButton.setOnClickListener {
            // Navigate back to the HomeActivity when the back button is clicked
            startActivity(Intent(this, HomeActivity::class.java))
            finish() // Optional: if you want to remove this activity from the backstack
        }
    }
}
