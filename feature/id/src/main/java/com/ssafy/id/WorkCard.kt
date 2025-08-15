package com.ssafy.id

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssafy.common.theme.HeyFYTheme
import com.ssafy.common.R as commonR

@Composable
fun WorkCard(
    modifier: Modifier = Modifier,
    weeklyLimit: String = "20 hours",
    dailyLimit: String = "4 hours",
    vacationPeriod: String = "Full-time OK",
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            HeaderSection()

            Spacer(modifier = Modifier.height(12.dp))

            // 정보 행들
            InfoRow(
                label = "Weekly Limit",
                value = weeklyLimit,
            )

            Spacer(modifier = Modifier.height(8.dp))

            InfoRow(
                label = "Daily Limit",
                value = dailyLimit,
            )

            Spacer(modifier = Modifier.height(8.dp))

            InfoRow(
                label = "Vacation Period",
                value = vacationPeriod,
            )
        }
    }
}

@Composable
private fun HeaderSection() {
    Row(
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .size(40.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = commonR.drawable.icon_work),
                contentDescription = null,
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column {
            Text(
                text = "Work Permission Hours",
                style = HeyFYTheme.typography.bodyL,
                color = Color(0xFF111827),

                )

            Text(
                text = "Based on your visa type",
                style = HeyFYTheme.typography.bodyS,
                color = Color(0xFF6B7280),
            )
        }
    }
}

@Composable
private fun InfoRow(
    label: String,
    value: String,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(42.dp)
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = HeyFYTheme.typography.bodyM,
            color = Color.Black,
        )

        Text(
            text = value,
            style = HeyFYTheme.typography.headlineM,
            color = Color.Black,
        )
    }
}

@Preview(
    name = "WorkCard - Default",
    showBackground = true,
    backgroundColor = 0xFFF8F9FA
)
@Composable
private fun WorkCardPreview() {
    WorkCard(
        modifier = Modifier.padding(16.dp)
    )
}
