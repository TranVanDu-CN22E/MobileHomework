package com.example.bai_tap_3

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color


@Composable
fun BasicUIScreen(navController: NavController) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        val (btnBack, title, list) = createRefs()

        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Back",
            tint = Color.Black,
            modifier = Modifier
                .size(28.dp)
                .clickable { navController.popBackStack() }
                .constrainAs(btnBack) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
        )


        Text(
            text = "Khám phá UI cơ bản",
            color = Color.Blue,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.constrainAs(title) {
                top.linkTo(btnBack.top)
                bottom.linkTo(btnBack.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp)
                .constrainAs(list) {
                    top.linkTo(title.bottom, margin = 48.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                },
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                ComponentItem("Text", "Hiển thị văn bản") {
                    Text("Đây là Text", fontSize = 16.sp, color = Color.Black)
                }
            }
            item {
                ComponentItem("Button", "Nút bấm") {
                    Button(onClick = {}) {
                        Text("Nhấn vào đây")
                    }
                }
            }
            item {
                ComponentItem("Image", "Hiển thị ảnh") {
                    Image(
                        painter = painterResource(id = R.drawable.image),
                        contentDescription = null,
                        modifier = Modifier.size(100.dp)
                    )
                }
            }
            item {
                ComponentItem("TextField", "Ô nhập liệu") {
                    var text by remember { mutableStateOf("") }
                    OutlinedTextField(value = text, onValueChange = { text = it }, placeholder = { Text("Nhập gì đó", color = Color.Black)})
                }
            }
            item {
                ComponentItem("Row & Column", "Sắp xếp ngang/dọc") {
                    Column {
                        Text("Dòng 1", color = Color.Black)
                        Row {
                            Text("Trái", modifier = Modifier.weight(1f), color = Color.Black)
                            Text("Phải", modifier = Modifier.weight(1f), color = Color.Black)
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun ComponentItem(title: String, description: String, content: @Composable () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color.Gray, RoundedCornerShape(12.dp))
            .padding(12.dp)
    ) {
        Text(title, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.Black)
        Text(description, fontSize = 13.sp, color = Color.Gray)
        Spacer(modifier = Modifier.height(8.dp))
        content()
    }
}
