package com.example.forgotpassword.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.forgotpassword.ui.viewmodel.AuthEvent
import com.example.forgotpassword.ui.viewmodel.AuthViewModel
import com.example.forgotpassword.ui.components.AppLogo
import com.example.forgotpassword.ui.components.AuthButton
import com.example.forgotpassword.ui.components.AuthTextField
import com.example.forgotpassword.ui.components.AuthTopAppBar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmScreen(
    viewModel: AuthViewModel,
    onNavigateToForgotPassword: (String) -> Unit,
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

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
                "Confirm",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                "We are here to help you!",
                fontSize = 14.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            AuthTextField(
                value = uiState.email,
                onValueChange = {},
                label = "Email",
                leadingIcon = { Icon(Icons.Outlined.Email, "Email Icon") },
                enabled = false
            )
            Spacer(modifier = Modifier.height(16.dp))

            AuthTextField(
                value = uiState.newPassword,
                onValueChange = {},
                label = "New Password",
                leadingIcon = { Icon(Icons.Outlined.Lock, "Password Icon") },
                visualTransformation = PasswordVisualTransformation(),
                enabled = false
            )


            Spacer(modifier = Modifier.height(8.dp))
            if (uiState.error != null) {
                Text(uiState.error!!, color = MaterialTheme.colorScheme.error, fontSize = 12.sp)
            }
            Spacer(modifier = Modifier.height(24.dp))

            AuthButton(
                text = "Submit",
                onClick = { viewModel.onEvent(AuthEvent.SubmitConfirmAndSave) },
                enabled = !uiState.isLoading
            )
            if (uiState.isLoading) {
                Spacer(modifier = Modifier.height(16.dp))
                CircularProgressIndicator()
            }
        }
    }
}

