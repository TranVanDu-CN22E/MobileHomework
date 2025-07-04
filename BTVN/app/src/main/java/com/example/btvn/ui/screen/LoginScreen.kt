package com.example.btvn.ui.screen

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.btvn.R
import com.example.btvn.auth.AuthViewModel
import com.example.btvn.ui.theme.BlueLight
import com.example.btvn.ui.theme.BlueLightWhite
import com.google.firebase.auth.FirebaseUser

@Composable
fun LoginScreen(
    onLoginSuccess: (FirebaseUser) -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            viewModel.handleSignInResult(result.data)
        }
    }

    LaunchedEffect(uiState.isSignedIn) {
        if (uiState.isSignedIn && uiState.user != null) {
            onLoginSuccess(uiState.user!!)
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize().background(Color.White)) {
        /*if (uiState.isSignedIn) {
            Text("Chào ${uiState.user?.displayName}")
            Button(onClick = { viewModel.signOut() }) {
                Text("Đăng xuất")
            }
        } else {*/
            Image(
                painter = painterResource(id = R.drawable.uth_logo),
                contentDescription = "UTH Logo",
                modifier = Modifier.size(150.dp)
            )
            Text(text = "SmartTask", fontSize = 48.sp, color = BlueLight, fontWeight = FontWeight.Bold)
            Text(text = "A simple and efficient task to-do app", fontSize = 16.sp, color = BlueLight)
            Spacer(modifier = Modifier.height(86.dp))
            Text(text = "Welcome", fontSize = 18.sp, fontWeight = FontWeight.W500)
            Text(text ="Ready to explore? Log in to get started", fontSize = 14.sp)
            Spacer(modifier = Modifier.height(36.dp))
            Button(
                onClick = { viewModel.signInWithGoogle(context, launcher) },
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.buttonColors(containerColor = BlueLightWhite, contentColor = Color.Black)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_google),
                    contentDescription = "Google Logo",
                    modifier = Modifier.size(24.dp).padding(end = 8.dp)
                )
                Text("Sign in with Google")
            }

    }
}

