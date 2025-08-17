package com.ssafy.account

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ssafy.common.theme.HeyFYTheme
import com.ssafy.common.R as commonR

@Composable
internal fun AccountRegistrationStep(
    accountNumber: String,
    onAccountNumberChange: (String) -> Unit,
) {
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
                text = "Register Your Account",
                style = HeyFYTheme.typography.headlineL,
                color = Color(0xFF111827),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Please enter your account number to verify and register your account\n",
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
                value = accountNumber,
                onValueChange = { input ->
                    if (input.length <= 12) {
                        onAccountNumberChange(input)
                    }
                },
                visualTransformation = AccountVisualTransformation("000-000-000000", '0'),
                placeholder = {
                    Text(
                        text = "000-000-000000",
                        style = HeyFYTheme.typography.bodyL,
                        color = Color(0xFFADAEBC)
                    )
                },
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
                    unfocusedContainerColor = Color.White,
                    cursorColor = Color(0xFF9333EA)
                ),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Spacer(modifier = Modifier.height(8.dp))
        }

    }
}
