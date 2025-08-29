package com.ssafy.common.data_store

import kotlinx.coroutines.flow.Flow

interface TokenManager {
    fun getAccessToken(): Flow<String?>
    fun getRefreshToken(): Flow<String?>
    fun getSid(): Flow<String?>
    suspend fun saveAccessToken(token: String)
    suspend fun saveRefreshToken(token: String)
    suspend fun saveSid(token: String)
    suspend fun deleteAccessToken()
    suspend fun deleteRefreshToken()
    suspend fun deleteSid()
}
