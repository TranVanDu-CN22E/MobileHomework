package com.example.btvn.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface CoinLoreApi {
    @GET("api/ticker/")
    suspend fun getTicker(@Query("id") id: String): List<CoinLoreResponse>
}

data class CoinLoreResponse(
    val id: String,
    val name: String,
    val symbol: String,
    val price_usd: String
)
