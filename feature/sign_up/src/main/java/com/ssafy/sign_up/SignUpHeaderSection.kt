package com.ssafy.sign_up

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ssafy.common.theme.HeyFYTheme

@Composable
internal fun SignUpHeaderSection() {
    Column {
        Text(
            text = "Create Account",
            style = HeyFYTheme.typography.headlineL,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Please enter your student ID and\ncreate a secure password to get started.",
            style = HeyFYTheme.typography.bodyM,
            color = Color(0xFF6B7280),
            lineHeight = 23.sp
        )
    }
}
