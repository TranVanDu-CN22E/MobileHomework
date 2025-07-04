package com.example.btvn.data.model

data class TaskDetailResponse(
    val isSuccess: Boolean,
    val message: String,
    val data: TaskDetail? = null
)

data class TaskDetail(
    val id: Int = -1,
    val title: String = "",
    val desImageURL: String = "",
    val description: String = "",
    val status: String = "",
    val priority: String = "",
    val category: String = "",
    val dueDate: String = "",
    val createdAt: String = "",
    val updatedAt: String = "",
    val subtasks: List<Subtask> = emptyList(),
    val attachments: List<Attachment> = emptyList(),
    val reminders: List<Reminder> = emptyList()
)

data class Subtask(
    val id: Int = -1,
    val title: String = "",
    val isCompleted: Boolean = false
)
data class Attachment(
    val id: Int = -1,
    val fileName: String = "",
    val fileUrl: String = ""
)

data class Reminder(
    val id: Int = -1,
    val time: String = "",
    val type: String = ""
)

