package com.example.btvn.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface BinanceApi {
    @GET("api/v3/ticker/price")
    suspend fun getPrice(@Query("symbol") symbol: String): BinancePriceResponse
}


data class BinancePriceResponse(val symbol: String, val price: String)
