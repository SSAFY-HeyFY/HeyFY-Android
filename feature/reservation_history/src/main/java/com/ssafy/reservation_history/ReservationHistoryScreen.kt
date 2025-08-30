package com.ssafy.reservation_history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ssafy.common.theme.HeyFYTheme
import com.ssafy.common.ui.DetailTopBar
import com.ssafy.common.ui.PasswordBottomSheet
import com.ssafy.exchange.domain.model.ExchangeReservationHistory
import com.ssafy.reservation_history.model.ReservationHistoryUiEvent
import com.ssafy.reservation_history.viewmodel.ReservationHistoryViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReservationHistoryScreen(
    viewModel: ReservationHistoryViewModel = hiltViewModel(),
) {
    val histories by viewModel.histories.collectAsStateWithLifecycle()
    val password by viewModel.pinNumber.collectAsStateWithLifecycle()
    val checkPin by viewModel.checkPin.collectAsStateWithLifecycle()
    val showPasswordBottomSheet by viewModel.showPasswordBottomSheet.collectAsStateWithLifecycle()


    var isPasswordError by remember { mutableStateOf(false) }

    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    LaunchedEffect(Unit) {
        viewModel.action(ReservationHistoryUiEvent.LoadReservationHistory)
    }

    LaunchedEffect(password) {
        if (password.length < 6) return@LaunchedEffect
        viewModel.cancelReservation(viewModel.reservationId, password)

    }

    LaunchedEffect(checkPin) {
        if(checkPin.not()) {
            isPasswordError = true
        }
    }

    LaunchedEffect(isPasswordError) {
        if(isPasswordError) {
            delay(700)
            viewModel.action(ReservationHistoryUiEvent.UpdatePinNumber(""))
            viewModel.action(ReservationHistoryUiEvent.UpdateCheckPin(true))
            isPasswordError = false
        }
    }

    Scaffold(
        modifier = Modifier
            .systemBarsPadding(),
        topBar = {
            DetailTopBar(
                title = "Reservation History",
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
            items(histories) { history ->
                ReservationHistoryItem(
                    reservation = history,
                    onCancelReservation = { reservationId ->
                        viewModel.action(ReservationHistoryUiEvent.CancelReservation(reservationId))
                    }
                )
            }
        }
    }

    if (showPasswordBottomSheet) {
        PasswordBottomSheet(
            bottomSheetState = bottomSheetState,
            password = password,
            isPasswordError = isPasswordError,
            updateShowPasswordBottomSheet = { viewModel.action(ReservationHistoryUiEvent.UpdateShowPasswordBottomSheet(it)) },
            updatePassword = { viewModel.action(ReservationHistoryUiEvent.UpdatePinNumber(it)) },
        )
    }
}

@Composable
private fun ReservationHistoryItem(
    reservation: ExchangeReservationHistory,
    onCancelReservation: (Int) -> Unit
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Header Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Reservation ID #${reservation.reservationId}",
                    style = HeyFYTheme.typography.labelL,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF111827)
                )

                ReservationStatusChip(
                    isCompleted = reservation.exchangeCompleted,
                    isCanceled = reservation.canceled
                )
            }
            
            // Exchange Info
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Exchange Amount",
                        style = HeyFYTheme.typography.bodyS,
                        color = Color(0xFF6B7280)
                    )
                    Text(
                        text = "${reservation.amount} ${reservation.currency}",
                        style = HeyFYTheme.typography.bodyL,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF111827)
                    )
                }
                
                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = "Target Rate",
                        style = HeyFYTheme.typography.bodyS,
                        color = Color(0xFF6B7280)
                    )
                    Text(
                        text = String.format("%,.2f", reservation.baseExchangeRate),
                        style = HeyFYTheme.typography.bodyL,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF111827)
                    )
                }
            }
            
            // Date
            Text(
                text = "Reservation Date: ${reservation.createdAt}",
                style = HeyFYTheme.typography.bodyS,
                color = Color(0xFF9CA3AF)
            )

            if (!reservation.exchangeCompleted && !reservation.canceled) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(
                        onClick = { onCancelReservation(reservation.reservationId) }
                    ) {
                        Text(
                            text = "Cancel Now",
                            style = HeyFYTheme.typography.bodyM,
                            color = Color(0xFFEF4444)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ReservationStatusChip(
    isCompleted: Boolean,
    isCanceled: Boolean
) {
    val (backgroundColor, textColor, text) = when {
        isCompleted -> Triple(Color(0xFF10B981), Color.White, "Complete")
        isCanceled -> Triple(Color(0xFFEF4444), Color.White, "Cancel")
        else -> Triple(Color(0xFFF59E0B), Color.White, "Wait")
    }
    
    Box(
        modifier = Modifier
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 12.dp, vertical = 4.dp)
    ) {
        Text(
            text = text,
            style = HeyFYTheme.typography.labelS,
            color = textColor,
            fontWeight = FontWeight.Medium
        )
    }
}
