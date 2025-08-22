package com.ssafy.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ssafy.common.theme.HeyFYTheme
import com.ssafy.common.utils.clickableOnce

@Composable
internal fun LoginBottomSection(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    Text(
        text = "Sign In",
        style = HeyFYTheme.typography.labelL,
        color = Color.White
    )
    Column(
        modifier = modifier
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFF9333EA))
                .clickableOnce { onClick() },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Sign In",
                style = HeyFYTheme.typography.labelL,
                color = Color.White
            )
        }
    }
}
