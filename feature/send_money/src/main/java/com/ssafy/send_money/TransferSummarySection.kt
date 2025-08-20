package com.ssafy.send_money

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ssafy.common.theme.HeyFYTheme

@Composable
internal fun TransferSummarySection(
    transferAmount: String,
    currency: String,
) {
    val amount = transferAmount.toDoubleOrNull() ?: 0.0

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF9FAFB).copy(alpha = 0.5f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Transfer Amount",
                    style = HeyFYTheme.typography.bodyM,
                    color = Color(0xFF6B7280)
                )
                Text(
                    text = when (currency) {
                        "KRW" -> "₩ ${String.format("%,.0f", amount)}"
                        else -> "$ ${String.format("%.2f", amount)}"
                    },
                    style = HeyFYTheme.typography.labelM,
                    color = Color(0xFF111827)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Transfer Fee",
                    style = HeyFYTheme.typography.bodyM,
                    color = Color(0xFF6B7280)
                )
                Text(
                    text = "Free",
                    style = HeyFYTheme.typography.labelM,
                    color = Color(0xFF111827)
                )
            }

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 12.dp),
                color = Color(0xFFE5E7EB)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Total Amount",
                    style = HeyFYTheme.typography.labelL,
                    color = Color(0xFF111827)
                )
                Text(
                    text = when (currency) {
                        "KRW" -> "₩ ${String.format("%,.0f", amount)}"
                        else -> "$ ${String.format("%.2f", amount)}"
                    },
                    style = HeyFYTheme.typography.labelL,
                    color = Color(0xFF9333EA)
                )
            }
        }
    }
}
