package com.example.taladapp.ui.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.taladapp.R


class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val sharedPreferences: SharedPreferences = getSharedPreferences("shared pref", Context.MODE_PRIVATE)
        val username = sharedPreferences.getString("username", "").toString()
        Toast.makeText(applicationContext, "Welcome $username!", Toast.LENGTH_SHORT).show()

        // Log out card
        val exit: CardView = findViewById(R.id.cardExit)
        exit.setOnClickListener {
            with(sharedPreferences.edit()) {
                clear()
                apply()
            }
            startActivity(Intent(this@HomeActivity, LoginActivity::class.java))
        }

        // Emergency call card
        val cardOrderDetails: CardView = findViewById(R.id.cardEmergencyCall)
        cardOrderDetails.setOnClickListener {
            val dialIntent = Intent(Intent.ACTION_DIAL)
            dialIntent.data = Uri.parse("tel:" + "666")
            startActivity(dialIntent)
        }

        // Report Accident card
        val cardReportAccident: CardView = findViewById(R.id.cardReportAccident)
        cardReportAccident.setOnClickListener {
            startActivity(Intent(this@HomeActivity, ReportAccidentActivity::class.java))
        }

        // All Reports card
        val cardAllReports: CardView = findViewById(R.id.cardAllReports)
        cardAllReports.setOnClickListener {
            startActivity(Intent(this@HomeActivity, ListReportsActivity::class.java))
        }

        // Weekly Passdown card
        val cardWeeklyPassdown: CardView = findViewById(R.id.cardWeeklyPassdown)
        cardWeeklyPassdown.setOnClickListener {
            startActivity(Intent(this@HomeActivity, WeeklyPassdownActivity::class.java))
        }

    }
}
