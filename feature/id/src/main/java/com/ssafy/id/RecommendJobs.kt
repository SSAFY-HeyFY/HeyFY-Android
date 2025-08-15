package com.ssafy.id

import androidx.annotation.DrawableRes
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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssafy.common.theme.HeyFYTheme
import com.ssafy.common.R as commonR

@Composable
fun RecommendJobs(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        Text(
            text = "Recommend Part-time Jobs",
            style = HeyFYTheme.typography.labelL,
            color = Color.Black
        )

        Spacer(
            Modifier.height(12.dp)
        )

        repeat(6) {
            JobItem()
            Spacer(
                Modifier.height(8.dp)
            )

        }
    }
}

@Composable
fun JobItem(
    modifier: Modifier = Modifier,
    position: String = "Cafe Staff",
    place: String = "Starbucks Gangnam Branch",
    hoursPerDay: String = "4h/day",
    hourlyWage: String = "2,000/h",
    distance: String = "2.1km",
    onApply: () -> Unit = {},
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
                .padding(horizontal = 18.dp, vertical = 12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier.size(48.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = commonR.drawable.icon_cafe),
                        contentDescription = null,
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = position,
                        style = HeyFYTheme.typography.bodyL,
                        color = Color(0xFF111827),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = place,
                        style = HeyFYTheme.typography.bodyS,
                        color = Color(0xFF6B7280),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                FilledTonalButton(
                    onClick = onApply,
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.filledTonalButtonColors(
                        containerColor = Color(0xFF9333EA),
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = "Apply",
                        style = HeyFYTheme.typography.bodyS
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Spacer(Modifier.weight(1f))
                InfoChip(imageRes = commonR.drawable.icon_time, text = hoursPerDay)
                InfoChip(imageRes = commonR.drawable.icon_won, text = hourlyWage)
                InfoChip(imageRes = commonR.drawable.icon_locate, text = distance)
            }
        }
    }
}

@Composable
private fun InfoChip(
    @DrawableRes imageRes: Int,
    text: String,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = null,
            modifier = Modifier.size(12.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = text,
            style = HeyFYTheme.typography.labelM,
            color = Color(0xFF6B7280)
        )
    }
}

@Preview(name = "JobItem - Default", showBackground = true, backgroundColor = 0xFFF8F9FA)
@Composable
private fun JobItemPreview() {
    HeyFYTheme {
        JobItem(
            modifier = Modifier
                .padding(16.dp)
        )
    }
}

