package com.example.datastore

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun ColorPickerScreen() {
    val context = LocalContext.current
    val prefs = remember { ColorPreferences(context) }
    val viewModel = remember { ColorViewModel(prefs) }

    val currentColor by viewModel.bgColor.collectAsState()

    val colorOptions = listOf(Color.Red, Color.Green, Color.Blue, Color.Yellow)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(currentColor))
            .padding(16.dp)
    ) {
        Text("Chọn màu nền:", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(16.dp))

        Row {
            colorOptions.forEach { color ->
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .background(color)
                        .clickable {
                            viewModel.updatePreviewColor(color.toArgb())
                        }
                        .padding(4.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = {
            viewModel.saveColor(currentColor)
        }) {
            Text("Lưu màu")
        }
    }
}
