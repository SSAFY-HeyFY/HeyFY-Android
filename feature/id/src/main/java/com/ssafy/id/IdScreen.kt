package com.ssafy.id

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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

    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(state = scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ResidenceCard()

        Spacer(Modifier.height(16.dp))

        WorkCard()

        Spacer(Modifier.height(16.dp))

        RecommendJobs()
    }
}
