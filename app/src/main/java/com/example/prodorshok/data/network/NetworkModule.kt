package com.example.prodorshok.data.network

import android.content.Context
import com.example.prodorshok.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkModule {
    fun provideOpenRouterService(context: Context): OpenRouterService {
        val apiKey = BuildConfig.OPENROUTER_API_KEY

        val authInterceptor = Interceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $apiKey")
                .addHeader("Content-Type", "application/json")
                .build()
            chain.proceed(request)
        }

        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(logging)
            .build()

        return Retrofit.Builder()
            .baseUrl("https://openrouter.ai/api/v1/") // ✅ Correct base URL
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OpenRouterService::class.java)
    }
}
