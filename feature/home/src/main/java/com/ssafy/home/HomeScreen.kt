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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ssafy.common.ui.ErrorPopUp
import com.ssafy.common.ui.HeyFYTopBar
import com.ssafy.home.domain.model.Home
import com.ssafy.home.model.HomeUiEvent
import com.ssafy.home.model.HomeUiState
import com.ssafy.navigation.DestinationParamConstants.CLUB
import com.ssafy.navigation.DestinationParamConstants.MENTO
import timber.log.Timber
import com.ssafy.common.R as commonR

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel<HomeViewModel>(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val studentId by viewModel.studentId.collectAsStateWithLifecycle()
    val normalAccount by viewModel.normalAccount.collectAsStateWithLifecycle()
    val foreignAccount by viewModel.foreignAccount.collectAsStateWithLifecycle()
    val hasNotificationPermission by viewModel.hasNotificationPermission.collectAsStateWithLifecycle()
    var errorMessage by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        if (uiState is HomeUiState.Init) {
            viewModel.action(HomeUiEvent.Init)
        }
    }

    LaunchedEffect(uiState) {
        when (uiState) {
            is HomeUiState.Error -> {
                errorMessage = (uiState as HomeUiState.Error).mag
            }

            else -> {}
        }
    }

    LaunchedEffect(hasNotificationPermission) {
        if (hasNotificationPermission) {
            viewModel.action(HomeUiEvent.RegisterToken)
        } else {
            viewModel.action(HomeUiEvent.DeleteToken)
        }
    }

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
            studentId = studentId,
            normalAccount = normalAccount,
            foreignAccount = foreignAccount,
            goToCardDetail = { viewModel.action(HomeUiEvent.ClickCard) },
            goToSendMoney = { type ->
                viewModel.action(HomeUiEvent.CLickSendMoney(type))
            },
            goToTransaction = { type ->
                viewModel.action(HomeUiEvent.ClickTransaction(type))
            },
            goToMentoClub = { type ->
                viewModel.action(HomeUiEvent.ClickMentoClub(type))
            },
            goToTips = {
                viewModel.action(HomeUiEvent.ClickTips)
            },
            goToExchange = { type ->
                viewModel.action(HomeUiEvent.ClickExchange(type))
            },
        )
    }

    if (errorMessage.isNotEmpty()) {
        ErrorPopUp(
            message = errorMessage,
            onDismiss = {
                errorMessage = ""
            }
        )
    }
}

@Composable
private fun HomeContent(
    modifier: Modifier = Modifier,
    studentId: String,
    normalAccount: Home.NAccount,
    foreignAccount: Home.FAccount,
    goToCardDetail: () -> Unit = {},
    goToSendMoney: (type: String) -> Unit = {},
    goToTransaction: (type: String) -> Unit = {},
    goToMentoClub: (type: String) -> Unit = {},
    goToTips: () -> Unit = {},
    goToExchange: (type: String) -> Unit = {},
) {
    Column(
        modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        SwipePagerWithIndicator(
            studentId = studentId,
            normalAccount = normalAccount,
            foreignAccount = foreignAccount,
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
                content = "1 on 1 Korean",
                type = "Mentoring",
                onClick = { goToMentoClub(MENTO) }
            )

            Spacer(Modifier.width(6.dp))

            HomeItem(
                modifier = Modifier.weight(1f),
                imageResId = commonR.drawable.icon_club,
                content = "Recommended",
                type = "Club",
                onClick = { goToMentoClub(CLUB) }
            )

            Spacer(Modifier.width(6.dp))

            HomeItem(
                modifier = Modifier.weight(1f),
                imageResId = commonR.drawable.icon_tips,
                content = "Campus Tip",
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
