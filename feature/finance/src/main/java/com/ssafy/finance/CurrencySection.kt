package com.ssafy.finance

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ssafy.common.theme.HeyFYTheme
import com.ssafy.finance.domain.model.ExchangeRateCurrent
import com.ssafy.common.R as commonR

@Composable
internal fun CurrencySection(
    modifier: Modifier = Modifier,
    current: ExchangeRateCurrent,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        CurrencyCard(
            modifier = Modifier.weight(1f),
            currency = "USD",
            amount = current.usd.rate.toString(),
            change = current.usd.fluctuation,
        )

        CurrencyCard(
            modifier = Modifier.weight(1f),
            currency = "CNY",
            amount = current.cny.rate.toString(),
            change = current.cny.fluctuation,
        )

        CurrencyCard(
            modifier = Modifier.weight(1f),
            currency = "VND",
            amount = current.vnd.rate.toString(),
            change = current.vnd.fluctuation,
        )
    }
}

@Composable
private fun CurrencyCard(
    currency: String,
    amount: String,
    change: Double,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.height(110.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier.padding(17.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = currency,
                    style = HeyFYTheme.typography.bodyM,
                    color = Color(0xFF6B7280)
                )

                // Currency symbol placeholder
                Text(
                    text = when (currency) {
                        "USD" -> "$"
                        "CNY" -> "¥"
                        "VND" -> "₫"
                        else -> ""
                    },
                    style = HeyFYTheme.typography.bodyM,
                    color = Color(0xFF6B7280)
                )
            }

            Text(
                text = amount,
                style = HeyFYTheme.typography.labelL,
                color = Color(0xFF111827)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                val rotate = when {
                    change > 0.0 -> 180f
                    change < 0.0 -> 0f
                    else -> -1f
                }

                val color = when {
                    change > 0.0 -> Color(0xFF10B981)
                    change < 0.0 -> Color(0xFFEF4444)
                    else -> Color.LightGray
                }
                if(rotate > -1f) {
                    Icon(
                        painter = painterResource(id = commonR.drawable.icon_vector_bottom),
                        contentDescription = null,
                        modifier = Modifier
                            .size(12.dp)
                            .rotate(rotate),
                        tint = color
                    )
                }

                Text(
                    text = change.toString(),
                    style = HeyFYTheme.typography.bodyS,
                    color = color
                )
            }
        }
    }
}
