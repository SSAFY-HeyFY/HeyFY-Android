package com.ssafy.id

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ssafy.common.ui.HeyFYPopUp
import com.ssafy.common.ui.HeyFYTopBar
import kotlinx.coroutines.delay

@Composable
fun IdScreen(
    viewModel: IdViewModel = hiltViewModel<IdViewModel>(),
) {
    var isShowPopUp by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { HeyFYTopBar() },
        containerColor = Color.White
    ) { innerPadding ->
        IdContent(
            modifier = Modifier.padding(innerPadding),
            onClick = { isShowPopUp = true }
        )
    }

    if (isShowPopUp) {
        HeyFYPopUp(
            onCancel = {
                isShowPopUp = false
            },
            onConfirm = {
                isShowPopUp = false
                // TODO : 송금 완료 처리
                viewModel.goToSuccess()
            },
            onDismiss = {
                isShowPopUp = false
            }
        )
    }
}

@Composable
private fun IdContent(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    var isResidenceBlurred by remember { mutableStateOf(true) }


    LaunchedEffect(isResidenceBlurred) {
        if (!isResidenceBlurred) {
            delay(60000) // 10초 대기
            isResidenceBlurred = true
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        ResidenceCard(
            isBlurred = isResidenceBlurred,
            onCardClick = { isResidenceBlurred = !isResidenceBlurred },
            modifier = Modifier.weight(0.40f)
        )

        WorkCard(
            modifier = Modifier.weight(0.35f)
        )

        RecommendJobs(
            onClick = { onClick() },
            modifier = Modifier.weight(0.25f)
        )
    }
}
