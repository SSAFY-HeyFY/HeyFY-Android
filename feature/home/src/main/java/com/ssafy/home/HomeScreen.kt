package com.ssafy.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.ssafy.common.theme.HeyFYTheme
import com.ssafy.common.ui.HeyFYTopBar
import kotlin.math.absoluteValue

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

@Composable
private fun UniversityCard(
    modifier: Modifier = Modifier,
    universityName: String = "Seoul National University",
    studentNumber: String = "2024123456",
    name: String = "John Smith",
    major: String = "Computer Science",
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(Color(0xFF7D80EE), Color(0xFFC78DEB)),
                    start = Offset.Zero,
                    end = Offset.Infinite,
                ),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 12.dp, vertical = 20.dp)
    ) {
        Text(
            text = "University Card",
            style = HeyFYTheme.typography.headlineS,
            color = Color.White
        )

        Spacer(Modifier.height(4.dp))

        Text(
            text = universityName,
            style = HeyFYTheme.typography.bodyM,
            color = Color.White
        )

        Spacer(Modifier.height(8.dp))

        Row {

            Box(
                modifier = Modifier
                    .width(80.dp)
                    .height(100.dp)
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(8.dp)
                    )
            ) {

            }

            Spacer(Modifier.width(16.dp))

            Column(
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = "Student ID",
                        style = HeyFYTheme.typography.bodyM,
                        color = Color.White
                    )

                    Spacer(Modifier.width(4.dp))

                    Text(
                        text = studentNumber,
                        style = HeyFYTheme.typography.headlineS,
                        color = Color.White
                    )
                }

                Spacer(Modifier.height(16.dp))

                Text(
                    text = name,
                    style = HeyFYTheme.typography.headlineS,
                    color = Color.White
                )

                Spacer(Modifier.height(4.dp))

                Text(
                    text = major,
                    style = HeyFYTheme.typography.headlineS,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun SwipePagerWithIndicator(
    modifier: Modifier = Modifier,
) {
    val pageCount = 3
    val pagerState = rememberPagerState(pageCount = { pageCount })

    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 스와이프 가능 Pager
        HorizontalPager(
            state = pagerState,
            pageSpacing = 12.dp,
            contentPadding = PaddingValues(horizontal = 20.dp)
        ) { page ->
            UniversityCard(
                modifier = Modifier
                    .graphicsLayer {
                        val pageOffset = (
                                (pagerState.currentPage - page) + pagerState
                                    .currentPageOffsetFraction
                                ).absoluteValue
                        alpha = lerp(
                            start = 0.5f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f),
                        )
                    }
            )
        }

        // 인디케이터
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            repeat(pageCount) { index ->
                val isSelected = pagerState.currentPage == index
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .size(if (isSelected) 10.dp else 8.dp)
                        .clip(CircleShape)
                        .background(
                            if (isSelected) Color(0xFF9333EA) else Color(0xFFD1D5DB)
                        )
                )
            }
        }
    }
}

