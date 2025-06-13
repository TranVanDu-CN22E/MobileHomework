package com.example.myapplication.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class Student(
    val id: Int,
    val name: String,
    val borrowedBooks: MutableList<Book> = mutableListOf()
)

data class Book(
    val id: Int,
    val title: String,
    val isSelectedBoolean: Boolean = false
) {
    val isSelected = mutableStateOf(isSelectedBoolean)
}
