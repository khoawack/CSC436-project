package com.knguy578.myapplication.data

import android.util.Log
import com.knguy578.myapplication.data.remote.ChatApi
import com.knguy578.myapplication.data.remote.ChatRequest
import com.knguy578.myapplication.data.remote.ChatResponse
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class MealRepository {

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)   // time to establish connection
        .readTimeout(90, TimeUnit.SECONDS)      // time waiting for server response
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://fast-api-csc-436.onrender.com/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val chatService = retrofit.create(ChatApi::class.java)

    suspend fun analyzeMeal(name: String, amount: String): ChatResponse? {
        return try {

            val message = """
                Meal title: $name
                Notes: $amount
            """.trimIndent()

            val response = chatService.sendMessage(
                ChatRequest(message),
                "i-like-chicken-nuggets"
            )

            Log.d("API_DEBUG", "HTTP CODE: ${response.code()}")

            if (response.isSuccessful) {
                response.body()
            } else {
                Log.d("API_DEBUG", "ERROR BODY: ${response.errorBody()?.string()}")
                null
            }

        } catch (e: Exception) {
            Log.e("API_DEBUG", "EXCEPTION: ${e.message}", e)
            null
        }
    }
}