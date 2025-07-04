package com.example.btvn.data.remote

import com.example.btvn.data.model.TaskDetailResponse
import com.example.btvn.data.model.TaskResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface TaskApiService {
    @GET("tasks")
    suspend fun getTasks(): TaskResponse
    @GET("task/{id}")
    suspend fun getTaskDetail(@Path("id") id: Int): TaskDetailResponse

}
