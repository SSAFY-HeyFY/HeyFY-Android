package com.ssafy.card

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ssafy.common.theme.HeyFYTheme
import com.ssafy.common.ui.DetailTopBar
import com.ssafy.common.utils.clickableOnce
import com.ssafy.common.R as commonR

@Composable
fun CardDetailScreen(
    viewModel: CardDetailViewModel = hiltViewModel<CardDetailViewModel>(),
) {

    val uriHandler = LocalUriHandler.current
    var isCardFlipped by remember { mutableStateOf(false) }

    val rotation by animateFloatAsState(
        targetValue = if (isCardFlipped) 180f else 0f,
        animationSpec = tween(durationMillis = 600),
        label = "card_flip"
    )

    Scaffold(
        modifier = Modifier
            .systemBarsPadding(),
        topBar = {
            DetailTopBar(
                title = "Card Recommendation",
                onBack = viewModel::back
            )
        },

        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFF9333EA))
                    .clickableOnce { uriHandler.openUri("https://www.shinhancard.com/pconts/html/card/apply/check/1229908_2206.html") },
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

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .clickableOnce { isCardFlipped = !isCardFlipped }
                    .clipToBounds(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .graphicsLayer {
                            rotationY = rotation
                            alpha = if (rotation >= 90f) 0f else 1f
                            transformOrigin = TransformOrigin.Center
                        },
                    painter = painterResource(id = commonR.drawable.image_shinhan_card),
                    contentDescription = "Card Front"
                )

                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .graphicsLayer {
                            rotationY = rotation - 180f
                            alpha = if (rotation <= 90f) 0f else 1f
                            transformOrigin = TransformOrigin.Center
                        },
                    painter = painterResource(id = commonR.drawable.image_shinhan_card_back),
                    contentDescription = "Card Back"
                )

                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.BottomEnd)
                        .offset(x = (-8).dp, y = (-8).dp)
                        .clip(CircleShape)
                        .background(Color.LightGray),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        modifier = Modifier
                            .size(12.dp)
                            .clickableOnce { isCardFlipped = !isCardFlipped }
                            .rotate(90f),
                        painter = painterResource(id = commonR.drawable.icon_rotation),
                        contentDescription = null,
                    )
                }
            }

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
