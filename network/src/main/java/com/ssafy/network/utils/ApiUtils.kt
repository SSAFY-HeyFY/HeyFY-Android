package com.ssafy.network.utils

import com.google.gson.Gson
import com.google.gson.JsonObject
import retrofit2.Response
import java.io.IOException

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
                val errorMessage = parseErrorMessage(response.errorBody()?.string())
                Result.failure(kotlin.Exception(errorMessage))
            }
        } catch (e: IOException) {
            Result.failure(IOException("Please check your network connection"))
        } catch (e: Exception) {
            Result.failure(kotlin.Exception("An unexpected error has occurred. Please contact the administrator ${e.message}"))
        }
    }

    private fun parseErrorMessage(errorBody: String?): String {
        return try {
            errorBody?.let { body ->
                val jsonObject = Gson().fromJson(body, JsonObject::class.java)
                jsonObject.get("message")?.asString ?: body
            } ?: "Unknown error occurred"
        } catch (e: Exception) {
            errorBody ?: "Unknown error occurred"
        }
    }
}
