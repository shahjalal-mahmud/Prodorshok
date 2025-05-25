package com.example.prodorshok.data.network

import retrofit2.http.Body
import retrofit2.http.POST

interface OpenRouterService {
    @POST("chat/completions")
    suspend fun getChatCompletion(@Body request: OpenRouterRequest): OpenRouterResponse
}
