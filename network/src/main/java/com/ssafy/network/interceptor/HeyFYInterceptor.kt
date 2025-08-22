package com.ssafy.network.interceptor

import com.ssafy.common.data_store.TokenManager
import com.ssafy.network.api.AuthApi
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
    private val authApi: AuthApi,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        if (originalRequest.url.encodedPath == "/auth/refresh") {
            return chain.proceed(originalRequest)
        }
        
        val builder = originalRequest.newBuilder()
        var accessToken = runBlocking { tokenManager.getAccessToken().first() }

        Timber.d("Access token: $accessToken")

        if (!accessToken.isNullOrEmpty()) {
            builder.addHeader(AUTHORIZATION, "Bearer $accessToken")
        }

        val response = chain.proceed(builder.build())

        if (response.isUnauthorized()) {
            response.closeQuietly()
            
            val refreshToken = runBlocking { tokenManager.getRefreshToken().first() }
            Timber.d("Refresh token: $refreshToken")

            if (!refreshToken.isNullOrEmpty() && !accessToken.isNullOrEmpty()) {
                try {
                    val refreshResponse = runBlocking {
                        authApi.refreshToken(
                            authorization = "Bearer $accessToken",
                            refreshToken = refreshToken
                        )
                    }

                    if (refreshResponse.isSuccessful) {
                        val newTokens = refreshResponse.body()
                        if (newTokens != null) {
                            runBlocking {
                                tokenManager.saveAccessToken(newTokens.accessToken)
                                tokenManager.saveRefreshToken(newTokens.refreshToken)
                            }

                            Timber.d("Token refreshed successfully")

                            val newRequest = originalRequest.newBuilder()
                                .removeHeader(AUTHORIZATION)
                                .addHeader(AUTHORIZATION, "Bearer ${newTokens.accessToken}")
                                .build()

                            return chain.proceed(newRequest)
                        }
                    } else {
                        Timber.e("Token refresh failed: ${refreshResponse.code()}")
                        runBlocking { tokenManager.deleteAccessToken() }
                    }
                } catch (e: Exception) {
                    Timber.e(e, "Exception during token refresh")
                    runBlocking { tokenManager.deleteAccessToken() }
                }
            }
        }

        return response
    }

    private fun Response.isUnauthorized(): Boolean = code == HttpURLConnection.HTTP_UNAUTHORIZED

    companion object {
        private const val AUTHORIZATION = "authorization"
        private const val REFRESH_TOKEN = "RefreshToken"
    }
}
