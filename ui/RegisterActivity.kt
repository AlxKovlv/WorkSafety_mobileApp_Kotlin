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
import com.example.taladapp.data.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.MainScope

class RegisterActivity : AppCompatActivity(), CoroutineScope by MainScope() {
    lateinit var edUsername: EditText
    lateinit var edEmail: EditText
    lateinit var edPassword: EditText
    lateinit var edConfirm: EditText
    lateinit var btn: Button
    lateinit var tv: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        edUsername = findViewById(R.id.editTextAppLocation)
        edPassword = findViewById(R.id.editTextAppDescribeAction)
        edEmail = findViewById(R.id.editTextAppAccidentDetails)
        edConfirm = findViewById(R.id.editTexEventDescription)
        btn = findViewById(R.id.buttonSendReport)
        tv = findViewById(R.id.textViewExistingUser)

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "talad"
        ).build()

        val userDao = db.userDao()

        tv.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        btn.setOnClickListener {
            val username = edUsername.text.toString()
            val email = edEmail.text.toString()
            val password = edPassword.text.toString()
            val confirm = edConfirm.text.toString()

            if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
                Toast.makeText(applicationContext, "Please fill all details", Toast.LENGTH_SHORT).show()
            } else {
                if (password == confirm) {
                    if (isValid(password)) {
                        launch {
                            val isAdmin = email == "admin@admin.com" // Check if the user is admin
                            userDao.register(User(username, email, password, isAdmin))
                            Toast.makeText(applicationContext, "Record accepted", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                        }
                    } else {
                        Toast.makeText(applicationContext, "At least 8 characters, having a letter, a digit, and a special character", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(applicationContext, "Confirmation password didn't match", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    companion object {
        fun isValid(passwordhere: String): Boolean {
            var f1 = 0
            var f2 = 0
            var f3 = 0
            return if (passwordhere.length < 8) {
                false
            } else {
                for (p in 0 until passwordhere.length) {
                    if (Character.isLetter(passwordhere[p])) {
                        f1 = 1
                    }
                }
                for (r in 0 until passwordhere.length) {
                    if (Character.isDigit(passwordhere[r])) {
                        f2 = 1
                    }
                }
                for (s in 0 until passwordhere.length) {
                    val c = passwordhere[s]
                    if (c.code >= 33 && c.code <= 46 || c.code == 64) {
                        f3 = 1
                    }
                }
                f1 == 1 && f2 == 1 && f3 == 1
            }
        }
    }
}
