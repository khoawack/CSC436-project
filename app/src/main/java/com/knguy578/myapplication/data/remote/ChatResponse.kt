package com.knguy578.myapplication.data.remote

data class ChatResponse(
    val cals: Int?,
    val reasoning: String?,
    val debug: Any? = null,
    val raw: String? = null
)