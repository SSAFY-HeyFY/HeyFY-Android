package com.ssafy.finance.data

import com.ssafy.common.utils.ApiUtils.safeApiCall
import com.ssafy.finance.api.response.ExchangeRateCurrentResponse
import com.ssafy.finance.api.response.ExchangeRateHistoriesResponse
import com.ssafy.finance.api.response.ExchangeRatePredictionResponse
import com.ssafy.finance.api.response.ExchangeRateTuitionResponse
import com.ssafy.finance.domain.FinanceRepository
import com.ssafy.finance.domain.model.ExchangeRateCurrent
import com.ssafy.finance.domain.model.ExchangeRateHistories
import com.ssafy.finance.domain.model.ExchangeRatePrediction
import com.ssafy.finance.domain.model.ExchangeRateTuition
import javax.inject.Inject

class FinanceRepositoryImpl @Inject constructor(
    private val financeDataSource: FinanceDataSource,
) : FinanceRepository {

    override suspend fun getExchangeRateTuition(): Result<ExchangeRateTuition> {
        return safeApiCall(
            apiCall = { financeDataSource.getExchangeRateTuition() },
            convert = { response -> response.toTuition() }
        )
    }

    override suspend fun getExchangeRatePrediction(): Result<ExchangeRatePrediction> {
        return safeApiCall(
            apiCall = { financeDataSource.getExchangeRatePrediction() },
            convert = { response -> response.toPrediction() }
        )
    }

    override suspend fun getExchangeRateHistories(): Result<ExchangeRateHistories> {
        return safeApiCall(
            apiCall = { financeDataSource.getExchangeRateHistories() },
            convert = { response -> response.toHistories() }
        )
    }

    override suspend fun getExchangeRateCurrent(): Result<ExchangeRateCurrent> {
        return safeApiCall(
            apiCall = { financeDataSource.getExchangeRateCurrent() },
            convert = { response -> response.toCurrent() }
        )
    }

    // Mapper functions
    private fun ExchangeRateTuitionResponse.toTuition(): ExchangeRateTuition {
        return ExchangeRateTuition(
            period = ExchangeRateTuition.Period(
                start = period.start,
                end = period.end
            ),
            recommendedDate = recommendedDate,
            recommendationNote = recommendationNote
        )
    }

    private fun ExchangeRatePredictionResponse.toPrediction(): ExchangeRatePrediction {
        return ExchangeRatePrediction(
            trend = trend,
            description = description,
            changePercent = changePercent,
            periodDays = periodDays,
        )
    }

    private fun ExchangeRateHistoriesResponse.toHistories(): ExchangeRateHistories {
        return ExchangeRateHistories(
            currency = currency,
            rates = rates.map { rate ->
                ExchangeRateHistories.Rate(
                    currency = rate.currency,
                    date = rate.date,
                    rate = rate.rate,
                    modelName = rate.modelName ?: "",
                    prediction = rate.prediction
                )
            }
        )
    }

    private fun ExchangeRateCurrentResponse.toCurrent(): ExchangeRateCurrent {
        return ExchangeRateCurrent(
            usd = ExchangeRateCurrent.ExchangeRate(
                currency = usd.currency,
                updatedAt = usd.updatedAt,
                rate = usd.rate,
                fluctuation = usd.fluctuation,
            ),
            cny = ExchangeRateCurrent.ExchangeRate(
                currency = cny.currency,
                updatedAt = cny.updatedAt,
                rate = cny.rate,
                fluctuation = cny.fluctuation,
            ),
            vnd = ExchangeRateCurrent.ExchangeRate(
                currency = vnd.currency,
                updatedAt = vnd.updatedAt,
                rate = vnd.rate,
                fluctuation = vnd.fluctuation,
            )
        )
    }
}
