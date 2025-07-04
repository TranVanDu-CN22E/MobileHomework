package com.example.btvn.data.remote

import retrofit2.http.GET
import retrofit2.http.Path

interface CoinPaprikaApi {
    @GET("v1/tickers/{id}")
    suspend fun getTicker(@Path("id") id: String): CoinPaprikaResponse
}

data class CoinPaprikaResponse(
    val id: String,
    val name: String,
    val symbol: String,
    val quotes: Map<String, Quote>
)

data class Quote(
    val price: Double
)
