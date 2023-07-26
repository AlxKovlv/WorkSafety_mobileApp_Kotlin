package com.example.taladapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "AccidentReport")
data class AccidentReport(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val location: String,
    val accidentDetails: String,
    val actionTaken: String,
    val date: String,
    val time: String,
    val username: String,
    val isAdminReport: Boolean
)
