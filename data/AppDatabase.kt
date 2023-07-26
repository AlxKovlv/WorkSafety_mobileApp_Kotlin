package com.example.taladapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.taladapp.dao.AccidentReportDao
import com.example.taladapp.dao.UserDao

@Database(entities = [User::class, AccidentReport::class], version = 3, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun accidentReportDao(): AccidentReportDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "taladapp_database"
                )
                    .fallbackToDestructiveMigration() // Add this line
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}
