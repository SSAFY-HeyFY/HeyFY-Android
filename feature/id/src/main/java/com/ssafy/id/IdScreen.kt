package com.ssafy.id

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ssafy.common.theme.HeyFYTheme
import com.ssafy.common.ui.HeyFYTopBar
import com.ssafy.common.R as commonR

@Composable
fun IdScreen() {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { HeyFYTopBar() },
        containerColor = Color.White
    ) { innerPadding ->
        IdContent(Modifier.padding(innerPadding))
    }
}

@Composable
private fun IdContent(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(12.dp))
                .paint(
                    painter = painterResource(commonR.drawable.image_residence_card),
                    contentScale = ContentScale.FillBounds
                )
                .padding(12.dp)
        ) {

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TitleSection()
                HorizontalDivider(thickness = 1.dp, color = Color(0xFFF3F4F6))
                IdItem(
                    title = "외국인등록번호",
                    englishTitle = "Registration No.",
                    content = "123456-1234567",
                )
                IdItem(
                    title = "성명",
                    englishTitle = "Name",
                    content = "JOHN SMITH"
                )
                IdItem(
                    title = "국가 / 지역",
                    englishTitle = "Country / Region",
                    content = "REPUBLIC OF UTOPIA"
                )
                IdItem(
                    title = "체류 자격",
                    englishTitle = "Status",
                    content = "기업투자(D-8)"
                )
            }

            // TODO 이미지 삽입
            Box(
                modifier = Modifier
                    .size(width = 90.dp, height = 110.dp)
                    .background(Color.White, RoundedCornerShape(8.dp))
                    .align(Alignment.TopEnd)
            )
        }
    }
}

@Composable
private fun TitleSection() {
    Row(verticalAlignment = Alignment.Bottom) {
        Text(
            text = "외국인 등록증",
            style = HeyFYTheme.typography.headlineM,
            color = Color.Black
        )
        Spacer(Modifier.width(4.dp))
        Text(
            text = "RESIDENCE CARD",
            style = HeyFYTheme.typography.bodyS,
            color = Color.Black
        )
    }
}

@Composable
private fun IdItem(
    modifier: Modifier = Modifier,
    title: String,
    englishTitle: String,
    content: String,
) {
    val font = HeyFYTheme.typography.bodyS2.copy(lineHeight = 14.sp)
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.width(80.dp),
            horizontalAlignment = Alignment.End
        ) {
            Text(text = title, style = font, color = Color.Black)
            Text(text = englishTitle, style = font, color = Color.Black)
        }
        Spacer(Modifier.width(8.dp))
        Text(text = content, style = HeyFYTheme.typography.labelM, color = Color.Black)
    }
}
