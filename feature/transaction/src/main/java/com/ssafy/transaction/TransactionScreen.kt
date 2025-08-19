package com.ssafy.transaction

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ssafy.common.ui.DetailTopBar

data class Transaction(
    val id: String,
    val title: String,
    val date: String,
    val amount: Double,
    val isIncome: Boolean,
)

@Composable
fun TransactionScreen(
    viewModel: TransactionViewModel = hiltViewModel<TransactionViewModel>()
) {
    val currentBalance = 12847.50
    val accountNumber = "103-12344123-24-84"

    val transactions = listOf(
        Transaction("1", "Salary Payment", "Today, 2:30 PM", 3200.00, true),
        Transaction("2", "Amazon Purchase", "Yesterday, 4:15 PM", 89.99, false),
        Transaction("3", "Netflix Subscription", "Dec 15, 11:00 AM", 15.99, false),
        Transaction("4", "Starbucks Coffee", "Dec 14, 8:30 AM", 5.75, false),
        Transaction("5", "Transfer from John", "Dec 13, 6:45 PM", 125.00, true),
        Transaction("6", "Gas Station", "Dec 12, 3:20 PM", 45.30, false),
        Transaction("7", "Restaurant Bill", "Dec 11, 7:15 PM", 67.80, false)
    )

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
            item {
                CurrentBalanceSection(
                    balance = currentBalance,
                    accountNumber = accountNumber
                )
            }

            items(transactions) { transaction ->
                TransactionItem(transaction = transaction)
            }
        }
    }
}
