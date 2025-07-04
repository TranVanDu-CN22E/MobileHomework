package com.example.btvn.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface CoinGeckoApi {
    @GET("simple/price")
    suspend fun getPrice(
        @Query("ids") ids: String,
        @Query("vs_currencies") currency: String = "usd"
    ): Map<String, Map<String, Double>>
}


