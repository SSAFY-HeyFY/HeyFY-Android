package com.ssafy.network.utils

import retrofit2.Response
import java.io.IOException
import kotlin.let

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
                Result.failure(
                    kotlin.Exception(
                        "API Response Error: ${
                            response.errorBody()?.byteString()
                        }"
                    )
                )
            }
        } catch (e: IOException) {
            Result.failure(IOException("Unknown Error: ${e.localizedMessage}"))
        } catch (e: Exception) {
            Result.failure(kotlin.Exception("Unknown Error: ${e.localizedMessage}"))
        }
    }
}
