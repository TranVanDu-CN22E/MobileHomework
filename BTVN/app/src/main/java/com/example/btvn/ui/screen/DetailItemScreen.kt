package com.example.btvn.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.btvn.ui.viewmodel.TaskViewModel
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.getValue
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import coil.compose.AsyncImage
import com.example.btvn.data.model.TaskDetail
import com.example.btvn.ui.theme.*
import com.example.btvn.ui.viewmodel.TaskDetailViewModel


@Composable
fun TaskDetailScreen(
    navController: NavController,
    taskId: Int,
    viewModel: TaskDetailViewModel = hiltViewModel()
) {
    val task by viewModel.taskDetail.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    LaunchedEffect(taskId) {
        viewModel.loadTaskDetail(taskId)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        //Header
        Box(modifier = Modifier.fillMaxWidth().padding(start = 16.dp, top = 16.dp, end = 16.dp)) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .background(BlueLight)
                    .clickable{navController.popBackStack()}
            ) {
                Text(
                    text = "<",
                    fontWeight = FontWeight.W600,
                    fontSize = 24.sp,
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.White
                )
            }
            Text("Detail", fontWeight = FontWeight.W800, color = BlueLight, fontSize = 24.sp, modifier = Modifier.align(Alignment.Center))
        }
        when {
            task != null -> {
                TaskDetailContent(task = task!!, navController = navController)
            }

            errorMessage != null -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    //Text(errorMessage ?: "Đã xảy ra lỗi", color = Color.Red)
                    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "No Tasks Yet!",
                            fontWeight = FontWeight.W800,
                            color = Color.Black,
                            fontSize = 24.sp
                        )
                        Text(
                            text = "Stay productive-add something to do",
                            color = Color.Gray,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(top = 16.dp)
                        )
                    }
                }
            }

            else -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Đang tải...", color = Color.Gray)
                }
            }
        }
    }
}
@Composable
fun TaskDetailContent(task: TaskDetail, navController: NavController) {
    Column(modifier = Modifier.padding(16.dp)) {

        // Hình ảnh mô tả
        if (task.desImageURL.isNotBlank()) {
            AsyncImage(
                model = task.desImageURL,
                contentDescription = "Image for ${task.title}",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Thông tin cơ bản
        Text(task.title, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = BlueLight)
        Spacer(modifier = Modifier.height(4.dp))
        Text(task.description, fontSize = 14.sp)
        Spacer(modifier = Modifier.height(8.dp))

        // Trạng thái & ưu tiên
        Row(modifier = Modifier.fillMaxWidth().background(PinkLight).padding(vertical = 16.dp).clip(RoundedCornerShape(4.dp))) {
            Column(modifier = Modifier.weight(1f).align(Alignment.CenterVertically)){
                Text("Status", color = Color.Black, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
                Text("${task.status.ifBlank { "N/A" }}", color = Color.Black, fontWeight = FontWeight.W800, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
            }
            Column(modifier = Modifier.weight(1f).align(Alignment.CenterVertically)){
                Text("Priority", color = Color.Black, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
                Text("${task.priority.ifBlank { "N/A" }}", color = Color.Black, fontWeight = FontWeight.W800, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
            }
            Column(modifier = Modifier.weight(1f).align(Alignment.CenterVertically)){
                Text("Category", color = Color.Black, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
                Text("${task.category.ifBlank { "N/A" }}", color = Color.Black, fontWeight = FontWeight.W800, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Ngày giờ
        /*Text("Due: ${task.dueDate.takeIf { it.length >= 10 } ?: "N/A"}")
        Text("Created: ${task.createdAt.takeIf { it.length >= 10 } ?: "N/A"}")
        Text("Updated: ${task.updatedAt.takeIf { it.length >= 10 } ?: "N/A"}")*/

        Spacer(modifier = Modifier.height(16.dp))

        // Subtasks
        if (task.subtasks.isNotEmpty()) {
            Text("Subtasks:", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))
            task.subtasks.forEach { sub ->
                Text("• ${sub.title} ${if (sub.isCompleted) "✓" else "✗"}")
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Attachments
        if (task.attachments.isNotEmpty()) {
            Text("Attachments:", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))
            task.attachments.forEach { file ->
                Text("📎 ${file.fileName}", fontSize = 12.sp, color = Color.Blue)
                Spacer(modifier = Modifier.height(4.dp))
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Reminders
        if (task.reminders.isNotEmpty()) {
            Text("Reminders:", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))
            task.reminders.forEach { reminder ->
                Text("🔔 ${reminder.type} at ${reminder.time}")
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}





