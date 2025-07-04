package com.example.btvn.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.btvn.ui.screen.HomeScreen
import com.example.btvn.ui.screen.LoginScreen
import com.example.btvn.ui.screen.ProfileScreen
import com.example.btvn.ui.screen.TaskDetailScreen
import com.google.firebase.auth.FirebaseUser

sealed class Screen(val route: String) {
    object Login : Screen("login_screen")
    object Profile : Screen("profile_screen")
    object Home : Screen("home_screen")
    object Detail : Screen("detail_screen/{id}") {
        fun passId(id: Int): String {
            return "detail_screen/$id"
        }
    }
}

@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: String = Screen.Login.route
) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable(Screen.Login.route) {
            LoginScreen(onLoginSuccess = { user: FirebaseUser ->
                navController.currentBackStackEntry?.savedStateHandle?.apply {
                    set("displayName", user.displayName ?: "")
                    set("email", user.email ?: "")
                    set("uid", user.uid)
                }
                navController.navigate(Screen.Profile.route)
            })
        }
        composable(Screen.Profile.route) {
            ProfileScreen(navController = navController)
        }
        composable(Screen.Home.route) {
            HomeScreen(navController = navController)
        }
        composable(
            route = Screen.Detail.route,
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getInt("id")
            if (taskId != null) {
                TaskDetailScreen(navController, taskId)
            } else {
                Text("Invalid task ID")
            }
        }

    }
}

