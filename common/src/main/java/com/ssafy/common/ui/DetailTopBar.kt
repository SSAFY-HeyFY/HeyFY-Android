package com.ssafy.common.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ssafy.common.R
import com.ssafy.common.theme.HeyFYTheme

@Composable
fun DetailTopBar(
    title: String = "Detail Top Bar",
    onBack: () -> Unit = {},
) {
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
        ) {
            Icon(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .align(Alignment.CenterStart)
                    .clickable { onBack() },
                painter = painterResource(id = R.drawable.icon_back),
                contentDescription = null,
                tint = Color.Black
            )

            Text(
                modifier = Modifier
                    .align(Alignment.Center),
                text = title,
                style = HeyFYTheme.typography.labelL,
                color = Color.Black,
            )
        }
        HorizontalDivider(thickness = 1.dp, color = Color(0xFFF3F4F6))
    }
}
