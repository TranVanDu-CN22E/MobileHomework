package com.example.datastore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ColorViewModel(private val prefs: ColorPreferences) : ViewModel() {

    private val _bgColor = MutableStateFlow(0xFFFFFFFF.toInt()) // Trắng
    val bgColor: StateFlow<Int> get() = _bgColor

    init {
        viewModelScope.launch {
            prefs.backgroundColor.collectLatest {
                _bgColor.value = it
            }
        }
    }

    fun saveColor(color: Int) {
        viewModelScope.launch {
            prefs.saveBackgroundColor(color)
        }
    }

    fun updatePreviewColor(color: Int) {
        _bgColor.value = color
    }
}
