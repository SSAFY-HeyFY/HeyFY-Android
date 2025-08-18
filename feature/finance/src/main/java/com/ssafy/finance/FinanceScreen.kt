package com.ssafy.finance

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ssafy.common.ui.HeyFYTopBar

@Composable
fun FinanceScreen(
    viewModel: FinanceViewModel = hiltViewModel<FinanceViewModel>(),
) {
    Scaffold(
        modifier = Modifier.systemBarsPadding(),
        topBar = {
            HeyFYTopBar()
        },
        containerColor = Color(0xFFF9FAFB)
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                CurrencySection()
            }

            item {
                ExchangeRateChartSection()
            }

            item {
                BullishPredictionCard(
                    onClick = {
                        viewModel.goToExchange()
                    }
                )
            }

            item {
                TuitionPaymentSection()
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}
