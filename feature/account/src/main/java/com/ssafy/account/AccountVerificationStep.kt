package com.ssafy.account

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ssafy.common.text.TextFormat.formatAccountNumber
import com.ssafy.common.theme.HeyFYTheme
import com.ssafy.common.utils.clickableOnce
import com.ssafy.common.R as commonR

@Composable
internal fun AccountVerificationStep(
    accountNumber: String,
    verificationCode: List<String>,
    onVerificationCodeChange: (Int, String) -> Unit,
    timeRemaining: Int,
    onResendCode: () -> Unit,
) {
    val minutes = timeRemaining / 60
    val seconds = timeRemaining % 60
    val timeString = String.format("%02d:%02d", minutes, seconds)

    Column {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF9333EA).copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = commonR.drawable.icon_finance),
                    contentDescription = null,
                    tint = Color(0xFF9333EA),
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Verify Your Account",
                style = HeyFYTheme.typography.headlineL,
                color = Color(0xFF111827),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Please enter your account password and the 6-digit verification code sent to your registered phone number.",
                style = HeyFYTheme.typography.bodyM,
                color = Color(0xFF6B7280),
                textAlign = TextAlign.Center,
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Column {
            Text(
                text = "Account Number",
                style = HeyFYTheme.typography.labelM,
                color = Color(0xFF374151),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            OutlinedTextField(
                value = if (accountNumber.isNotEmpty()) formatAccountNumber(accountNumber) else "000-000-00000000-00",
                onValueChange = { },
                readOnly = true,
                trailingIcon = {
                    Icon(
                        painter = painterResource(id = commonR.drawable.icon_finance),
                        contentDescription = null,
                        tint = Color(0xFFADAEBC),
                        modifier = Modifier.size(18.dp)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFD1D5DB),
                    unfocusedBorderColor = Color(0xFFD1D5DB),
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                ),
                singleLine = true,
            )

            Spacer(modifier = Modifier.height(8.dp))

        }

        Spacer(modifier = Modifier.height(16.dp))

        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Verification Code",
                    style = HeyFYTheme.typography.labelM,
                    color = Color(0xFF374151),
                )

                Text(
                    text = "Verify Code",
                    style = HeyFYTheme.typography.labelM,
                    color = Color(0xFF9333EA),
                    modifier = Modifier.clickableOnce { onResendCode() }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                val focusRequesters = remember { List(4) { FocusRequester() } }

                repeat(4) { index ->
                    OutlinedTextField(
                        value = verificationCode[index],
                        onValueChange = { value ->
                            if (value.length <= 1 && value.all { it.isDigit() }) {
                                onVerificationCodeChange(index, value)
                                if (value.isNotEmpty() && index < 3) {
                                    focusRequesters[index + 1].requestFocus()
                                }
                            }
                        },
                        modifier = Modifier
                            .size(48.dp)
                            .focusRequester(focusRequesters[index]),
                        shape = RoundedCornerShape(8.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF9333EA),
                            unfocusedBorderColor = Color(0xFFE5E7EB),
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            cursorColor = Color(0xFF9333EA)
                        ),
                        singleLine = true,
                        textStyle = HeyFYTheme.typography.labelM.copy(
                            textAlign = TextAlign.Center
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )

                    if (index < 3) {
                        Spacer(Modifier.width(8.dp))
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Code expires in",
                    style = HeyFYTheme.typography.bodyM,
                    color = Color(0xFF111827)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = timeString,
                    style = HeyFYTheme.typography.labelL,
                    color = Color.Black
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}
