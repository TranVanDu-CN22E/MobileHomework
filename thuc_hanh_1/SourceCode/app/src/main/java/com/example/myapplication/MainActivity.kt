package com.example.myapplication

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.ImageView
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
        val originalBitmap = BitmapFactory.decodeResource(resources, R.drawable.profile_image)
        val cropHeight = (originalBitmap.height * 0.8).toInt()
        val cropWidth = originalBitmap.width

        val croppedBitmap = Bitmap.createBitmap(
            originalBitmap,
            0,
            0,
            cropWidth,
            cropHeight
        )

        val imageView = findViewById<ImageView>(R.id.imageProfile)
        imageView.setImageBitmap(croppedBitmap)
    }


}