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
import com.ssafy.common.R as commonR
import com.ssafy.common.text.HeyFYVisualTransformation
import com.ssafy.common.theme.HeyFYTheme

@Composable
internal fun ExchangeMainCard(
    exchangeAmount: String,
    onAmountChange: (String) -> Unit,
    currentRate: Double,
    receivedAmount: Double,
    showInsufficientBalanceError: Boolean,
    updateShowInsufficientBalanceError: (Boolean) -> Unit,
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
            // Warning Alert
            WarningAlert()

            // Current Exchange Rate
            CurrentExchangeRateSection(currentRate = currentRate)

            // Amount Input Section
            AmountInputSection(
                exchangeAmount = exchangeAmount,
                onAmountChange = onAmountChange,
                receivedAmount = receivedAmount,
                showInsufficientBalanceError = showInsufficientBalanceError,
                updateShowInsufficientBalanceError = updateShowInsufficientBalanceError,
            )

            // Exchange Details
            ExchangeDetailsSection(receivedAmount = receivedAmount)
        }
    }
}

@Composable
private fun WarningAlert(
    isIncreased: Boolean = true,
) {
    val color = if (isIncreased) Color(0xFFD3F3DF) else Color(0xFFFEF2F2)
    val colorText = if (isIncreased) Color(0xFF10B981) else Color(0xFFB91C1C)
    val rotate = if (isIncreased) 0f else 1800f

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
    showInsufficientBalanceError: Boolean,
    updateShowInsufficientBalanceError: (Boolean) -> Unit,
    maxBalance: Float = 12450.5f,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Amount to Exchange",
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

                    if (!value.matches(Regex("^\\d*\\.?\\d{0,2}$"))) {
                        return@OutlinedTextField
                    }

                    val numericValue = value.toDoubleOrNull() ?: 0.0

                    if (numericValue > maxBalance) {
                        updateShowInsufficientBalanceError(true)
                    } else {
                        onAmountChange(value)
                    }
                },
                visualTransformation = HeyFYVisualTransformation("0000000000000000.00", '0'),
                modifier = Modifier.fillMaxWidth(),
                textStyle = HeyFYTheme.typography.labelL.copy(
                    textAlign = TextAlign.End
                ),
                suffix = {
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 4.dp),
                        text = "USD",
                        style = HeyFYTheme.typography.bodyL,
                        color = Color(0xFF6B7280)
                    )
                },
                placeholder = {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = "0.00",
                        style = HeyFYTheme.typography.bodyL.copy(
                            textAlign = TextAlign.End,
                        ),
                        color = Color(0xFF6B7280)
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF6B7280),
                    unfocusedBorderColor = Color(0xFF6B7280),
                    focusedContainerColor = Color(0xFFF9FAFB),
                    unfocusedContainerColor = Color(0xFFF9FAFB)
                ),
                shape = RoundedCornerShape(8.dp)
            )
        }

        val textColor = if (showInsufficientBalanceError) {
            Color(0xFFDC2626)
        } else {
            Color(0xFF000000)
        }

        Text(
            text = "Amount Available for Exchange : $${maxBalance}",
            style = HeyFYTheme.typography.bodyS,
            color = textColor,
            textAlign = TextAlign.End,
            modifier = Modifier.fillMaxWidth()
        )

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(Color(0xFFF3F4F6), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = commonR.drawable.icon_vector_bottom),
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = Color(0xFF6B7280)
                )
            }
        }

        // You'll Receive
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "You'll Receive",
                style = HeyFYTheme.typography.bodyM,
                color = Color(0xFF374151)
            )

            OutlinedTextField(
                value = String.format("%,.0f", receivedAmount),
                onValueChange = { },
                modifier = Modifier.fillMaxWidth(),
                readOnly = true,
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
private fun ExchangeDetailsSection(receivedAmount: Double) {
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

            // Divider
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color(0xFFE5E7EB))
            )

            DetailRow(
                label = "Total Amount",
                value = "${String.format("%,.0f", receivedAmount)} KRW",
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
