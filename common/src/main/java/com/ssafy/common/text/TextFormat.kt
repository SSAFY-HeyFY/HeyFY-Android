package com.ssafy.common.text

import java.text.NumberFormat
import java.util.Locale

object TextFormat {
    /**
     * 16자리 계좌번호를 3-3-4-4-2 형식으로 포맷팅
     * 예: "1234567890123456" -> "123-456-7890-1234-56"
     */
    fun formatAccountNumber(accountNo: String): String {
        return if (accountNo.length == 16) {
            "${accountNo.substring(0, 3)}-${accountNo.substring(3, 6)}-${
                accountNo.substring(
                    6,
                    14
                )
            }-${accountNo.substring(14, 16)}"
        } else {
            accountNo // 16자리가 아니면 원본 그대로 반환
        }
    }

    /**
     * 숫자에 콤마(,) 추가
     * 예: 1227000 -> "1,227,000"
     */
    fun formatCurrencyKRW(balance: Double): String {
        return String.format("%,.0f", balance)
    }

    fun formatCurrencyUSD(balance: Double): String {
        return String.format("%,.2f", balance)
    }
}
