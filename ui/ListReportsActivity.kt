package com.example.taladapp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.taladapp.R
import com.example.taladapp.data.AccidentReport
import com.example.taladapp.data.AppDatabase
import com.example.taladapp.ui.adapter.AccidentReportAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListReportsActivity : AppCompatActivity() {
    private lateinit var listViewReports: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_reports)

        listViewReports = findViewById(R.id.listViewBMList)

        findViewById<Button>(R.id.buttonLTDBack).setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }

        // Retrieve the loggedInUsername from shared preferences
        val sharedpreferences = getSharedPreferences("shared_prefs", MODE_PRIVATE)
        val loggedInUsername = sharedpreferences.getString("username", null)

        GlobalScope.launch {
            val reports = if (loggedInUsername == "Manager") {
                // If the user is admin, fetch all accident reports
                AppDatabase.getInstance(this@ListReportsActivity).accidentReportDao().getAllReports()
            } else {
                // If regular user, fetch only their own accident reports
                loggedInUsername?.let {
                    AppDatabase.getInstance(this@ListReportsActivity).accidentReportDao().getUserReports(it)
                } ?: emptyList()
            }

            withContext(Dispatchers.Main) {
                val adapter = AccidentReportAdapter(this@ListReportsActivity, reports, loggedInUsername ?: "")
                listViewReports.adapter = adapter
            }
        }
    }
}
