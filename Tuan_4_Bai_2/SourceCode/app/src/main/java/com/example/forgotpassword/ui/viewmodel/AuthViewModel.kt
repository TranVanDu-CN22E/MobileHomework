package com.example.forgotpassword.ui.viewmodel


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.forgotpassword.data.AppData
import com.example.forgotpassword.data.User
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class AuthUiState(
    val email: String = "",
    val otp: String = "",
    val newPassword: String = "",
    val confirmPassword: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val prefillEmail: String? = null
)

sealed class AuthEvent {
    data class EmailChanged(val email: String) : AuthEvent()
    data class OtpChanged(val otp: String) : AuthEvent()
    data class NewPasswordChanged(val newPassword: String) : AuthEvent()
    data class ConfirmPasswordChanged(val confirmPassword: String) : AuthEvent()
    object SubmitForgotPassword : AuthEvent()
    object SubmitVerifyCode : AuthEvent()
    object SubmitResetPassword : AuthEvent()
    object SubmitConfirmAndSave : AuthEvent()
    object ClearPrefillEmail : AuthEvent()
}

class AuthViewModel(application: Application) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    private val _navigationEvent = MutableSharedFlow<NavigationAction>()
    val navigationEvent: SharedFlow<NavigationAction> = _navigationEvent.asSharedFlow()

    sealed class NavigationAction {
        data class NavigateTo(val route: String) : NavigationAction()
        object NavigateBack : NavigationAction()
        data class NavigateToForgotPasswordWithEmail(val email: String) : NavigationAction()
    }


    private val userDao = AppData.getDatabase(application).userDao()

    fun onEvent(event: AuthEvent) {
        when (event) {
            is AuthEvent.EmailChanged -> _uiState.update { it.copy(email = event.email) }
            is AuthEvent.OtpChanged -> _uiState.update { it.copy(otp = event.otp) }
            is AuthEvent.NewPasswordChanged -> _uiState.update { it.copy(newPassword = event.newPassword) }
            is AuthEvent.ConfirmPasswordChanged -> _uiState.update { it.copy(confirmPassword = event.confirmPassword) }
            AuthEvent.SubmitForgotPassword -> handleForgotPassword()
            AuthEvent.SubmitVerifyCode -> handleVerifyCode()
            AuthEvent.SubmitResetPassword -> handleResetPassword()
            AuthEvent.SubmitConfirmAndSave -> handleConfirmAndSave()
            AuthEvent.ClearPrefillEmail -> _uiState.update { it.copy(prefillEmail = null) }
        }
    }

    private fun handleForgotPassword() {
        val email = _uiState.value.email
        if (email.isBlank() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _uiState.update { it.copy(error = "Invalid email address") }
            return
        }
        _uiState.update { it.copy(isLoading = true, error = null) }
        // Simulate sending OTP
        viewModelScope.launch {
            kotlinx.coroutines.delay(1000)
            _uiState.update { it.copy(isLoading = false) }
            _navigationEvent.emit(NavigationAction.NavigateTo("verify_code_screen"))
        }
    }

    private fun handleVerifyCode() {
        val otp = _uiState.value.otp
        if (otp.length < 5) {
            _uiState.update { it.copy(error = "Invalid OTP") }
            return
        }
        _uiState.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            kotlinx.coroutines.delay(1000)
            _uiState.update { it.copy(isLoading = false) }
            _navigationEvent.emit(NavigationAction.NavigateTo("reset_password_screen"))
        }
    }

    private fun handleResetPassword() {
        val newPassword = _uiState.value.newPassword
        val confirmPassword = _uiState.value.confirmPassword
        if (newPassword.length < 6) {
            _uiState.update { it.copy(error = "Password must be at least 6 characters") }
            return
        }
        if (newPassword != confirmPassword) {
            _uiState.update { it.copy(error = "Passwords do not match") }
            return
        }
        _uiState.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            kotlinx.coroutines.delay(500)
            _uiState.update { it.copy(isLoading = false) }
            _navigationEvent.emit(NavigationAction.NavigateTo("confirm_screen"))
        }
    }

    private fun handleConfirmAndSave() {
        _uiState.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            val email = _uiState.value.email
            val newPassword = _uiState.value.newPassword
            try {
                userDao.insertOrUpdateUser(User(email, newPassword))
                _uiState.update { it.copy(isLoading = false, prefillEmail = email, otp = "", newPassword = "", confirmPassword = "") }
                _navigationEvent.emit(NavigationAction.NavigateToForgotPasswordWithEmail(email))
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = "Failed to save: ${e.message}") }
            }
        }
    }
}