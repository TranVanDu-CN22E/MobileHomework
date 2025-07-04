package com.example.btvn.di

import com.example.btvn.data.remote.BinanceApi
import com.example.btvn.data.remote.CoinCapApi
import com.example.btvn.data.remote.CoinGeckoApi
import com.example.btvn.data.remote.CoinLoreApi
import com.example.btvn.data.remote.CoinPaprikaApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideCoinGeckoApi(): CoinGeckoApi =
        Retrofit.Builder()
            .baseUrl("https://api.coingecko.com/api/v3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CoinGeckoApi::class.java)

    @Provides
    @Singleton
    fun provideCoinCapOkHttpClient(): okhttp3.OkHttpClient {
        return okhttp3.OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .header("User-Agent", "CoinApp/1.0")
                    .build()
                chain.proceed(request)
            }
            .build()
    }

    @Provides
    @Singleton
    fun provideCoinCapApi(
        coinCapClient: okhttp3.OkHttpClient
    ): CoinCapApi =
        Retrofit.Builder()
            .baseUrl("https://api.coincap.io/v2/")
            .client(coinCapClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CoinCapApi::class.java)

    @Provides
    @Singleton
    fun provideBinanceApi(): BinanceApi =
        Retrofit.Builder()
            .baseUrl("https://api.binance.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BinanceApi::class.java)

    @Provides
    @Singleton
    fun provideCoinPaprikaApi(): CoinPaprikaApi =
        Retrofit.Builder()
            .baseUrl("https://api.coinpaprika.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CoinPaprikaApi::class.java)

    @Provides
    @Singleton
    fun provideCoinLoreApi(): CoinLoreApi =
        Retrofit.Builder()
            .baseUrl("https://api.coinlore.net/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CoinLoreApi::class.java)

}

