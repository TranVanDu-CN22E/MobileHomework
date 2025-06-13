package com.example.forgotpassword.nav

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.forgotpassword.ui.screens.*
import com.example.forgotpassword.ui.viewmodel.AuthViewModel
import com.example.forgotpassword.ui.viewmodel.AuthViewModelFactory

@Composable
fun AppNav(navController: NavHostController) {
    val context = LocalContext.current
    val application = context.applicationContext as Application
    val authViewModel: AuthViewModel = viewModel(
        factory = AuthViewModelFactory(application)
    )

    LaunchedEffect(key1 = Unit) {
        authViewModel.navigationEvent.collect { event ->
            when (event) {
                is AuthViewModel.NavigationAction.NavigateTo -> {
                    navController.navigate(event.route)
                }
                is AuthViewModel.NavigationAction.NavigateBack -> {
                    navController.popBackStack()
                }
                is AuthViewModel.NavigationAction.NavigateToForgotPasswordWithEmail -> {
                    navController.navigate(AppScreen.ForgotPassword.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            }
        }
    }


    NavHost(navController = navController, startDestination = AppScreen.ForgotPassword.route) {
        composable(AppScreen.ForgotPassword.route) {
            ForgotPasswordScreen(
                viewModel = authViewModel,
                onNavigateToVerify = {}
            )
        }
        composable(AppScreen.VerifyCode.route) {
            VerifyCodeScreen(
                viewModel = authViewModel,
                onNavigateToReset = {},
                onBack = { navController.popBackStack() }
            )
        }
        composable(AppScreen.ResetPassword.route) {
            ResetPasswordScreen(
                viewModel = authViewModel,
                onNavigateToConfirm = {},
                onBack = { navController.popBackStack() }
            )
        }
        composable(AppScreen.Confirm.route) {
            ConfirmScreen(
                viewModel = authViewModel,
                onNavigateToForgotPassword = { email -> },
                onBack = { navController.popBackStack() }
            )
        }
    }
}