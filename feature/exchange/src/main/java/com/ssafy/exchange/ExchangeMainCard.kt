package com.ssafy.exchange

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ssafy.common.text.CurrencyVisualTransformation
import com.ssafy.common.theme.HeyFYTheme
import com.ssafy.common.utils.clickableOnce
import com.ssafy.common.R as commonR

@Composable
internal fun ExchangeMainCard(
    exchangeAmount: String,
    onAmountChange: (String) -> Unit,
    currentRate: Double,
    receivedAmount: Double,
    isUSD: Boolean,
    onToggleCurrency: () -> Unit = {},
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier.padding(21.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            WarningAlert()

            CurrentExchangeRateSection(
                currentRate = currentRate,
            )

            AmountInputSection(
                exchangeAmount = exchangeAmount,
                onAmountChange = onAmountChange,
                receivedAmount = receivedAmount,
                isUSD = isUSD,
                onToggleCurrency = onToggleCurrency,
            )

            ExchangeDetailsSection(receivedAmount = receivedAmount, isUSD = isUSD)
        }
    }
}

@Composable
private fun WarningAlert(
    isIncreased: Boolean = true,
) {
    val color = if (isIncreased) Color(0xFFD3F3DF) else Color(0xFFFEF2F2)
    val colorText = if (isIncreased) Color(0xFF10B981) else Color(0xFFB91C1C)
    val rotate = if (isIncreased) 0f else 180f

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = color
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier.padding(17.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                modifier = Modifier
                    .size(14.dp)
                    .rotate(rotate),
                painter = painterResource(id = commonR.drawable.icon_variation),
                contentDescription = null,
                tint = colorText,
            )

            Text(
                text = "You're exchanging 17 KRW lower than your last exchange",
                style = HeyFYTheme.typography.bodyM,
                color = colorText,
                lineHeight = 20.sp
            )
        }
    }
}

@Composable
private fun CurrentExchangeRateSection(
    currentRate: Double,
    isIncreased: Boolean = false,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Current Exchange Rate",
            style = HeyFYTheme.typography.bodyM,
            color = Color(0xFF6B7280)
        )

        Text(
            text = "1 USD = ${String.format("%,.2f", currentRate)} KRW",
            style = HeyFYTheme.typography.headlineL,
            color = Color(0xFF111827)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            val color = if (isIncreased) Color(0xFF22C55E) else Color(0xFFEF4444)
            val rotate = if (isIncreased) 180f else 0f

            Icon(
                painter = painterResource(id = commonR.drawable.icon_vector_bottom),
                contentDescription = null,
                modifier = Modifier
                    .size(12.dp)
                    .rotate(rotate),
                tint = color
            )

            Text(
                text = "+2.3 (0.17%)",
                style = HeyFYTheme.typography.bodyS,
                color = color
            )
        }
    }
}

@Composable
private fun AmountInputSection(
    exchangeAmount: String,
    onAmountChange: (String) -> Unit,
    receivedAmount: Double,
    isUSD: Boolean,
    onToggleCurrency: () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = if (isUSD) "Amount to Exchange (USD)" else "Amount to Exchange (KRW)",
                style = HeyFYTheme.typography.bodyM,
                color = Color(0xFF374151)
            )

            OutlinedTextField(
                value = if (isUSD) {
                    "₩ ${String.format("%,.0f", receivedAmount)}"
                } else {
                    "$ ${String.format("%,.2f", receivedAmount)}"
                },
                onValueChange = { },
                modifier = Modifier.fillMaxWidth(),
                readOnly = true,
                textStyle = HeyFYTheme.typography.labelL.copy(
                    textAlign = TextAlign.End,
                ),
                suffix = {
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 4.dp),
                        text = if (isUSD) "KRW" else "USD",
                        style = HeyFYTheme.typography.bodyL,
                        color = Color(0xFF6B7280)
                    )
                },

                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF6B7280),
                    unfocusedBorderColor = Color(0xFF6B7280),
                    focusedContainerColor = Color(0xFFF9FAFB),
                    unfocusedContainerColor = Color(0xFFF9FAFB),
                    cursorColor = Color(0xFF9333EA),
                ),
                singleLine = true,

                shape = RoundedCornerShape(8.dp)
            )
        }

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(Color(0xFFF3F4F6), CircleShape)
                    .clickableOnce { onToggleCurrency() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = commonR.drawable.icon_rotation),
                    contentDescription = null,
                    modifier = Modifier
                        .size(16.dp)
                        .rotate(90f),
                    tint = Color(0xFF6B7280)
                )
            }
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "You'll Receive",
                style = HeyFYTheme.typography.bodyM,
                color = Color(0xFF374151)
            )

            OutlinedTextField(
                value = exchangeAmount,
                onValueChange = { value ->
                    if (value.isEmpty()) {
                        onAmountChange(value)
                        return@OutlinedTextField
                    }

                    onAmountChange(value)
                },
                visualTransformation = CurrencyVisualTransformation(if (isUSD) "USD" else "KRW"),
                modifier = Modifier.fillMaxWidth(),
                textStyle = HeyFYTheme.typography.labelL.copy(
                    textAlign = TextAlign.End,
                    color = Color(0xFF9333EA)
                ),
                suffix = {
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 4.dp),
                        text = if (isUSD) "USD" else "KRW",
                        style = HeyFYTheme.typography.bodyL,
                        color = Color(0xFF9333EA)
                    )
                },
                singleLine = true,
                placeholder = {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = "${if (isUSD) "$" else "₩"} 0",
                        style = HeyFYTheme.typography.bodyL.copy(
                            textAlign = TextAlign.End,
                        ),
                        color = Color(0xFF9333EA)
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF9333EA),
                    unfocusedBorderColor = Color(0xFF9333EA),
                    focusedContainerColor = Color(0xFF9333EA).copy(alpha = 0.05f),
                    unfocusedContainerColor = Color(0xFF9333EA).copy(alpha = 0.05f)
                ),

                shape = RoundedCornerShape(8.dp)
            )
        }
    }
}

@Composable
private fun ExchangeDetailsSection(
    receivedAmount: Double,
    isUSD: Boolean = true,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF9FAFB)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            DetailRow(label = "Exchange Fee", value = "Free")
            DetailRow(label = "Processing Time", value = "Instant")

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color(0xFFE5E7EB))
            )

            DetailRow(
                label = "Total Amount",
                value = if (isUSD) {
                    "${String.format("%,.0f", receivedAmount)} KRW"
                } else {
                    "${String.format("%,.2f", receivedAmount)} USD"
                },
                valueColor = Color(0xFF1B45F5)
            )
        }
    }
}

@Composable
private fun DetailRow(
    label: String,
    value: String,
    valueColor: Color = Color(0xFF111827),
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = HeyFYTheme.typography.bodyM,
            color = if (label == "Total Amount") Color(0xFF111827) else Color(0xFF6B7280)
        )

        Text(
            text = value,
            style = HeyFYTheme.typography.bodyM,
            color = valueColor
        )
    }
}
