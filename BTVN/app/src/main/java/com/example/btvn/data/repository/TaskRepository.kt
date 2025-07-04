package com.example.btvn.data.repository

import com.example.btvn.data.model.TaskDetail
import com.example.btvn.data.model.TaskDetailResponse
import com.example.btvn.data.remote.TaskApiService
import javax.inject.Inject

class TaskRepository @Inject constructor(
    private val api: TaskApiService
) {
    suspend fun fetchTasks() = api.getTasks()
    suspend fun getTaskDetail(id: Int): TaskDetail {
        val response = api.getTaskDetail(id)
        return response.data ?: throw Exception("Task not found")
    }
    suspend fun getTaskDetailResponse(id: Int): TaskDetailResponse {
        return api.getTaskDetail(id)
    }


}
