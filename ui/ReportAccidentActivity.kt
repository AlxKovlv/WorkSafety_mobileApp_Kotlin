package com.example.taladapp.ui.activity

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.taladapp.R
import com.example.taladapp.data.AccidentReport
import com.example.taladapp.data.AppDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.Calendar

class ReportAccidentActivity : AppCompatActivity() {
    lateinit var edLocation: EditText
    lateinit var edAccidentDetails: EditText
    lateinit var edDescribeAction: EditText
    private lateinit var datePickerDialog: DatePickerDialog
    private lateinit var timePickerDialog: TimePickerDialog
    private lateinit var dateButton: Button
    private lateinit var timeButton: Button
    private lateinit var btnBack: Button
    private lateinit var btnSendReport: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report_accident)

        edLocation = findViewById(R.id.editTextAppLocation)
        edAccidentDetails = findViewById(R.id.editTextAppAccidentDetails)
        edDescribeAction = findViewById(R.id.editTextAppDescribeAction)
        dateButton = findViewById(R.id.buttonAppDate)
        timeButton = findViewById(R.id.buttonAppTime)
        btnBack = findViewById(R.id.ButtonAppBack)
        btnSendReport = findViewById(R.id.buttonSendReport)

        //datepicker
        initDatePicker()
        dateButton.setOnClickListener { datePickerDialog.show() }

        //timePicker
        initTimePicker()
        timeButton.setOnClickListener { timePickerDialog.show() }

        btnBack.setOnClickListener {
            startActivity(
                Intent(
                    this@ReportAccidentActivity,
                    HomeActivity::class.java
                )
            )
        }

        btnSendReport.setOnClickListener {
            val sharedpreferences = getSharedPreferences("shared_prefs", MODE_PRIVATE)
            val loggedInUsername = sharedpreferences.getString("username", null)

            val report = AccidentReport(
                id = 0,
                location = edLocation.text.toString(),
                accidentDetails = edAccidentDetails.text.toString(),
                actionTaken = edDescribeAction.text.toString(),
                date = dateButton.text.toString(),
                time = timeButton.text.toString(),
                username = loggedInUsername ?: "Unknown User",
                isAdminReport = loggedInUsername == "admin@admin.com" // Set the isAdminReport based on admin privilege
            )

            GlobalScope.launch {
                AppDatabase.getInstance(this@ReportAccidentActivity).accidentReportDao()
                    .insert(report)
                runOnUiThread {
                    Toast.makeText(
                        this@ReportAccidentActivity,
                        "Report sent successfully",
                        Toast.LENGTH_LONG
                    ).show()
                    val intent = Intent(this@ReportAccidentActivity, HomeActivity::class.java)
                    startActivity(intent)
                    finish()  // Optional: if you want to remove this activity from the backstack
                }
            }
        }
    }

    private fun initDatePicker() {
        val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, day ->
            dateButton.text = "$day/${month + 1}/$year"
        }
        val cal = Calendar.getInstance()
        val style = AlertDialog.THEME_HOLO_DARK
        datePickerDialog = DatePickerDialog(
            this,
            style,
            dateSetListener,
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.datePicker.minDate = cal.timeInMillis + 86400000
    }

    private fun initTimePicker() {
        val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
            timeButton.text = String.format("%02d:%02d", hour, minute)
        }
        val cal = Calendar.getInstance()
        val style = AlertDialog.THEME_HOLO_DARK
        timePickerDialog = TimePickerDialog(
            this,
            style,
            timeSetListener,
            cal.get(Calendar.HOUR_OF_DAY),
            cal.get(Calendar.MINUTE),
            true
        )
    }
}
