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
fun AccountVerificationBottomBar(
    verificationCode: List<String>,
) {
    Button(
        onClick = { /* TODO: 계속하기 처리 */ },
        enabled = verificationCode.all { it.isNotEmpty() },
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (verificationCode.all { it.isNotEmpty() }) Color(
                0xFF9333EA
            ) else Color(
                0xFFD1D5DB
            ),
            disabledContainerColor = Color(0xFFD1D5DB)
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = "Continue",
            style = HeyFYTheme.typography.labelL,
            color = if (verificationCode.all { it.isNotEmpty() }) Color.White else Color(
                0xFF6B7280
            )
        )
    }
}
