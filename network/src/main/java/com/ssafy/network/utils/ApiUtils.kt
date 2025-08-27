package com.ssafy.network.utils

import com.google.gson.Gson
import com.google.gson.JsonObject
import retrofit2.Response
import timber.log.Timber
import java.io.IOException
import com.ssafy.network.utils.ErrorResponse

object ApiUtils {

    suspend fun <T, R> safeApiCall(
        apiCall: suspend () -> Response<T>,
        convert: (T) -> R,
    ): Result<R> {
        return try {
            val response = apiCall()
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(convert(it))
                } ?: Result.failure(kotlin.Exception("Response Body is null"))
            } else {
                val errorResponse = parseErrorResponse(response.errorBody()?.string())
                Timber.tag("pjh").e("Error Response: $errorResponse")

                when(errorResponse.getErrorCodeOrDefault()) {
                    "EXPIRED_TOKEN" -> Result.failure(kotlin.Exception(errorResponse.getErrorCodeOrDefault()))
                    "EXPIRED_REFRESH_TOKEN" -> Result.failure(kotlin.Exception(errorResponse.getErrorCodeOrDefault()))
                    "SID_INVALID_OR_EXPIRED" -> Result.failure(kotlin.Exception(errorResponse.getErrorCodeOrDefault()))
                    "UNAUTHORIZED" -> Result.failure(kotlin.Exception(errorResponse.getDisplayMessage()))
                    else -> Result.failure(kotlin.Exception(errorResponse.getDisplayMessage()))
                }
            }
        } catch (e: IOException) {
            Result.failure(IOException("Please check your network connection"))
        } catch (e: Exception) {
            Result.failure(kotlin.Exception("An unexpected error has occurred. Please contact the administrator ${e.message}"))
        }
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
