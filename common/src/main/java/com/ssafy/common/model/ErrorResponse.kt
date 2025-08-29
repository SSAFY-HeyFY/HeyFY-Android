package com.ssafy.common.model

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("status")
    val status: Int?,

    @SerializedName("httpError")
    val httpError: String?,

    @SerializedName("errorCode")
    val errorCode: String?,

    @SerializedName("message")
    val message: String?
) {
    fun getDisplayMessage(): String {
        return when {
            !message.isNullOrBlank() -> message
            !errorCode.isNullOrBlank() -> errorCode
            !httpError.isNullOrBlank() -> httpError
            else -> "Unknown error occurred"
        }
    }

    fun getErrorCodeOrDefault(): String {
        return errorCode ?: "UNKNOWN_ERROR"
    }
}
