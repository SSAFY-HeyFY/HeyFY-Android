package com.ssafy.network.interceptor

import com.google.gson.Gson
import com.ssafy.common.data_store.TokenManager
import com.ssafy.common.error.RefreshTokenExpiredError
import com.ssafy.common.error.SidExpiredError
import com.ssafy.network.api.AuthApi
import com.ssafy.network.model.RefreshTokenResponse
import com.ssafy.network.utils.ErrorResponse
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.Response as RetrofitResponse
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
        val accessToken = runBlocking { tokenManager.getAccessToken().first() }

        Timber.d("Access token: $accessToken")

        if (accessToken.isNullOrEmpty().not()) {
            builder.addHeader(AUTHORIZATION, "Bearer $accessToken")
        }

        val sid = runBlocking { tokenManager.getSid().first() }

        if (sid.isNullOrEmpty().not()) {
            builder.addHeader(SID, sid)
        }

        val response = chain.proceed(builder.build())

        if (response.isUnauthorized()) {
            val errorResponse = parseErrorResponse(response.peekBody(Long.MAX_VALUE).string())
            Timber.e("Unauthorized response received: $errorResponse")

            if(errorResponse.errorCode != "EXPIRED_TOKEN") return response

            val refreshToken = runBlocking { tokenManager.getRefreshToken().first() }

            if (refreshToken.isNullOrEmpty() || accessToken.isNullOrEmpty()) {
                Timber.d("Both tokens are null or empty, clearing tokens and returning unauthorized response")
                runBlocking {
                    tokenManager.deleteAccessToken()
                    tokenManager.deleteRefreshToken()
                }
                return response
            }

            val refreshResponse = callRefreshTokenApi(accessToken, refreshToken)
            return handleRefreshTokenResponse(refreshResponse, originalRequest, chain)
        }
        return response
    }

    private fun callRefreshTokenApi(accessToken: String, refreshToken: String): RetrofitResponse<RefreshTokenResponse> {
        return runBlocking {
            authApi.refreshToken(
                authorization = "Bearer $accessToken",
                refreshToken = refreshToken
            )
        }
    }

    private fun handleRefreshTokenResponse(
        refreshResponse: RetrofitResponse<RefreshTokenResponse>,
        originalRequest: okhttp3.Request,
        chain: Interceptor.Chain
    ): Response {
        return try {
            if (refreshResponse.isSuccessful) {
                handleSuccessfulTokenRefresh(refreshResponse, originalRequest, chain)
            } else {
                handleFailedTokenRefresh()
                chain.proceed(originalRequest)
            }
        } catch (e: Exception) {
            handleTokenRefreshException(e)
            chain.proceed(originalRequest)
        }
    }

    private fun handleSuccessfulTokenRefresh(
        refreshResponse: RetrofitResponse<RefreshTokenResponse>,
        originalRequest: okhttp3.Request,
        chain: Interceptor.Chain
    ): Response {
        val newTokens = refreshResponse.body()
        if (newTokens != null) {
            saveNewTokens(newTokens)
            Timber.d("Token refreshed successfully")
            return createNewRequestWithNewToken(originalRequest, chain, newTokens.accessToken)
        }
        return chain.proceed(originalRequest)
    }

    private fun saveNewTokens(newTokens: RefreshTokenResponse) {
        runBlocking {
            tokenManager.deleteAccessToken()
            tokenManager.deleteRefreshToken()
            tokenManager.saveAccessToken(newTokens.accessToken)
            tokenManager.saveRefreshToken(newTokens.refreshToken)
        }
    }

    private fun createNewRequestWithNewToken(
        originalRequest: okhttp3.Request,
        chain: Interceptor.Chain,
        newAccessToken: String
    ): Response {
        val newRequest = originalRequest.newBuilder()
            .removeHeader(AUTHORIZATION)
            .removeHeader(REFRESH_TOKEN)
            .addHeader(AUTHORIZATION, "Bearer $newAccessToken")
            .build()

        return chain.proceed(newRequest)
    }

    private fun handleFailedTokenRefresh() {
        Timber.e("Token refresh failed")
        clearTokens()
    }

    private fun handleTokenRefreshException(e: Exception) {
        Timber.e(e, "Exception during token refresh")
        clearTokens()
    }

    private fun clearTokens() {
        runBlocking {
            tokenManager.deleteAccessToken()
            tokenManager.deleteRefreshToken()
        }
    }

    private fun Response.isUnauthorized(): Boolean = code == HttpURLConnection.HTTP_UNAUTHORIZED

    companion object {
        private const val AUTHORIZATION = "Authorization"
        private const val REFRESH_TOKEN = "RefreshToken"
        private const val TXN_AUTH_TOKEN = "TxnAuthToken"
        private const val SID = "sid"
    }

    private fun parseErrorResponse(errorBody: String?): ErrorResponse {
        return try {
            errorBody?.let { body ->
                Gson().fromJson(body, ErrorResponse::class.java)
            } ?: ErrorResponse(
                status = null,
                httpError = null,
                errorCode = null,
                message = "Unknown error occurred"
            )
        } catch (e: Exception) {
            Timber.e("Failed to parse error response: ${e.message}")
            ErrorResponse(
                status = null,
                httpError = null,
                errorCode = null,
                message = errorBody ?: "Unknown error occurred"
            )
        }
    }
}
