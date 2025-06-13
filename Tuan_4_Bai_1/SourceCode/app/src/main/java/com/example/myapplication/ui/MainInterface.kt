package com.example.myapplication.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.myapplication.AppScreen
import com.example.myapplication.ui.screens.BookListScreen
import com.example.myapplication.ui.screens.LibraryScreen
import com.example.myapplication.ui.screens.StudentListScreen

@Composable
fun MainInterface() {
    var currentScreen by remember { mutableStateOf<AppScreen>(AppScreen.Dashboard) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Color.White)
    ) {
        Scaffold(
            bottomBar = {
                Column {
                    Divider(
                        color = Color(0x33000000),
                        thickness = 1.5.dp
                    )
                    NavigationBar(
                        containerColor = Color.White
                    ) {
                        listOf(AppScreen.Dashboard, AppScreen.BookList, AppScreen.Students).forEach { screen ->
                            NavigationBarItem(
                                selected = screen == currentScreen,
                                onClick = { currentScreen = screen },
                                icon = { Icon(screen.icon, contentDescription = screen.label) },
                                label = { Text(screen.label) },
                                alwaysShowLabel = true,
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = Color(0xFF3F51B5),
                                    unselectedIconColor = Color.Gray,
                                    selectedTextColor = Color(0xFF3F51B5),
                                    unselectedTextColor = Color.Gray,
                                    indicatorColor = Color.Transparent
                                )
                            )
                        }
                    }
                }
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .background(Color.White)
                    .fillMaxSize()
            ) {
                when (currentScreen) {
                    is AppScreen.Dashboard -> LibraryScreen()
                    is AppScreen.BookList -> BookListScreen()
                    is AppScreen.Students -> StudentListScreen()
                }
            }
        }
    }
}
