package com.example.btvn.auth

import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val googleAuthUiClient: GoogleAuthUiClient
) : ViewModel() {

    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState

    fun signInWithGoogle(context: Context, launcher: ActivityResultLauncher<IntentSenderRequest>) {
        viewModelScope.launch {
            val result = googleAuthUiClient.signIn()
            result?.let {
                val intentSender = IntentSenderRequest.Builder(it.pendingIntent.intentSender).build()
                launcher.launch(intentSender)
            }
        }
    }

    fun handleSignInResult(data: Intent?) {
        viewModelScope.launch {
            val user = data?.let { googleAuthUiClient.signInWithIntent(it) }
            _uiState.value = _uiState.value.copy(user = user, isSignedIn = user != null)
        }
    }

    fun signOut() {
        googleAuthUiClient.signOut()
        _uiState.value = AuthUiState()
    }
}

data class AuthUiState(
    val isSignedIn: Boolean = false,
    val user: FirebaseUser? = null
)
