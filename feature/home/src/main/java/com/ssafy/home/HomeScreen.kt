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
import androidx.hilt.navigation.compose.hiltViewModel
import com.ssafy.common.ui.HeyFYTopBar
import com.ssafy.navigation.DestinationParamConstants.CLUB
import com.ssafy.navigation.DestinationParamConstants.MENTO
import com.ssafy.common.R as commonR

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel<HomeViewModel>(),
) {

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            HeyFYTopBar()
        },

        containerColor = Color.White,
    ) { innerPadding ->
        HomeContent(
            modifier = Modifier.padding(innerPadding),
            goToCardDetail = viewModel::goToCardDetail,
            goToSendMoney = viewModel::goToSendMoney,
            goToTransaction = viewModel::goToTransaction,
            goToMentoClub = { type ->
                viewModel.goToMentoClub(type)
            },
            goToTips = viewModel::goToTips,
            goToExchange = viewModel::goToExchange,
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
    goToCardDetail: () -> Unit = {},
    goToSendMoney: () -> Unit = {},
    goToTransaction: () -> Unit = {},
    goToMentoClub: (type: String) -> Unit = {},
    goToTips: () -> Unit = {},
    goToExchange: () -> Unit = {},
) {
    Column(
        modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        SwipePagerWithIndicator(
            goToSendMoney = goToSendMoney,
            goToTransaction = goToTransaction,
            goToExchange = goToExchange,
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            HomeItem(
                modifier = Modifier.weight(1f),
                imageResId = commonR.drawable.icon_mentoring,
                content = "1 on 1\nKorean Culture\nMentoring",
                type = "Mentoring",
                onClick = { goToMentoClub(MENTO) }
            )

            Spacer(Modifier.width(6.dp))

            HomeItem(
                modifier = Modifier.weight(1f),
                imageResId = commonR.drawable.icon_club,
                content = "Recommended\nClubs\nFor You",
                type = "Club",
                onClick = { goToMentoClub(CLUB) }
            )

            Spacer(Modifier.width(6.dp))

            HomeItem(
                modifier = Modifier.weight(1f),
                imageResId = commonR.drawable.icon_tips,
                content = "Today's\nCampus\nTips",
                type = "Tips",
                onClick = goToTips,
            )
        }

        Spacer(Modifier.weight(1f))

        RecommendationCard(
            onCardClick = goToCardDetail
        )
    }
}
