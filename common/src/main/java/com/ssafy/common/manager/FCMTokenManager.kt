package com.ssafy.common.manager

interface FCMTokenManager {
    suspend fun getToken(): String?
    fun deleteToken()
    suspend fun subscribeToTopic(topic: String)
    suspend fun unsubscribeFromTopic(topic: String)
}
