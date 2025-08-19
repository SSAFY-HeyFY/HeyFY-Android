package com.ssafy.tips

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.ssafy.common.ui.DetailTopBar
import com.ssafy.common.R as commonR

@Composable
fun TipsScreen(
    viewModel: TipsViewModel = hiltViewModel<TipsViewModel>()
) {

    val imageIds = listOf(
        commonR.drawable.image_tips_1,
        commonR.drawable.image_tips_2,
        commonR.drawable.image_tips_3,
        commonR.drawable.image_tips_4,
        commonR.drawable.image_tips_5,
        commonR.drawable.image_tips_6,
    )

    Scaffold(
        modifier = Modifier
            .systemBarsPadding(),
        topBar = {
            DetailTopBar (
                title = "Today's Tips",
                onBack = viewModel::back
            )
        },
        containerColor = Color.White
    ) { innerPadding ->
        Image(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            painter = painterResource(id = imageIds.random()),
            contentScale = ContentScale.FillBounds,
            contentDescription = null
        )
    }
}
