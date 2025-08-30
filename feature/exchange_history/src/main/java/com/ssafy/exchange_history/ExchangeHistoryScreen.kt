package com.ssafy.exchange_history

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
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ssafy.account.domain.model.ExchangeHistory
import com.ssafy.common.text.TextFormat
import com.ssafy.common.theme.HeyFYTheme
import com.ssafy.common.ui.DetailTopBar
import com.ssafy.common.ui.ErrorPopUp
import com.ssafy.exchange_history.model.ExchangeHistoryUiEvent
import com.ssafy.exchange_history.model.ExchangeHistoryUiState
import com.ssafy.common.R as commonR

@Composable
fun ExchangeHistoryScreen(
    viewModel: ExchangeHistoryViewModel = hiltViewModel<ExchangeHistoryViewModel>(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val exchanges by viewModel.exchanges.collectAsStateWithLifecycle()
    var errorMessage by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        if (uiState is ExchangeHistoryUiState.Init) {
            viewModel.action(ExchangeHistoryUiEvent.Init)
        }
    }

    LaunchedEffect(uiState) {
        when (uiState) {
            is ExchangeHistoryUiState.Error -> {
                errorMessage = (uiState as ExchangeHistoryUiState.Error).mag
            }

            else -> {}
        }
    }

    Scaffold(
        modifier = Modifier.systemBarsPadding(),
        topBar = {
            DetailTopBar(
                title = "Transaction History",
                onBack = viewModel::back
            )
        },
        containerColor = Color.White
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(exchanges) { exchange ->
                ExchangeItem(exchange = exchange)
            }
        }
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
internal fun ExchangeItem(
    exchange: ExchangeHistory,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(17.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        color = Color.LightGray,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {

                Icon(
                    painter = painterResource(
                        id = commonR.drawable.icon_rotation
                    ),
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier
                        .size(18.dp)
                        .rotate(90f)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = exchange.exchangeRate,
                    style = HeyFYTheme.typography.bodyM,
                    color = Color(0xFF111827)
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = TextFormat.formatDateEnglish(exchange.created),
                    style = HeyFYTheme.typography.bodyS,
                    color = Color(0xFF6B7280)
                )
            }

            Text(
                text = exchange.amount,
                style = HeyFYTheme.typography.bodyM,
                color = Color.Black,
                fontWeight = FontWeight.Normal
            )
        }
    }
}

