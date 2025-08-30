package com.ssafy.exchange.api

import com.ssafy.exchange.api.request.ExchangeRequest
import com.ssafy.exchange.api.request.ExchangeReservationRequest
import com.ssafy.exchange.api.response.ExchangeResponse
import com.ssafy.exchange.api.response.ExchangeAiPredictionResponse
import com.ssafy.exchange.api.response.ExchangeHistoricalAnalysisResponse
import com.ssafy.exchange.api.response.ExchangeReservationResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ExchangeApi {

    @POST("/exchange")
    suspend fun exchange(
        @Body request: ExchangeRequest
    ): Response<ExchangeResponse>

    @POST("/exchange/foreign")
    suspend fun exchangeForeign(
        @Body request: ExchangeRequest
    ): Response<ExchangeResponse>

    @POST("/exchange/reservation")
    suspend fun exchangeReservation(
        @Body request: ExchangeReservationRequest
    ): Response<ExchangeReservationResponse>

    @GET("/exchange/analysis")
    suspend fun getHistoricalAnalysis(): Response<ExchangeHistoricalAnalysisResponse>
}

