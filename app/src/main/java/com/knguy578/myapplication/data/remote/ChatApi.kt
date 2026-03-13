package com.knguy578.myapplication.data.remote

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ChatApi {

    @POST("chat")
    suspend fun sendMessage(
        @Body request: ChatRequest,
        @Header("X-App-Secret") appSecret: String
    ): Response<ChatResponse>
}