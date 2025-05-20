package com.example.sourcecode

import android.graphics.Color
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val checkButton = findViewById<Button>(R.id.button)
        val notif = findViewById<TextView>(R.id.textNotif)
        val emailInput = findViewById<EditText>(R.id.editTextTextEmailAddress)

        checkButton.setOnClickListener{
            val email = emailInput.text.toString().trim()
            val countChar = email.count{ it == '@'}

            if (email.isEmpty()) {
                notif.text = "Email không hợp lệ"
                notif.visibility = TextView.VISIBLE
            } else if (countChar != 1) {
                notif.text = "Email không đúng định dạng"
                notif.visibility = TextView.VISIBLE
            } else {
                notif.text = "Bạn đã nhập email hợp lệ"
                notif.visibility = TextView.VISIBLE
            }
        }

    }
}