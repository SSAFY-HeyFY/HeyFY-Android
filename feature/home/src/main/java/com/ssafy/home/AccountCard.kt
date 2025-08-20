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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssafy.common.theme.HeyFYTheme
import com.ssafy.common.utils.clickableOnce

@Composable
internal fun AccountCard(
    modifier: Modifier = Modifier,
    isFX: Boolean,
    account: String = "103-124-34412384",
    currency: String = "1,227,000",
    goToSendMoney: () -> Unit = {},
    goToExchange: () -> Unit = {},
    goToTransaction: () -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(Color(0xFF7D80EE), Color(0xFFC78DEB)),
                    start = Offset.Zero,
                    end = Offset.Infinite,
                ),
            )
            .clickableOnce { goToTransaction() }
            .padding(20.dp),
    ) {
        Text(
            text = if (isFX) "FX Account - USD" else "Accout",
            style = HeyFYTheme.typography.bodyL,
            color = Color.White
        )

        Spacer(Modifier.height(2.dp))

        Text(
            text = account,
            style = HeyFYTheme.typography.bodyL,
            color = Color.White
        )

        Spacer(Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            val currencyUnit = if (isFX) "USD($)" else "KRW(₩)"

            Text(
                text = currencyUnit,
                style = HeyFYTheme.typography.headlineL,
                color = Color.White
            )

            Spacer(Modifier.width(8.dp))

            Text(
                text = currency,
                style = HeyFYTheme.typography.displayL,
                color = Color.White
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
                    .background(Color.White)
                    .clickableOnce { goToSendMoney() },
                contentAlignment = Alignment.Center,
            ) {

                Text(
                    modifier = Modifier
                        .padding(vertical = 12.dp),
                    text = "Send Money",
                    style = HeyFYTheme.typography.labelL,
                    color = Color(0xFF9784ED)
                )
            }

            Spacer(Modifier.width(20.dp))

            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.White)
                    .clickableOnce { goToExchange() },
                contentAlignment = Alignment.Center,
            ) {

                Text(
                    modifier = Modifier
                        .padding(vertical = 12.dp),
                    text = "Exchange",
                    style = HeyFYTheme.typography.labelL,
                    color = Color(0xFF9784ED)
                )
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
            account = "103-124-34412384",
            currency = "1,227,000"
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
            account = "FX-001-12345678",
            currency = "5,000"
        )
    }
}
