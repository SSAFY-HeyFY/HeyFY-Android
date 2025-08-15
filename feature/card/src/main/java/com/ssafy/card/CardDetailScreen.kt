package com.ssafy.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ssafy.common.theme.HeyFYTheme
import com.ssafy.common.ui.DetailTopBar
import com.ssafy.common.R as commonR

@Composable
fun CardDetailScreen() {
    Scaffold(
        modifier = Modifier
            .systemBarsPadding(),
        topBar = {
            DetailTopBar(
                title = "Card Recommendation",
                onBack = { }
            )
        },

        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFF9333EA)),
                contentAlignment = Alignment.Center

            ) {
                Text(
                    modifier = Modifier
                        .padding(vertical = 18.dp),
                    text = "Apply Now",
                    style = HeyFYTheme.typography.bodyL,
                    color = Color.White
                )
            }
        },
        containerColor = Color.White,
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                painter = painterResource(id = commonR.drawable.image_shinhan_card),
                contentDescription = null
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .border(1.dp, Color(0xFFF3F4F6), RoundedCornerShape(12.dp))
                        .padding(20.dp)
                ) {
                    Text(
                        text = "Shinhan SOL Global U Check",
                        style = HeyFYTheme.typography.bodyL,
                        color = Color.Black
                    )

                    Text(
                        text = "K-benefits that foreigner can enjoy together",
                        style = HeyFYTheme.typography.bodyS,
                        color = Color.Gray
                    )
                }
            }

            Spacer(Modifier.height(20.dp))

            CardBenefit(
                modifier = Modifier.padding(horizontal = 20.dp)
            )

        }
    }
}
