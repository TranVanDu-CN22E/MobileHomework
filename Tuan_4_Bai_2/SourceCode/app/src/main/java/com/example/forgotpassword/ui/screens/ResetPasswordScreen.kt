package com.example.forgotpassword.ui.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.forgotpassword.ui.components.AppLogo
import com.example.forgotpassword.ui.components.AuthButton
import com.example.forgotpassword.ui.components.AuthTextField
import com.example.forgotpassword.ui.components.AuthTopAppBar
import com.example.forgotpassword.ui.viewmodel.AuthEvent
import com.example.forgotpassword.ui.viewmodel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResetPasswordScreen(
    viewModel: AuthViewModel,
    onNavigateToConfirm: () -> Unit,
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val currentError = uiState.error
    Scaffold(
        topBar = { AuthTopAppBar(title = "", onBackClicked = onBack) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AppLogo(modifier = Modifier.padding(bottom = 48.dp))

            Text(
                "Create new password",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                "Your new password must be different form previously used password",
                fontSize = 14.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            AuthTextField(
                value = uiState.newPassword,
                onValueChange = { viewModel.onEvent(AuthEvent.NewPasswordChanged(it)) },
                label = "Password",
                leadingIcon = { Icon(Icons.Outlined.Lock, "Password Icon") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardType = KeyboardType.Password,
                isError = currentError?.contains("password", ignoreCase = true) == true
                        && currentError?.contains("match", ignoreCase = true) == false
            )
            Spacer(modifier = Modifier.height(16.dp))
            AuthTextField(
                value = uiState.confirmPassword,
                onValueChange = { viewModel.onEvent(AuthEvent.ConfirmPasswordChanged(it)) },
                label = "Confirm Password",
                leadingIcon = { Icon(Icons.Outlined.Lock, "Password Icon") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardType = KeyboardType.Password,
                isError = uiState.error?.contains("match", ignoreCase = true) == true
            )

            Spacer(modifier = Modifier.height(8.dp))
            if (uiState.error != null) {
                Text(uiState.error!!, color = MaterialTheme.colorScheme.error, fontSize = 12.sp)
            }
            Spacer(modifier = Modifier.height(24.dp))

            AuthButton(
                text = "Next",
                onClick = { viewModel.onEvent(AuthEvent.SubmitResetPassword) },
                enabled = !uiState.isLoading
            )
            if (uiState.isLoading) {
                Spacer(modifier = Modifier.height(16.dp))
                CircularProgressIndicator()
            }
        }
    }
}