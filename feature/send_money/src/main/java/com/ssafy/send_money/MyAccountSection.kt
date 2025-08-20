package com.ssafy.send_money

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ssafy.common.theme.HeyFYTheme

@Composable
internal fun MyAccountSection(maxBalance: Double, currency: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier.padding(24.dp)
        ) {
            Text(
                text = "My Account",
                style = HeyFYTheme.typography.labelL,
                color = Color(0xFF111827)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Sinhan",
                style = HeyFYTheme.typography.bodyL,
                color = Color(0xFF6B7280)
            )

            Text(
                text = "103-12344123-24-84",
                style = HeyFYTheme.typography.bodyM,
                color = Color(0xFF9CA3AF)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = when (currency) {
                    "KRW" -> "₩ ${String.format("%,.0f", maxBalance)}"
                    else -> "$ ${String.format("%,.2f", maxBalance)}"
                },
                style = HeyFYTheme.typography.headlineL,
                color = Color(0xFF111827)
            )
        }
    }
}
