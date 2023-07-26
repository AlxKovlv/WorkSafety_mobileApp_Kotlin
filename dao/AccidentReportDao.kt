package com.example.taladapp.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.taladapp.data.AccidentReport

@Dao
interface AccidentReportDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(report: AccidentReport)

    @Query("SELECT * FROM AccidentReport WHERE username = :username")
    suspend fun getUserReports(username: String): List<AccidentReport>

    @Query("SELECT * FROM AccidentReport")
    suspend fun getAllReports(): List<AccidentReport>

    @Query("SELECT * FROM AccidentReport")
    suspend fun getAdminReports(): List<AccidentReport>
}


