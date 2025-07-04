package com.example.btvn

import BTVNTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.example.btvn.ui.screen.CoinChartScreen
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BTVNTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    CoinChartScreen() // UI chính
                }
            }
        }
    }
}
