package com.ssafy.id

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssafy.common.theme.HeyFYTheme
import com.ssafy.common.R as commonR

@Composable
fun WorkCard(
    modifier: Modifier = Modifier,
    weeklyLimit: String = "20",
    vacationPeriod: String = "Full-Time",
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
                .padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            HeaderSection()

            Row {

                InfoRow(
                    modifier.weight(1f),
                    label = "Weekly",
                    value = weeklyLimit,
                    unit = "hrs"
                )

                VerticalDivider(
                    modifier = Modifier.width(1.dp),
                    color = Color(0xFFE5E7EB)
                )

                InfoRow(
                    modifier.weight(1f),
                    label = "Vacation",
                    value = vacationPeriod,
                )
            }
        }

    }
}

@Composable
private fun HeaderSection() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(32.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = commonR.drawable.icon_work),
                contentDescription = null,
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Column {
            Text(
                text = "Work Permission Hours",
                style = HeyFYTheme.typography.bodyM,
                color = Color(0xFF111827),
            )

            Text(
                text = "Based on your visa type",
                style = HeyFYTheme.typography.labelM,
                color = Color(0xFF6B7280),
            )
        }
    }
}

@Composable
private fun InfoRow(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    unit: String = "",
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))

        Text(
            modifier = Modifier,
            text = label,
            style = HeyFYTheme.typography.labelM,
            color = Color.Black,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.weight(1f))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = value,
                style = HeyFYTheme.typography.displayM,
                color = Color.Black,
            )
            if(unit.isNotEmpty()) {
                Spacer(Modifier.width(4.dp))

                Text(
                    modifier = Modifier,
                    text = unit,
                    style = HeyFYTheme.typography.bodyM,
                    color = Color.Black,
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))
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
