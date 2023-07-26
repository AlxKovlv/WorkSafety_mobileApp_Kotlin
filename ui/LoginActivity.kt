package com.example.taladapp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.taladapp.R
import com.example.taladapp.data.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.MainScope

class LoginActivity : AppCompatActivity(), CoroutineScope by MainScope() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val edUsername = findViewById<EditText>(R.id.editTextLoginUsername)
        val edPassword = findViewById<EditText>(R.id.editTextLoginPassword)
        val btn = findViewById<Button>(R.id.buttonLogin)
        val tv = findViewById<TextView>(R.id.textViewNewUser)

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "talad"
        ).build()

        val userDao = db.userDao()

        btn.setOnClickListener {
            val username = edUsername.text.toString()
            val password = edPassword.text.toString()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(applicationContext, "Please fill all details", Toast.LENGTH_SHORT).show()
            } else {
                launch {
                    val user = userDao.login(username, password)
                    if (user != null) {
                        Toast.makeText(applicationContext, "Login Success", Toast.LENGTH_SHORT).show()
                        val sharedpreferences = getSharedPreferences("shared_prefs", MODE_PRIVATE)
                        val editor = sharedpreferences.edit()
                        editor.putString("username", username)
                        editor.apply()
                        startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                    } else {
                        Toast.makeText(applicationContext, "Invalid Username and/or password", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        tv.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}
