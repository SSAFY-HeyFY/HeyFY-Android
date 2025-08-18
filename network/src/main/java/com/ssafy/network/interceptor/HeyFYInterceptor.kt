package com.ssafy.network.interceptor

import com.ssafy.common.data_store.TokenManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.internal.closeQuietly
import timber.log.Timber
import java.net.HttpURLConnection
import javax.inject.Inject

class HeyFYInterceptor @Inject constructor(
    private val tokenManager: TokenManager,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        var token = runBlocking { tokenManager.getAccessToken().first() }

        Timber.Forest.d("Access token: $token")

        builder
            .addHeader(AUTHORIZATION, "Bearer $token")

        val response = chain.proceed(builder.build())

        if (response.isUnauthorized()) {
            response.closeQuietly()

            token = runBlocking { tokenManager.getRefreshToken().first() }
            Timber.Forest.d("Refresh token: $token")

            val refreshedRequest = chain.request().newBuilder()
                .addHeader(AUTHORIZATION, "Bearer $token")
                .build()

            return chain.proceed(refreshedRequest)
        }

        return response
    }

    private fun Response.isUnauthorized(): Boolean = code == HttpURLConnection.HTTP_UNAUTHORIZED

    companion object {
        private const val AUTHORIZATION = "authorization"
    }
}
