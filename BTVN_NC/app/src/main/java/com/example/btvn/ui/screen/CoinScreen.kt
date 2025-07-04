package com.example.btvn.ui.screen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CurrencyBitcoin
import androidx.compose.material.icons.filled.TrendingDown
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.btvn.viewmodel.CoinViewModel
import com.example.btvn.data.model.CoinPrice
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun CoinChartScreen(viewModel: CoinViewModel = hiltViewModel()) {
    val prices by viewModel.coinPrices.collectAsState()
    val histories: Map<String, List<Float>> by viewModel.coinPriceHistories.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
            .padding(16.dp)
    ) {
        item {
            Text("Home", fontWeight = FontWeight.Bold, fontSize = 24.sp, color = Color.White)
            Spacer(modifier = Modifier.height(12.dp))

            // Banner
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.Yellow),
                contentAlignment = Alignment.Center
            ) {
                Text("🔴 BREAKING NEWS", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text("Favorites", fontWeight = FontWeight.SemiBold, fontSize = 18.sp, color = Color.White)
            Spacer(modifier = Modifier.height(12.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                listOf("ETH", "BTC").forEach { symbol ->
                    val coin = prices.find { it.symbol == symbol }
                    coin?.let { coinData ->
                        val chartData = histories[coinData.symbol] ?: List(10) { coinData.price.toFloat() }
                        FavoriteCoinCard(coinData, chartData, modifier = Modifier.weight(1f))
                    }


                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text("All Fluctuations", fontWeight = FontWeight.SemiBold, fontSize = 18.sp, color = Color.White)
            Spacer(modifier = Modifier.height(12.dp))
        }

        items(prices) { coin ->
            CoinListItem(coin)
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
fun FavoriteCoinCard(coin: CoinPrice, dataPoints: List<Float>, modifier: Modifier = Modifier) {
    val change = mockChangePercent()
    val isUp = change >= 0

    Box(
        modifier = modifier
            .height(100.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFF1E1E2E))
            .padding(12.dp)
    ) {
        Column {
            Text(coin.symbol, color = Color.LightGray)
            Text("$${coin.price}", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text(
                text = (if (isUp) "+" else "−") + "${kotlin.math.abs(change)}%",
                color = if (isUp) Color.Green else Color.Red,
                fontSize = 12.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            LineChartMini(dataPoints = dataPoints)
        }
    }
}

@Composable
fun LineChartMini(
    dataPoints: List<Float>,
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .height(40.dp)
) {
    if (dataPoints.size < 2) return

    val max = dataPoints.maxOrNull() ?: 1f
    val min = dataPoints.minOrNull() ?: 0f
    val range = (max - min).takeIf { it > 0f } ?: 1f

    Canvas(modifier = modifier) {
        val spacing = size.width / (dataPoints.size - 1)
        val points = dataPoints.mapIndexed { i, value ->
            val x = i * spacing
            val y = size.height - (value - min) / range * size.height
            androidx.compose.ui.geometry.Offset(x, y)
        }

        val path = Path().apply {
            reset()
            moveTo(points.first().x, points.first().y)
            for (i in 1 until points.size) {
                val prev = points[i - 1]
                val curr = points[i]
                val midX = (prev.x + curr.x) / 2
                val midY = (prev.y + curr.y) / 2
                quadraticBezierTo(prev.x, prev.y, midX, midY)
            }
            lineTo(points.last().x, points.last().y)
        }

        drawPath(path, color = Color.Cyan, style = Stroke(width = 2f))
    }
}

@Composable
fun CoinListItem(coin: CoinPrice) {
    val change = mockChangePercent()
    val isUp = change >= 0

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFF2A2A3C))
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.CurrencyBitcoin, contentDescription = null, tint = Color.White)
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(coin.symbol, color = Color.White, fontWeight = FontWeight.Bold)
                Text(getFullName(coin.symbol), color = Color.Gray, fontSize = 12.sp)
            }
        }

        Column(horizontalAlignment = Alignment.End) {
            Text("$${coin.price}", color = Color.White)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = if (isUp) Icons.Default.TrendingUp else Icons.Default.TrendingDown,
                    contentDescription = null,
                    tint = if (isUp) Color.Green else Color.Red,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    "${if (isUp) "+" else "−"}${kotlin.math.abs(change)}%",
                    color = if (isUp) Color.Green else Color.Red,
                    fontSize = 12.sp
                )
            }
        }
    }
}

fun getFullName(symbol: String): String = when (symbol) {
    "BTC" -> "Bitcoin"
    "ETH" -> "Ethereum"
    "BNB" -> "Binance Coin"
    "SOL" -> "Solana"
    else -> "Unknown"
}

fun mockChangePercent(): Double {
    return listOf(-0.3, -2.5, 1.2, 3.4, -1.0, 0.5).random()
}