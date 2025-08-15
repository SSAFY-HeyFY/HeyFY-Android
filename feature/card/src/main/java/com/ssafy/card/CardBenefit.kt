package com.ssafy.card

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssafy.common.theme.HeyFYTheme
import com.ssafy.common.R as commonR

@Composable
fun CardBenefit(
    modifier: Modifier = Modifier,
    benefits: List<BenefitItem> = defaultBenefits,
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Text(
                text = "Key Benefits",
                style = HeyFYTheme.typography.labelM,
                color = Color(0xFF111827)
            )

            Spacer(modifier = Modifier.height(16.dp))

            benefits.forEach { benefit ->
                BenefitRow(benefit = benefit)
                if (benefit != benefits.last()) {
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
private fun BenefitRow(benefit: BenefitItem) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .size(44.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = benefit.imageResId),
                contentDescription = null,
                tint = benefit.color,
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = benefit.title,
                style = HeyFYTheme.typography.bodyM,
                color = Color(0xFF111827),
                maxLines = 2
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = benefit.description,
                style = HeyFYTheme.typography.bodyS2,
                color = Color(0xFF6B7280)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Box(
            modifier = Modifier
                .size(52.dp, 28.dp)
                .background(
                    color = benefit.color,
                    shape = RoundedCornerShape(14.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = benefit.percentage,
                style = HeyFYTheme.typography.bodyS,
                color = Color.White,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

data class BenefitItem(
    @DrawableRes val imageResId: Int,
    val title: String,
    val description: String,
    val percentage: String,
    val color: Color,
)

private val defaultBenefits = listOf(
    BenefitItem(
        imageResId = commonR.drawable.icon_bus,
        title = "Transportation & Telecom",
        description = "10% cashback",
        percentage = "10%",
        color = Color(0xFF2563EB) // blue-600
    ),
    BenefitItem(
        imageResId = commonR.drawable.icon_cafe,
        title = "Coffee & Convenience Store",
        description = "10% cashback",
        percentage = "10%",
        color = Color(0xFFD97706) // amber-600
    ),
    BenefitItem(
        imageResId = commonR.drawable.icon_shop,
        title = "Delivery & Shopping",
        description = "10% cashback",
        percentage = "10%",
        color = Color(0xFF16A34A) // green-600
    )
)

@Preview(
    name = "CardBenefit - Default",
    showBackground = true,
    backgroundColor = 0xFFF8F9FA
)
@Composable
private fun CardBenefitPreview() {
    HeyFYTheme {
        CardBenefit(
            modifier = Modifier.padding(16.dp)
        )
    }
}
