package com.example.btvn.data.remote

import retrofit2.http.GET
import retrofit2.http.Path

interface CoinCapApi {
    @GET("assets/{id}")
    suspend fun getAsset(@Path("id") id: String): CoinCapPriceResponse
}

data class CoinCapPriceResponse(val data: CoinCapData)
data class CoinCapData(val id: String, val symbol: String, val priceUsd: String)
