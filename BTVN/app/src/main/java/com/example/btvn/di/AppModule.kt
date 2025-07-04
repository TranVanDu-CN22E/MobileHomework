package com.example.btvn.di

import android.content.Context
import com.example.btvn.auth.GoogleAuthUiClient
import com.example.btvn.data.remote.TaskApiService
import com.example.btvn.data.repository.TaskRepository
import com.google.android.gms.auth.api.identity.Identity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideGoogleAuthUiClient(
        @ApplicationContext context: Context
    ): GoogleAuthUiClient {
        val oneTapClient = Identity.getSignInClient(context)
        val webClientId = "664620219848-8c753p40ek1g7tv3miadbapuo9m91acs.apps.googleusercontent.com"
        return GoogleAuthUiClient(context, oneTapClient, webClientId)
    }

    @Provides
    @Singleton
    fun provideTaskApiService(): TaskApiService {
        return Retrofit.Builder()
            .baseUrl("https://amock.io/api/researchUTH/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TaskApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideTaskRepository(api: TaskApiService): TaskRepository {
        return TaskRepository(api)
    }
}



