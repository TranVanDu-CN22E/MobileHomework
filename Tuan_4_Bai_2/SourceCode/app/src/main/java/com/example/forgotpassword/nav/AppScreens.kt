package com.example.forgotpassword.nav

sealed class AppScreen(val route: String) {
    object ForgotPassword : AppScreen("forgot_password_screen")
    object VerifyCode : AppScreen("verify_code_screen")
    object ResetPassword : AppScreen("reset_password_screen")
    object Confirm : AppScreen("confirm_screen")
}