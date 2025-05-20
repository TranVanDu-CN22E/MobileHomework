package com.example.tuan02

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
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

        val nameInput = findViewById<EditText>(R.id.editName)
        val ageInput = findViewById<EditText>(R.id.editAge)
        val checkButton = findViewById<Button>(R.id.button)
        val cancelButton = findViewById<Button>(R.id.buttonCane)

        val notifText = findViewById<TextView>(R.id.textNotif)
        val resultBox = findViewById<LinearLayout>(R.id.boxResult)
        val resultText = findViewById<TextView>(R.id.textResult)

        // Ẩn kết quả khi nhấn "Hủy"
        cancelButton.setOnClickListener {
            resultBox.visibility = View.GONE
            notifText.visibility = View.GONE
        }

        // Kiểm tra và phân loại
        checkButton.setOnClickListener {
            val name = nameInput.text.toString().trim()
            val ageText = ageInput.text.toString().trim()

            if (name.isEmpty() || ageText.isEmpty()) {
                showNotification(notifText, "Vui lòng nhập cả Họ tên và Tuổi", false)
                resultBox.visibility = View.GONE
                return@setOnClickListener
            }

            val age = ageText.toIntOrNull()
            if (age == null || age < 0) {
                showNotification(notifText, "Tuổi không hợp lệ. Vui lòng nhập số nguyên dương.", false)
                resultBox.visibility = View.GONE
                return@setOnClickListener
            }

            val category = classifyAge(age)
            resultText.text = "Họ và tên: $name\nTuổi: $age\nPhân loại: $category"
            resultBox.visibility = View.VISIBLE
            showNotification(notifText, "Phân loại thành công", true)
        }
    }

    private fun classifyAge(age: Int): String {
        return when (age) {
            in 0..1 -> "Em bé"
            in 2..5 -> "Trẻ em"
            in 6..65 -> "Người lớn"
            else -> "Người già"
        }
    }

    private fun showNotification(view: TextView, message: String, isSuccess: Boolean) {
        view.text = message
        view.setTextColor(if (isSuccess) Color.parseColor("#228B22") else Color.parseColor("#FF0000"))
        view.visibility = View.VISIBLE
    }
}
