package com.ssafy.send_money

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ssafy.common.theme.HeyFYTheme

@Composable
internal fun SendMoneyBottomBar(
    isEnabled: Boolean,
    onContinue: () -> Unit,
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color.White,
        shadowElevation = 8.dp
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .imePadding()
                .padding(16.dp)
        ) {
            Button(
                onClick = onContinue,
                enabled = isEnabled,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isEnabled) Color(0xFF9333EA) else Color(0xFFD1D5DB),
                    disabledContainerColor = Color(0xFFD1D5DB)
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Continue Transfer",
                    style = HeyFYTheme.typography.labelL,
                    color = if (isEnabled) Color.White else Color(0xFF6B7280)
                )
            }
        }
    }
}
