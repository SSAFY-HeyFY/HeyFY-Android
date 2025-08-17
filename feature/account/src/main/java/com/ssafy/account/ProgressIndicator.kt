package com.ssafy.account

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ssafy.common.theme.HeyFYTheme

@Composable
internal fun ProgressIndicator(currentStep: Int) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(if (currentStep >= 1) Color(0xFF9333EA) else Color(0xFFE5E7EB)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "1",
                    style = HeyFYTheme.typography.labelM,
                    color = if (currentStep >= 1) Color.White else Color(0xFF6B7280),
                )
            }

            Box(
                modifier = Modifier
                    .width(48.dp)
                    .height(2.dp)
                    .background(Color(0xFFE5E7EB))
            )

            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(if (currentStep >= 2) Color(0xFF9333EA) else Color(0xFFE5E7EB)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "2",
                    style = HeyFYTheme.typography.labelM,
                    color = if (currentStep >= 2) Color.White else Color(0xFF6B7280),
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Account Verification",
            style = HeyFYTheme.typography.bodyM,
            color = Color(0xFF6B7280),
            textAlign = TextAlign.Center
        )
    }
}
