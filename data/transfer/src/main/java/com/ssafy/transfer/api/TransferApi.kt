package com.ssafy.transfer.api

import com.ssafy.transfer.api.request.TransferRequest
import com.ssafy.transfer.api.response.TransferResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface TransferApi {

    @POST("/transfers/domestic")
    suspend fun transferDomestic(
        @Body request: TransferRequest,
    ): Response<TransferResponse>

    @POST("/transfers/foreign")
    suspend fun transferForeign(
        @Body request: TransferRequest,
    ): Response<TransferResponse>
}
