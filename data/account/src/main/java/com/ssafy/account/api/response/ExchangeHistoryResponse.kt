package com.ssafy.account.api.response

data class ExchangeHistoryResponse(
    val list: List<ExchangeHistoryItem>
) {
    data class ExchangeHistoryItem(
        val fromAccountNo: String,
        val toAccountNo: String,
        val currency: String,
        val currencyName: String,
        val amount: String,
        val exchangeCurrency: String,
        val exchangeCurrencyName: String,
        val exchangeAmount: String,
        val exchangeRate: String,
        val created: String
    )
}
