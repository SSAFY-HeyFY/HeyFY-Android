package com.ssafy.reservation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ssafy.common.text.CurrencyVisualTransformation
import com.ssafy.common.text.HeyFYVisualTransformation
import com.ssafy.common.theme.HeyFYTheme
import com.ssafy.common.utils.clickableOnce
import com.ssafy.common.R as commonR

@Composable
internal fun ReservationMainCard(
    modifier: Modifier = Modifier,
    exchangeAmount: String,
    onAmountChange: (String) -> Unit,
    receivedAmount: Double,
    targetRate: String,
    onTargetRateChange: (String) -> Unit,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier.padding(21.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CurrentExchangeRateSection(
                targetRate = targetRate,
                onTargetRateChange = onTargetRateChange,
            )

            AmountInputSection(
                exchangeAmount = exchangeAmount,
                onAmountChange = onAmountChange,
            )

            ExchangeDetailsSection(receivedAmount = receivedAmount)
        }
    }
}

@Composable
private fun CurrentExchangeRateSection(
    targetRate: String,
    onTargetRateChange: (String) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Target Exchange Rate",
            style = HeyFYTheme.typography.bodyM,
            color = Color(0xFF6B7280)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "1 USD = ",
                style = HeyFYTheme.typography.headlineL,
                color = Color(0xFF111827)
            )
            
            OutlinedTextField(
                value = targetRate,
                onValueChange = { value ->
                    if(value.length < 7) {
                        onTargetRateChange(value)
                    }

                },
                modifier = Modifier.width(120.dp),
                textStyle = HeyFYTheme.typography.headlineL.copy(
                    textAlign = TextAlign.End,
                ),
                visualTransformation = HeyFYVisualTransformation("0000.00", '0'),
                singleLine = true,
                placeholder = {
                    Text(
                        text = "0000.00",
                        style = HeyFYTheme.typography.headlineL,
                        color = Color(0xFF6B7280),
                        textAlign = TextAlign.End
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF111827),
                    unfocusedBorderColor = Color(0xFF111827),
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent
                ),
                shape = RoundedCornerShape(8.dp)
            )
            
            Text(
                text = " KRW",
                style = HeyFYTheme.typography.headlineL,
                color = Color(0xFF111827)
            )
        }
    }
}

@Composable
private fun AmountInputSection(
    exchangeAmount: String,
    onAmountChange: (String) -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row {
                Text(
                    text = "You'll Receive",
                    style = HeyFYTheme.typography.bodyM,
                    color = Color(0xFF374151)
                )

                val exchangeAmountL = exchangeAmount.toLongOrNull() ?: 0L
                val color = if (exchangeAmountL % 10 == 0L) Color(0xFF6B7280) else Color(0xFFEF4444)

                Text(
                    text = "• Trading unit: ₩10 increments",
                    style = HeyFYTheme.typography.bodyS,
                    textAlign = TextAlign.End,
                    color = color,
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .weight(1f)
                )
            }


            OutlinedTextField(
                value = exchangeAmount,
                onValueChange = { value ->
                    if (value.isEmpty()) {
                        onAmountChange(value)
                        return@OutlinedTextField
                    }

                    onAmountChange(value)
                },
                visualTransformation = CurrencyVisualTransformation("KRW"),
                modifier = Modifier.fillMaxWidth(),
                textStyle = HeyFYTheme.typography.labelL.copy(
                    textAlign = TextAlign.End,
                    color = Color(0xFF9333EA)
                ),
                suffix = {
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 4.dp),
                        text = "KRW",
                        style = HeyFYTheme.typography.bodyL,
                        color = Color(0xFF9333EA)
                    )
                },
                singleLine = true,
                placeholder = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Min ₩1,000",
                        style = HeyFYTheme.typography.bodyM.copy(
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
                label = "Amount to Exchange",
                value = "${String.format("%,.2f", receivedAmount)} USD",
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
