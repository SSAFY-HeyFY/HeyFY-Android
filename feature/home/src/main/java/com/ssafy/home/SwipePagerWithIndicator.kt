package com.ssafy.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.ssafy.navigation.DestinationParamConstants.ACCOUNT
import com.ssafy.navigation.DestinationParamConstants.FX_ACCOUNT
import kotlin.math.absoluteValue


@Composable
internal fun SwipePagerWithIndicator(
    modifier: Modifier = Modifier,
    goToSendMoney: (type: String) -> Unit = {},
    goToTransaction: () -> Unit = {},
    goToExchange: () -> Unit = {},
) {
    val pageCount = 3
    val pagerState = rememberPagerState(pageCount = { pageCount })

    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            state = pagerState,
            pageSpacing = 12.dp,
            contentPadding = PaddingValues(horizontal = 20.dp)
        ) { page ->

            val modifierPager = Modifier
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

            when (page) {
                0 -> UniversityCard(
                    modifier = modifierPager
                )

                1 -> AccountCard(
                    modifier = modifierPager,
                    isFX = false,
                    goToSendMoney = { goToSendMoney(ACCOUNT) } ,
                    goToTransaction = goToTransaction,
                    goToExchange = goToExchange,
                )

                2 -> AccountCard(
                    modifier = modifierPager,
                    isFX = true,
                    goToSendMoney = { goToSendMoney(FX_ACCOUNT) },
                    goToTransaction = goToTransaction,
                    goToExchange = goToExchange,
                )
            }
        }

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
