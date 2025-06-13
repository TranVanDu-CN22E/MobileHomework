package com.example.myapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.model.Book
import com.example.myapplication.model.Student

@Composable
fun LibraryScreen() {
    val books = remember {
        mutableStateListOf(
            Book(1, "Sách 01"),
            Book(2, "Sách 02"),
            Book(3, "Sách 03")
        )
    }

    val students = remember {
        mutableStateListOf(
            Student(1, "Trần Văn Dự", mutableListOf(Book(1, "Sách 01", true), Book(2, "Sách 02", true))),
            Student(2, "Nguyễn Văn A", mutableListOf(Book(3, "Sách 03", true))),
            Student(3, "Nguyễn Văn B", mutableListOf())
        )
    }

    var selectedStudentIndex by remember { mutableStateOf(0) }
    val selectedStudent = students[selectedStudentIndex]
    var borrowedBooks by remember { mutableStateOf(selectedStudent.borrowedBooks.toList()) }
    var showAddDialog by remember { mutableStateOf(false) }

    LaunchedEffect(selectedStudentIndex) {
        borrowedBooks = selectedStudent.borrowedBooks.toList()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Color(0xFFF6F7FB))
            .padding(16.dp)

    ) {
        Spacer(modifier = Modifier.height(12.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("HỆ THỐNG", fontSize = 22.sp, fontWeight = FontWeight.Medium, color = Color(0xFF3F51B5))
            Text("Quản lý Thư viện", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Black)
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text("Chọn sinh viên:", fontWeight = FontWeight.SemiBold, color = Color.DarkGray)
        DropdownMenuStudent(
            selectedIndex = selectedStudentIndex,
            students = students,
            selectedStudent = selectedStudent,
            onSelect = { selectedStudentIndex = it },
            onChangeBooks = {
                borrowedBooks = selectedStudent.borrowedBooks.filter { it.isSelected.value }
            },
            books = books,
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text("Sách đã mượn:", fontWeight = FontWeight.SemiBold, color = Color.DarkGray)

        Spacer(modifier = Modifier.height(12.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 120.dp, max = 300.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            if (borrowedBooks.isEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Chưa mượn sách nào", fontSize = 14.sp, color = Color.Gray)
                    Text("Nhấn 'Thêm' để bắt đầu hành trình đọc!", fontSize = 14.sp)
                }
            } else {
                LazyColumn(modifier = Modifier.padding(8.dp)) {
                    items(borrowedBooks) { book ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color(0xFFEFEFEF))
                                .padding(horizontal = 8.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = book.isSelected.value,
                                onCheckedChange = { checked -> book.isSelected.value = checked }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(book.title, fontSize = 14.sp)
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = { showAddDialog = true },
                shape = RoundedCornerShape(6.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF3F51B5),
                    contentColor = Color.White
                )
            ) {
                Text("Thêm")
            }
        }

        if (showAddDialog) {
            AddBooksDialog(
                allBooks = books,
                borrowedBooks = borrowedBooks,
                onDismiss = { showAddDialog = false },
                onConfirm = { selectedBooks ->
                    selectedStudent.borrowedBooks.addAll(selectedBooks)
                    borrowedBooks = selectedStudent.borrowedBooks.toList()
                    showAddDialog = false
                }
            )
        }
    }
}


@Composable
fun DropdownMenuStudent(
    selectedIndex: Int,
    students: List<Student>,
    selectedStudent: Student,
    onSelect: (Int) -> Unit,
    onChangeBooks: () -> Unit,
    books: List<Book>
) {
    var expanded by remember { mutableStateOf(false) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .border(1.dp, Color.Gray, RoundedCornerShape(4.dp))
                .padding(horizontal = 12.dp, vertical = 8.dp)
                .width(200.dp)
                .height(25.dp)
                .weight(4f)
                .clickable { expanded = true }
        ) {
            Text(selectedStudent.name, fontWeight = FontWeight.Bold, color = Color.Black)
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.width(200.dp)
            ) {
                students.forEachIndexed { index, student ->
                    DropdownMenuItem(
                        text = { Text(student.name) },
                        onClick = {
                            onSelect(index)
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        Button(
            modifier = Modifier.weight(2f),
            shape = RoundedCornerShape(4.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF3F51B5),
                contentColor = Color.White
            ),
            onClick = {
                // Lọc lại danh sách sách mượn: chỉ giữ lại sách được check
                val filteredBooks = selectedStudent.borrowedBooks.filter { it.isSelected.value }

                selectedStudent.borrowedBooks.clear()
                selectedStudent.borrowedBooks.addAll(filteredBooks)

                onChangeBooks()
            }
        ) {
            Text("Thay đổi", color = Color.White)
        }
    }

}
