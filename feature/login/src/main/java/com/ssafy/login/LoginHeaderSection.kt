package com.ssafy.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ssafy.common.R as commonR
import com.ssafy.common.theme.HeyFYTheme

@Composable
internal fun LoginHeaderSection() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // HeyFY 로고
        Text(
            text = "HeyFY",
            fontSize = 40.sp,
            fontFamily = FontFamily(Font(commonR.font.pretendard_black)),
            fontWeight = FontWeight.W900,
            color = Color(0xFF9784ED),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Welcome Back",
            style = HeyFYTheme.typography.headlineL,
            color = Color(0xFF111827),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Sign in to your student account",
            style = HeyFYTheme.typography.bodyM,
            color = Color(0xFF6B7280),
            textAlign = TextAlign.Center
        )
    }
}
