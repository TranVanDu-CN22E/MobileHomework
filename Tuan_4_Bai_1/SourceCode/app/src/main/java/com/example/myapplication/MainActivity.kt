package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.myapplication.ui.MainInterface
import com.example.myapplication.ui.theme.MyApplicationTheme

// Thay đổi tên sealed class và các object, icon thay đổi một chút
sealed class AppScreen(val label: String, val icon: androidx.compose.ui.graphics.vector.ImageVector) {
    object Dashboard : AppScreen("Tổng quan", Icons.Default.Home)
    object BookList : AppScreen("Thư viện", Icons.Default.List)
    object Students : AppScreen("Sinh viên", Icons.Default.Person)
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Áp dụng system window fitting
        WindowCompat.setDecorFitsSystemWindows(window, false)

        // Thay đổi màu nền của status và navigation bar
        window.statusBarColor = android.graphics.Color.parseColor("#F5F5F5")
        window.navigationBarColor = android.graphics.Color.parseColor("#F5F5F5")

        // Điều chỉnh màu icon cho status bar và navigation bar
        val controller = WindowInsetsControllerCompat(window, window.decorView)
        controller.isAppearanceLightStatusBars = true
        controller.isAppearanceLightNavigationBars = true

        // Set UI chính
        setContent {
            MyApplicationTheme {
                MainInterface()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    MainInterface()
}
