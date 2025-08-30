package com.ssafy.home

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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssafy.common.text.TextFormat.formatAccountNumber
import com.ssafy.common.text.TextFormat.formatCurrencyKRW
import com.ssafy.common.text.TextFormat.formatCurrencyUSD
import com.ssafy.common.theme.HeyFYTheme
import com.ssafy.common.ui.AutoSizeText
import com.ssafy.common.utils.clickableOnce

@Composable
internal fun AccountCard(
    modifier: Modifier = Modifier,
    isFX: Boolean,
    account: String,
    currencyN: Long = 0L,
    currencyF: Double = 0.0,
    goToSendMoney: () -> Unit = {},
    goToExchange: () -> Unit = {},
    goToTransaction: () -> Unit = {},
) {

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White)
                .clickableOnce { goToTransaction() }
                .padding(20.dp),
        ) {
            Text(
                text = if (isFX) "FX Account - USD" else "Account",
                style = HeyFYTheme.typography.labelL,
                color = Color.Black
            )

            Spacer(Modifier.height(2.dp))

            Text(
                text = formatAccountNumber(account),
                style = HeyFYTheme.typography.bodyL,
                color = Color(0xFF707070)
            )

            Spacer(Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                val currencyUnit = if (isFX) "USD" else "KRW"

                Text(
                    text = currencyUnit,
                    style = HeyFYTheme.typography.headlineL,
                    color = Color(0xFF292929)
                )

                Spacer(Modifier.width(8.dp))

                AutoSizeText(
                    text = if (isFX) {
                        "$ ${formatCurrencyUSD(currencyF)}"
                    } else {
                        "₩ ${formatCurrencyKRW(currencyN)}"
                    },
                    style = HeyFYTheme.typography.displayL,
                    color = Color(0xFF292929),
                )
            }

            Row(
                Modifier.weight(1f),
                verticalAlignment = Alignment.Bottom,
            ) {

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFFEFD7FF))
                        .clickableOnce { goToSendMoney() },
                    contentAlignment = Alignment.Center,
                ) {

                    Text(
                        modifier = Modifier
                            .padding(vertical = 12.dp),
                        text = "Send Money",
                        style = HeyFYTheme.typography.labelL,
                        color =  Color(0xFF923FEA)
                    )
                }

                Spacer(Modifier.width(20.dp))

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFFEFD7FF))
                        .clickableOnce { goToExchange() },
                    contentAlignment = Alignment.Center,
                ) {

                    Text(
                        modifier = Modifier
                            .padding(vertical = 12.dp),
                        text = "Exchange",
                        style = HeyFYTheme.typography.labelL,
                        color =  Color(0xFF923FEA)
                    )
                }
            }
        }
    }

}

@Preview(
    name = "AccountCard - KRW Account",
    showBackground = true,
    backgroundColor = 0xFFF8F9FA
)
@Composable
private fun AccountCardKRWPreview() {
    HeyFYTheme {
        AccountCard(
            modifier = Modifier.padding(16.dp),
            isFX = false,
            account = "1031243441238400",
            currencyN = 1227000,
            currencyF = 122700012.2700012
        )
    }
}

@Preview(
    name = "AccountCard - FX Account",
    showBackground = true,
    backgroundColor = 0xFFF8F9FA
)
@Composable
private fun AccountCardFXPreview() {
    HeyFYTheme {
        AccountCard(
            modifier = Modifier.padding(16.dp),
            isFX = true,
            account = "1031243441238400",
            currencyN = 1227000,
            currencyF = 1227000122.700012
        )
    }
}
