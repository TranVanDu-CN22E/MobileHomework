package com.example.btvn.ui.screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import com.example.btvn.data.model.Task
import com.example.btvn.ui.theme.*
import com.example.btvn.ui.viewmodel.TaskViewModel
import androidx.compose.foundation.lazy.items
import com.example.btvn.navigation.Screen
import androidx.compose.foundation.clickable
import androidx.compose.ui.res.painterResource
import com.example.btvn.R


@Composable
fun HomeScreen(navController: NavController, viewModel: TaskViewModel = hiltViewModel()){
    val taskList by viewModel.tasks.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadTasks()
    }
    var heightTopAndBottom = 70;
    Box(modifier = Modifier.fillMaxSize()){
        //Header
        Box(modifier = Modifier.height(heightTopAndBottom.dp).fillMaxWidth().background(Color.White).align(Alignment.TopCenter)){
            Row(modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Box(modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .background(BlueLight)
                ){
                    Text(
                        text="<",
                        fontSize = 24.sp,
                        modifier = Modifier.align(Alignment.Center),
                        color = Color.White,
                        fontWeight = FontWeight.W800)
                }
                Text(text = "List", fontSize = 26.sp, color = BlueLight, fontWeight = FontWeight.Bold)
                Box(modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(45.dp))
                    .background(Orange),
                    contentAlignment = Alignment.Center
                ){
                    Box(modifier = Modifier
                        .size(26.dp)
                        .clip(RoundedCornerShape(45.dp))
                        .background(Color.White),
                        contentAlignment = Alignment.Center
                    ){
                        Text(
                            text = "+",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.W800,
                            color = Orange,
                        )
                    }
                }
            }
        }
        //Body
        Box(modifier = Modifier.padding(top = heightTopAndBottom.dp, bottom = heightTopAndBottom.dp).fillMaxSize().background(Color.Blue).align(Alignment.Center)){
            Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){
                LazyColumn(modifier = Modifier.padding(16.dp)) {
                    items(taskList) { task: Task ->
                        Log.d("TASK", "Task ID = ${task.id}, Title = ${task.title}")
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color.White)
                                .padding(16.dp)
                                .clickable{navController.navigate(Screen.Detail.passId(task.id))}
                        ) {
                            Text(task.title, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = BlueLight)
                            Text(task.description, fontSize = 14.sp, color = Color.Gray)
                            Text("Due: ${task.dueDate.take(10)}", fontSize = 12.sp, color = Color.DarkGray)
                        }
                    }
                }
            }
        }
        Box(modifier = Modifier.height(heightTopAndBottom.dp).fillMaxWidth().align(Alignment.BottomCenter).background(Color.White)){
            Row(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp).fillMaxSize().clip(RoundedCornerShape(10.dp)).background(Color.LightGray), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceAround){
                Box(modifier = Modifier.weight(1f)) {
                    Image(
                        painter = painterResource(id = R.drawable.home),
                        contentDescription = "Home",
                        modifier = Modifier.size(26.dp).align(Alignment.Center)
                    )
                }
                Box(modifier = Modifier.weight(1f)) {
                    Image(
                        painter = painterResource(id = R.drawable.calendar),
                        contentDescription = "Home",
                        modifier = Modifier.size(26.dp).align(Alignment.Center)
                    )
                }
                Box(modifier = Modifier.weight(1f)) {
                    Image(
                        painter = painterResource(id = R.drawable.file),
                        contentDescription = "File",
                        modifier = Modifier.size(26.dp).align(Alignment.Center)
                    )
                }
                Box(modifier = Modifier.weight(1f)) {
                    Image(
                        painter = painterResource(id = R.drawable.settings),
                        contentDescription = "Settings",
                        modifier = Modifier.size(26.dp).align(Alignment.Center)
                    )
                }
            }
        }
    }
}