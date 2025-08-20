package com.ssafy.success

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.ssafy.common.theme.HeyFYTheme
import com.ssafy.common.utils.clickableOnce

@Composable
fun SuccessScreen(
    viewModel: SuccessViewModel = hiltViewModel<SuccessViewModel>(),
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.Asset("animation_success.json"))

    Scaffold(
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFF9333EA))
                    .clickableOnce { viewModel.goToMain() },
                contentAlignment = Alignment.Center

            ) {
                Text(
                    modifier = Modifier
                        .padding(vertical = 18.dp),
                    text = "OK",
                    style = HeyFYTheme.typography.labelL,
                    color = Color.White
                )
            }
        },
        containerColor = Color.White
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                Modifier.padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                LottieAnimation(
                    modifier = Modifier
                        .size(100.dp),
                    contentScale = ContentScale.Crop,
                    composition = composition,
                )

                Spacer(Modifier.height(16.dp))

                Text(
                    text = "Complete",
                    style = HeyFYTheme.typography.headlineL,
                    color = Color.Black
                )
            }
        }
    }
}

@Composable
@Preview
private fun SuccessViewPreview() {
    SuccessScreen()
}
