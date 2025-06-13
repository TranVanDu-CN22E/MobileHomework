package com.example.myapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.model.Book

@Composable
fun AddBooksDialog(
    allBooks: List<Book>,
    borrowedBooks: List<Book>,
    onDismiss: () -> Unit,
    onConfirm: (List<Book>) -> Unit,
) {
    // Lọc ra sách chưa mượn
    val notBorrowedBooks = remember(allBooks, borrowedBooks) {
        allBooks.filter { book ->
            borrowedBooks.none { it.id == book.id }
        }.map { book ->
            Book(book.id, book.title, isSelectedBoolean = false)
        }
    }

    val selectedBooksState = remember { mutableStateListOf<Book>() }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Thêm sách mới",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            if (notBorrowedBooks.isEmpty()) {
                Text(
                    "Không còn sách nào để thêm",
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .height(300.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFFF5F5F5))
                        .padding(8.dp)
                ) {
                    items(notBorrowedBooks) { book ->
                        androidx.compose.material3.Card(
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .clickable {
                                    book.isSelected.value = !book.isSelected.value
                                    if (book.isSelected.value) {
                                        selectedBooksState.add(book)
                                    } else {
                                        selectedBooksState.remove(book)
                                    }
                                },
                            elevation = androidx.compose.material3.CardDefaults.cardElevation(4.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 12.dp, vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Checkbox(
                                    checked = book.isSelected.value,
                                    onCheckedChange = { checked ->
                                        book.isSelected.value = checked
                                        if (checked) {
                                            selectedBooksState.add(book)
                                        } else {
                                            selectedBooksState.remove(book)
                                        }
                                    }
                                )
                                Text(
                                    text = book.title,
                                    fontSize = 16.sp,
                                    color = Color.Black
                                )
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onConfirm(selectedBooksState.toList())
                },
                enabled = selectedBooksState.isNotEmpty(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6DC6EE))
            ) {
                Text("Xác nhận")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6DC6EE))) {
                Text("Hủy")
            }
        }
    )
}
