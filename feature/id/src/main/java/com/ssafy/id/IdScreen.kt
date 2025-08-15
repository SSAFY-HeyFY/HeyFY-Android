package com.ssafy.id

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ssafy.common.ui.HeyFYTopBar

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
        ResidenceCard()
    }
}
