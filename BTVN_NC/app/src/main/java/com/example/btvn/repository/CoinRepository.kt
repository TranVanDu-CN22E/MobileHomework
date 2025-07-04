package com.example.btvn.repository

import android.util.Log
import com.example.btvn.data.model.CoinPrice
import com.example.btvn.data.remote.BinanceApi
import com.example.btvn.data.remote.CoinGeckoApi
import com.example.btvn.data.remote.CoinLoreApi
import com.example.btvn.data.remote.CoinPaprikaApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CoinRepository @Inject constructor(
    private val geckoApi: CoinGeckoApi,
    //private val capApi: CoinCapApi,
    private val paprikaApi: CoinPaprikaApi,
    private val binanceApi: BinanceApi,
    private val loreApi: CoinLoreApi
) {
    suspend fun getCoinPrices(): List<CoinPrice> = withContext(Dispatchers.IO) {
        /*val idsMap = mapOf(
            "BTC" to Triple("bitcoin", "bitcoin", "BTCUSDT"),
            "ETH" to Triple("ethereum", "ethereum", "ETHUSDT"),
            "BNB" to Triple("binancecoin", "binance-coin", "BNBUSDT"),
            "SOL" to Triple("solana", "solana", "SOLUSDT")
        )*/
        val idsMap = mapOf(
            "BTC" to CoinIds("bitcoin", "btc-bitcoin", "BTCUSDT", "90"),
            "ETH" to CoinIds("ethereum", "eth-ethereum", "ETHUSDT", "80"),
            "BNB" to CoinIds("binancecoin", "bnb-binance-coin", "BNBUSDT", "2710"),
            "SOL" to CoinIds("solana", "sol-solana", "SOLUSDT", "48543")
        )


        val result = idsMap.map { (symbol, ids) ->
            async {
                Log.d("CoinRepository", "🔁 Thread for [$symbol]: ${Thread.currentThread().name}")
                val (geckoId, paprikaId, binanceSymbol, loreId) = ids

                val geckoPrice = try {
                    val map = geckoApi.getPrice(geckoId)
                    val price = map[geckoId]?.get("usd") ?: -1.0
                    Log.d("CoinRepository", "Gecko price [$symbol]: $price") // ✅ Log giá từ CoinGecko
                    price
                } catch (e: Exception) {
                    Log.e("CoinRepository", "Gecko error [$symbol]: ${e.message}")
                    -1.0
                }


                val lorePrice = try {
                    //loreApi.getTicker(loreId).firstOrNull()?.price_usd?.toDoubleOrNull() ?: -1.0
                    val response = loreApi.getTicker(loreId)
                    val price = response.firstOrNull()?.price_usd?.toDoubleOrNull() ?: -1.0
                    Log.d("CoinRepository", "Lore price [$symbol]: $price") // ✅ Log giá thành công
                    price
                } catch (e: Exception) {
                    Log.e("CoinRepository", "Lore error [$symbol]: ${e.message}")
                    -1.0
                }


                /*val capPrice = try {
                    capApi.getAsset(capId).data.priceUsd.toDoubleOrNull() ?: -1.0
                } catch (e: Exception) {
                    Log.e("CoinRepository", "CoinCap error [$symbol]: ${e.message}")
                    -1.0
                }*/

                val paprikaPrice = try {
                    val price = paprikaApi.getTicker(paprikaId).quotes["USD"]?.price ?: -1.0
                    Log.d("CoinRepository", "Paprika price [$symbol]: $price") // Log giá từ Paprika
                    price
                } catch (e: Exception) {
                    Log.e("CoinRepository", "Paprika error [$symbol]: ${e.message}")
                    -1.0
                }



                val binancePrice = try {
                    val price = binanceApi.getPrice(binanceSymbol).price.toDoubleOrNull() ?: -1.0
                    Log.d("CoinRepository", "Binance price [$symbol]: $price") // Log giá từ Binance
                    price
                } catch (e: Exception) {
                    Log.e("CoinRepository", "Binance error [$symbol]: ${e.message}")
                    -1.0
                }


                val prices = listOf(geckoPrice, paprikaPrice, binancePrice, lorePrice).filter { it > 0 }
                val commonPrice = prices
                    .groupingBy { it.round(2) }
                    .eachCount()
                    .maxByOrNull { it.value }?.key ?: prices.firstOrNull() ?: -1.0

                CoinPrice(symbol = symbol, price = commonPrice)
            }
        }
        result.awaitAll()
    }
    data class CoinIds(
        val geckoId: String,
        val paprikaId: String,
        val binanceSymbol: String,
        val coinLoreId: String
    )


    private fun Double.round(n: Int): Double =
        "%.${n}f".format(this).toDouble()
}
