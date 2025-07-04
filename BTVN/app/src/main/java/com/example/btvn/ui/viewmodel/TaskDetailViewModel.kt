package com.example.btvn.ui.viewmodel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.btvn.data.model.TaskDetail
import com.example.btvn.data.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class TaskDetailViewModel @Inject constructor(
    private val repository: TaskRepository
) : ViewModel() {

    private val _taskDetail = MutableStateFlow<TaskDetail?>(null)
    val taskDetail: StateFlow<TaskDetail?> = _taskDetail

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun loadTaskDetail(id: Int) {
        viewModelScope.launch {
            try {
                val response = repository.getTaskDetailResponse(id)
                if (response.isSuccess && response.data != null) {
                    _taskDetail.value = response.data
                } else {
                    _errorMessage.value = response.message
                }
            } catch (e: Exception) {
                _errorMessage.value = "Lỗi không xác định: ${e.localizedMessage}"
            }
        }
    }
}

