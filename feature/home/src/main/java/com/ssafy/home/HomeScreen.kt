package com.ssafy.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ssafy.common.ui.HeyFYTopBar
import com.ssafy.common.R as commonR

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
    Column(
        modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        SwipePagerWithIndicator()

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            HomeItem(
                modifier = Modifier.weight(1f),
                imageResId = commonR.drawable.icon_mentoring,
                content = "1 on 1\nKorean Culture\nMentoring",
                type = "Mentoring"
            )

            Spacer(Modifier.width(6.dp))

            HomeItem(
                modifier = Modifier.weight(1f),
                imageResId = commonR.drawable.icon_club,
                content = "Recommended\nClubs\nFor You",
                type = "Club"
            )

            Spacer(Modifier.width(6.dp))

            HomeItem(
                modifier = Modifier.weight(1f),
                imageResId = commonR.drawable.icon_tips,
                content = "Today's\nCampus\nTips",
                type = "Tips"
            )
        }

        Spacer(Modifier.weight(1f))

        RecommendationCard()
    }
}
