package com.example.btvn.viewmodel

import retrofit2.HttpException
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.btvn.data.model.CoinPrice
import com.example.btvn.repository.CoinRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinViewModel @Inject constructor(
    private val repository: CoinRepository
) : ViewModel() {

    private val _coinPriceHistories = MutableStateFlow<Map<String, List<Float>>>(emptyMap())
    val coinPriceHistories: StateFlow<Map<String, List<Float>>> = _coinPriceHistories

    private val _coinPrices = MutableStateFlow<List<CoinPrice>>(emptyList())
    val coinPrices: StateFlow<List<CoinPrice>> = _coinPrices

    init {
        viewModelScope.launch(Dispatchers.IO) {
            while (isActive) {
                fetchPrices()
                delay(20000) // Cập nhật mỗi 20 giây
            }
        }
    }



    fun fetchPrices() {
        viewModelScope.launch {
            try {
                val result = repository.getCoinPrices()
                _coinPrices.value = result
                Log.d("CoinViewModel", "✅ Prices loaded: $result")
            } catch (e: HttpException) {
                Log.e("CoinViewModel", "❌ Http ${e.code()} - ${e.message()}")
            } catch (e: Exception) {
                Log.e("CoinViewModel", "❌ Error: ${e.message}")
            }
        }
    }
}
