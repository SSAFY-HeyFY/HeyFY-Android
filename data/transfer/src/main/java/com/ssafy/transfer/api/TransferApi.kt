package com.ssafy.transfer.api

import com.ssafy.transfer.api.request.TransferRequest
import com.ssafy.transfer.api.response.TransferResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface TransferApi {

    @POST("/transfers")
    suspend fun transfer(
        @Body request: TransferRequest,
    ): Response<TransferResponse>
}
