package com.ssafy.common.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ssafy.common.R as commonR

@Composable
fun HeyFYTopBar(
    modifier: Modifier = Modifier,
) {
    Column {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(56.dp),
            contentAlignment = Alignment.CenterStart,
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 20.dp),
                text = "HeyFY",
                style = TextStyle(
                    fontSize = 24.sp,
                    lineHeight = 28.sp,
                    fontFamily = FontFamily(Font(commonR.font.pretendard_black)),
                    fontWeight = FontWeight(900),
                    color = Color(0xFF9784ED)
                )
            )
        }
        HorizontalDivider(thickness = 1.dp, color = Color(0xFFF3F4F6))
    }
}
