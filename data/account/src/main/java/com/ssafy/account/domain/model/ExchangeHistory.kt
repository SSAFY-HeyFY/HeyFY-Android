package com.ssafy.account.domain.model

data class ExchangeHistory(
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
