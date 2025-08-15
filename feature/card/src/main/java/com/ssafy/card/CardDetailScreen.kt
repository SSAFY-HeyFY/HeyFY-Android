package com.ssafy.card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.ssafy.common.ui.DetailTopBar

@Composable
fun CardDetailScreen() {
    Scaffold(
        topBar = {
            DetailTopBar(
                title = "Card Recommendation",
                onBack = {  }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Blue)
                .padding(innerPadding)
        ) {

        }
    }
}
