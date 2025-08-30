package com.ssafy.transaction

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ssafy.common.text.TextFormat.formatAccountNumber
import com.ssafy.common.theme.HeyFYTheme
import com.ssafy.common.utils.clickableOnce

@Composable
internal fun CurrentBalanceSection(
    balance: String,
    accountNumber: String,
    goToExchangeHistory: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Current Balance",
            style = HeyFYTheme.typography.bodyM,
            color = Color(0xFF000000),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = balance,
            fontSize = 30.sp,
            fontWeight = FontWeight.Normal,
            color = Color(0xFF000000),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = formatAccountNumber(accountNumber),
            style = HeyFYTheme.typography.bodyL,
            color = Color(0xFF000000),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFFEFD7FF))
                    .clickableOnce { goToExchangeHistory() },
                contentAlignment = Alignment.Center,
            ) {

                Text(
                    modifier = Modifier
                        .padding(vertical = 12.dp),
                    text = "Exchange Hist.",
                    style = HeyFYTheme.typography.labelL,
                    color = Color(0xFF923FEA)
                )
            }

            Spacer(Modifier.width(20.dp))

            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFFEFD7FF))
                    .clickableOnce {  },
                contentAlignment = Alignment.Center,
            ) {

                Text(
                    modifier = Modifier
                        .padding(vertical = 12.dp),
                    text = "Exchange Res.",
                    style = HeyFYTheme.typography.labelL,
                    color = Color(0xFF923FEA)
                )
            }
        }
    }
}
