package com.ssafy.finance.api

import com.ssafy.finance.api.response.ExchangeRateCurrentResponse
import com.ssafy.finance.api.response.ExchangeRateHistoriesResponse
import com.ssafy.finance.api.response.ExchangeRatePredictionResponse
import com.ssafy.finance.api.response.ExchangeRateTuitionResponse
import retrofit2.Response
import retrofit2.http.GET

interface FinanceApi {

    @GET("/exchange-rate/tuition")
    suspend fun getExchangeRateTuition(): Response<ExchangeRateTuitionResponse>

    @GET("/exchange-rate/prediction")
    suspend fun getExchangeRatePrediction(): Response<ExchangeRatePredictionResponse>

    @GET("/exchange-rate/histories")
    suspend fun getExchangeRateHistories(): Response<ExchangeRateHistoriesResponse>

    @GET("/exchange-rate/current")
    suspend fun getExchangeRateCurrent(): Response<ExchangeRateCurrentResponse>
}
