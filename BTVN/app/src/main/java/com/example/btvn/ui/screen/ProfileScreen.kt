package com.example.btvn.ui.screen

import android.R.attr.shape
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.btvn.R
import com.example.btvn.ui.theme.BlueLight
import com.example.btvn.ui.theme.BlueLightWhite

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController) {
    val backStackEntry = remember(navController) {
        navController.previousBackStackEntry
    }
    val displayName = backStackEntry?.savedStateHandle?.get<String>("displayName") ?: "Unknown"
    val email = backStackEntry?.savedStateHandle?.get<String>("email") ?: "Unknown"
    val uid = backStackEntry?.savedStateHandle?.get<String>("uid") ?: "Unknown"

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .background(BlueLight)
            ) {
                Text(
                    text = "<",
                    fontWeight = FontWeight.W600,
                    fontSize = 24.sp,
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.White
                )
            }
            Text("Profile", fontWeight = FontWeight.W800, fontSize = 24.sp, modifier = Modifier.align(Alignment.Center))
        }
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center){
            Image(
                painter = painterResource(id = R.drawable.profile),
                contentDescription = "UTH Logo",
                modifier = Modifier.size(150.dp).clip(RoundedCornerShape(45))
            )
        }
        Text(text = "Name", fontWeight = FontWeight.W600, fontSize = 18.sp, modifier = Modifier.padding(top = 24.dp))
        TextField(
            value = displayName,
            onValueChange = {},
            readOnly = true,
            enabled = false,
            colors = TextFieldDefaults.textFieldColors(
                disabledTextColor = Color.Black,
                containerColor = Color.White,

            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
                .border(1.dp, Color.Black, RoundedCornerShape(4.dp))
        )
        Text(text = "Email", fontWeight = FontWeight.W600, fontSize = 18.sp, modifier = Modifier.padding(top = 24.dp))
        TextField(
            value = email,
            onValueChange = {},
            readOnly = true,
            enabled = false,
            colors = TextFieldDefaults.textFieldColors(
                disabledTextColor = Color.Black,
                containerColor = Color.White,

                ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
                .border(1.dp, Color.Black, RoundedCornerShape(4.dp))
        )
        Text(text = "Date of Birth", fontWeight = FontWeight.W600, fontSize = 18.sp, modifier = Modifier.padding(top = 24.dp))
        TextField(
            value = "29/06/2025",
            onValueChange = {},
            readOnly = true,
            enabled = false,
            trailingIcon = {
                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = "Dropdown",
                    tint = Color.Black,
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                disabledTextColor = Color.Black,
                containerColor = Color.White,
                disabledIndicatorColor = Color.Transparent,
                disabledTrailingIconColor = Color.Black,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
                .border(1.dp, Color.Black, RoundedCornerShape(4.dp))
        )
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Button(
                modifier = Modifier
                    .padding(top = 24.dp),
                onClick = { navController.navigate("home_screen") },
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = BlueLightWhite,
                    contentColor = Color.Black
                )
            ) {
                Text("Trang chủ")
            }
        }

    }
}
