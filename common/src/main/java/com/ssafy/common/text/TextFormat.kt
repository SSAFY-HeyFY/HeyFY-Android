package com.ssafy.common.text

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
    fun formatCurrencyKRW(balance: Long): String {
        return String.format("%,d", balance)
    }

    fun formatCurrencyUSD(balance: Double): String {
        return String.format("%,.2f", balance)
    }

    /**
     * 날짜를 영어 표기법으로 포맷팅
     * 예: "20250825" -> "Aug 25, 2025"
     */
    fun formatDateEnglish(dateString: String): String {
        return try {
            if (dateString.length == 8) {
                val year = dateString.substring(0, 4)
                val month = dateString.substring(4, 6).toInt()
                val day = dateString.substring(6, 8).toInt()

                val monthNames = listOf(
                    "Jan", "Feb", "Mar", "Apr", "May", "Jun",
                    "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
                )

                val monthName = monthNames.getOrNull(month - 1) ?: "Unknown"
                "$monthName. $day, $year"
            } else {
                dateString
            }
        } catch (e: Exception) {
            dateString
        }
    }

    /**
     * 날짜를 영어 표기법으로 포맷팅
     * 예: "2025-08-25" -> "Aug 25, 2025"
     */
    fun formatDateEnglish1(dateString: String): String {
        return try {
            if (dateString.length == 10) {
                val year = dateString.substring(0, 4)
                val month = dateString.substring(5, 7).toInt()
                val day = dateString.substring(8, 10).toInt()

                val monthNames = listOf(
                    "Jan", "Feb", "Mar", "Apr", "May", "Jun",
                    "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
                )

                val monthName = monthNames.getOrNull(month - 1) ?: "Unknown"
                "$monthName $day, $year"
            } else {
                dateString
            }
        } catch (e: Exception) {
            dateString
        }
    }
}
