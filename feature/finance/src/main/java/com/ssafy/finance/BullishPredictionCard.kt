package com.ssafy.finance

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ssafy.common.theme.HeyFYTheme
import com.ssafy.finance.domain.model.ExchangeRatePrediction

@Composable
internal fun BullishPredictionCard(
    modifier: Modifier = Modifier,
    prediction: ExchangeRatePrediction,
    onClick: () -> Unit = {},
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier.padding(17.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = prediction.trend,
                style = HeyFYTheme.typography.labelL.copy(fontWeight = FontWeight.SemiBold),
                color = Color(0xFF000000)
            )

            Text(
                text = prediction.trend,
                style = HeyFYTheme.typography.bodyM,
                color = Color(0xFF000000)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val percent = if (prediction.changePercent < 0) {
                    "${prediction.changePercent}"
                } else {
                    "+${prediction.changePercent}"
                }
                Text(
                    text = "${percent}% in ${prediction.periodDays} days",
                    style = HeyFYTheme.typography.labelL,
                    color = Color(0xFF10B981)
                )

                Button(
                    onClick = { onClick() },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF9333EA)
                    ),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "Exchange",
                        style = HeyFYTheme.typography.bodyM,
                        color = Color.White
                    )
                }
            }
        }
    }
}
