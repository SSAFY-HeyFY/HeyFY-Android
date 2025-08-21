package com.ssafy.send_money

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ssafy.common.text.TextFormat.formatAccountNumber
import com.ssafy.common.text.TextFormat.formatCurrencyKRW
import com.ssafy.common.text.TextFormat.formatCurrencyUSD
import com.ssafy.common.theme.HeyFYTheme
import com.ssafy.common.ui.AutoSizeText

@Composable
internal fun MyAccountSection(
    balance: Double,
    account: String,
    currency: String,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier.padding(24.dp)
        ) {
            Text(
                text = "My Account",
                style = HeyFYTheme.typography.labelL,
                color = Color(0xFF111827)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Sinhan",
                style = HeyFYTheme.typography.bodyL,
                color = Color(0xFF6B7280)
            )

            Text(
                text = formatAccountNumber(account),
                style = HeyFYTheme.typography.bodyM,
                color = Color(0xFF9CA3AF)
            )

            Spacer(modifier = Modifier.height(16.dp))

            AutoSizeText(
                text = when (currency) {
                    "KRW" -> "₩ ${formatCurrencyKRW(balance)}"
                    else -> "$ ${formatCurrencyUSD(balance)}"
                },
                style = HeyFYTheme.typography.headlineL,
                color = Color(0xFF111827),
            )
        }
    }
}
