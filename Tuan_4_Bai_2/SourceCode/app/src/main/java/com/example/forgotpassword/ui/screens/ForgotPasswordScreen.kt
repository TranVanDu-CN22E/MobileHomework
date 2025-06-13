package com.example.forgotpassword.ui.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
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
fun ForgotPasswordScreen(
    viewModel: AuthViewModel,
    onNavigateToVerify: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.prefillEmail) {
        uiState.prefillEmail?.let { email ->
            viewModel.onEvent(AuthEvent.EmailChanged(email))
            viewModel.onEvent(AuthEvent.ClearPrefillEmail)
        }
    }

    Scaffold(
        topBar = { AuthTopAppBar(title = "") }
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
                "Forgot Password?",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                "Enter your Email, we will send you a verification code.",
                fontSize = 14.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            AuthTextField(
                value = uiState.email,
                onValueChange = { viewModel.onEvent(AuthEvent.EmailChanged(it)) },
                label = "Your Email",
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Email,
                        contentDescription = "Email Icon",
                        tint = Color.Black
                    )
                },
                isError = uiState.error?.contains("email", ignoreCase = true) == true
            )
            Spacer(modifier = Modifier.height(8.dp))
            if (uiState.error != null) {
                Text(uiState.error!!, color = MaterialTheme.colorScheme.error, fontSize = 12.sp)
            }
            Spacer(modifier = Modifier.height(24.dp))

            AuthButton(
                text = "Next",
                onClick = { viewModel.onEvent(AuthEvent.SubmitForgotPassword) },
                enabled = !uiState.isLoading
            )
            if (uiState.isLoading) {
                Spacer(modifier = Modifier.height(16.dp))
                CircularProgressIndicator()
            }
        }
    }
}