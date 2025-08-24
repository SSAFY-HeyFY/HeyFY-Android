package com.ssafy.home

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ssafy.common.theme.HeyFYTheme
import com.ssafy.common.utils.clickableOnce

@Composable
internal fun HomeItem(
    modifier: Modifier = Modifier,
    @DrawableRes imageResId: Int,
    content: String,
    type: String,
    onClick: () -> Unit = {},
) {
    val (color, buttonText) = when (type) {
        "Mentoring" -> Pair(Color(0xFF1B45F5), "Match")
        "Club" -> Pair(Color(0xFF22C55E), "Explore")
        else -> Pair(Color(0xFF9333EA), "View")
    }


    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        border = BorderStroke(1.dp, Color(0xFFF3F4F6))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = imageResId),
                    contentDescription = null,
                )
            }
            Spacer(Modifier.height(12.dp))

            Text(
                text = type,
                style = HeyFYTheme.typography.labelL,
                color = Color.Black,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = content,
                style = HeyFYTheme.typography.bodyS,
                color = Color.Black,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(24.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .clip(RoundedCornerShape(8.dp))
                    .border(1.dp, color, RoundedCornerShape(8.dp))
                    .clickableOnce { onClick() }
                    .padding(vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = buttonText,
                    style = HeyFYTheme.typography.bodyS,
                    color = color
                )
            }
        }
    }
}
