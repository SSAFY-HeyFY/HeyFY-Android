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
    var isResidenceBlurred by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(state = scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ResidenceCard(
            isBlurred = isResidenceBlurred,
            onCardClick = { isResidenceBlurred = !isResidenceBlurred }
        )

        Spacer(Modifier.height(16.dp))

        WorkCard()

        Spacer(Modifier.height(16.dp))

        RecommendJobs(
            onClick = { onClick() }
        )
    }
}
