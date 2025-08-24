package com.ssafy.id

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssafy.common.theme.HeyFYTheme
import com.ssafy.common.R as commonR

data class JobData(
    val position: String,
    val place: String,
    val hoursPerDay: String,
    val hourlyWage: String,
)

@Composable
fun RecommendJobs(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    val jobItems = listOf(
        JobData("Cafe Staff", "Starbucks Gangnam Branch", "4h/day", "12,000/h"),
        JobData("Delivery Driver", "Coupang Eats", "6h/day", "15,000/h"),
        JobData("Tutor", "Math Academy", "3h/day", "25,000/h"),
        JobData("Store Clerk", "GS25 Hongdae", "5h/day", "10,500/h"),
        JobData("Restaurant Server", "Pizza Hut", "4h/day", "13,000/h"),
        JobData("Library Assistant", "Seoul Library", "4h/day", "11,000/h"),
        JobData("Library Assistant", "Seoul Library", "4h/day", "11,000/h"),
        JobData("Library Assistant", "Seoul Library", "4h/day", "11,000/h"),
        JobData("Library Assistant", "Seoul Library", "4h/day", "11,000/h"),
        JobData("Library Assistant", "Seoul Library", "4h/day", "11,000/h"),
        JobData("Library Assistant", "Seoul Library", "4h/day", "11,000/h"),
        JobData("Library Assistant", "Seoul Library", "4h/day", "11,000/h"),
        JobData("Library Assistant", "Seoul Library", "4h/day", "11,000/h"),
        JobData("Library Assistant", "Seoul Library", "4h/day", "11,000/h"),
        JobData("Library Assistant", "Seoul Library", "4h/day", "11,000/h"),
        JobData("Library Assistant", "Seoul Library", "4h/day", "11,000/h"),


        )

    val pagerState = rememberPagerState(pageCount = { jobItems.size })

    Column(
        modifier = modifier,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Recommend Part-time Jobs",
                style = HeyFYTheme.typography.labelL,
                color = Color.Black
            )

            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.CenterEnd
            ) {
                Text(
                    modifier = Modifier
                        .width(55.dp)
                        .background(Color(0x409333EA), CircleShape)
                        .padding(horizontal = 12.dp, vertical = 6.dp),
                    text = "${pagerState.currentPage + 1}/${jobItems.size}",
                    style = HeyFYTheme.typography.labelS,
                    color = Color(0xFF9333EA),
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(Modifier.height(12.dp))

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth()
        ) { page ->
            val job = jobItems[page]
            JobItem(
                position = job.position,
                place = job.place,
                hoursPerDay = job.hoursPerDay,
                hourlyWage = job.hourlyWage,
                onApply = onClick,
                modifier = Modifier.padding(horizontal = 4.dp)
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
                .padding(horizontal = 16.dp, vertical = 10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier.size(40.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = commonR.drawable.icon_cafe),
                        contentDescription = null,
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = position,
                        style = HeyFYTheme.typography.bodyM,
                        color = Color(0xFF111827),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = place,
                        style = HeyFYTheme.typography.labelM,
                        color = Color(0xFF6B7280),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                FilledTonalButton(
                    onClick = onApply,
                    shape = RoundedCornerShape(6.dp),
                    colors = ButtonDefaults.filledTonalButtonColors(
                        containerColor = Color(0xFF9333EA),
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = "Apply",
                        style = HeyFYTheme.typography.labelM
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Spacer(Modifier.weight(1f))
                InfoChip(imageRes = commonR.drawable.icon_time, text = hoursPerDay)
                InfoChip(imageRes = commonR.drawable.icon_won, text = hourlyWage)
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
            modifier = Modifier.size(10.dp)
        )
        Spacer(modifier = Modifier.width(3.dp))
        Text(
            text = text,
            style = HeyFYTheme.typography.labelS,
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

