package com.ssafy.account

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ssafy.common.theme.HeyFYTheme

@Composable
fun AccountRegistrationBottomBar(
    accountNumber: String,
    onClick: () -> Unit
) {

    Button(
        onClick = { onClick() },
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (accountNumber.length > 15) Color(
                0xFF9333EA
            ) else Color(
                0xFFD1D5DB
            ),
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = "Verify Account",
            style = HeyFYTheme.typography.labelL,
            color = if (accountNumber.length > 15) Color.White else Color(
                0xFF6B7280
            )
        )
    }
}
