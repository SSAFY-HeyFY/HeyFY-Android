package com.ssafy.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ssafy.common.ui.HeyFYTopBar

@Composable
fun HomeScreen() {

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            HeyFYTopBar()
        },

        containerColor = Color.White,
    ) { innerPadding ->
        HomeContent(
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
private fun HomeContent(
    modifier: Modifier = Modifier,
    universityName: String = "Seoul National University",
    studentNumber: String = "2024123456",
    name: String = "John Smith",
    major: String = "Computer Science",
) {
    Box(
        modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        SwipePagerWithIndicator()
    }
}
