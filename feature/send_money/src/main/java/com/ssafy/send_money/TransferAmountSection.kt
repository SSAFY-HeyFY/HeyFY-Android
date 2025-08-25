package com.ssafy.send_money

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ssafy.common.text.CurrencyVisualTransformation
import com.ssafy.common.text.TextFormat.formatCurrencyKRW
import com.ssafy.common.theme.HeyFYTheme

@Composable
internal fun TransferAmountSection(
    transferAmount: String,
    balance: Long,
    currency: String,
    showInsufficientBalanceError: Boolean,
    updateShowInsufficientBalanceError: (Boolean) -> Unit,
    onAmountChange: (String) -> Unit,
    transferAmountFocusRequester: FocusRequester,
    onAmountDone: () -> Unit,
) {
    val horizontalScrollState = rememberScrollState()

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Transfer Amount",
                style = HeyFYTheme.typography.labelL,
                color = Color(0xFF111827),
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (showInsufficientBalanceError) {
                Text(
                    text = "Insufficient Balance \n[Available Balance: ₩ ${
                        formatCurrencyKRW(balance)
                    }]",
                    style = HeyFYTheme.typography.bodyM,
                    color = Color(0xFFDC2626),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            } else {
                Text(
                    text = "Enter amount to transfer\n",
                    style = HeyFYTheme.typography.bodyM,
                    color = Color(0xFF6B7280),
                    textAlign = TextAlign.Center
                )
            }

            OutlinedTextField(
                value = transferAmount,
                onValueChange = { value ->
                    if (value.isEmpty()) {
                        onAmountChange(value)
                        return@OutlinedTextField
                    }

                    val numericValue = value.toLong()

                    if (numericValue > balance) {
                        updateShowInsufficientBalanceError(true)
                    } else {
                        onAmountChange(value)
                    }
                },

                visualTransformation = CurrencyVisualTransformation(currency),
                placeholder = {
                    Text(
                        text = "₩ 0",
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFADAEBC),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .focusRequester(transferAmountFocusRequester),
                textStyle = TextStyle(
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF111827),
                    textAlign = TextAlign.Center
                ),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    cursorColor = Color(0xFF9333EA)
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { onAmountDone() }
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier
                    .horizontalScroll(horizontalScrollState),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                QuickAmountButton(
                    text = "All",
                    onClick = {
                        onAmountChange(balance.toString())
                    }
                )

                QuickAmountButton(
                    text = "₩10,000",
                    onClick = {
                        val currentAmount = transferAmount.toIntOrNull() ?: 0
                        val newAmount = currentAmount + 10000
                        if (newAmount <= balance) {
                            onAmountChange(newAmount.toString())
                        } else {
                            updateShowInsufficientBalanceError(true)
                        }
                    }
                )
                QuickAmountButton(
                    text = "₩50,000",
                    onClick = {
                        val currentAmount = transferAmount.toIntOrNull() ?: 0
                        val newAmount = currentAmount + 50000
                        if (newAmount <= balance) {
                            onAmountChange(newAmount.toString())
                        } else {
                            updateShowInsufficientBalanceError(true)
                        }
                    }
                )


                QuickAmountButton(
                    text = "₩100,000",
                    onClick = {
                        val currentAmount = transferAmount.toIntOrNull() ?: 0
                        val newAmount = currentAmount + 100000
                        if (newAmount <= balance) {
                            onAmountChange(newAmount.toString())
                        } else {
                            updateShowInsufficientBalanceError(true)
                        }
                    }
                )
            }
        }
    }
}


@Composable
private fun QuickAmountButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFF9FAFB)
        ),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
    ) {
        Text(
            text = text,
            style = HeyFYTheme.typography.bodyS,
            color = Color(0xFF374151)
        )
    }
}

