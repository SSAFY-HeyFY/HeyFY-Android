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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ssafy.common.theme.HeyFYTheme
import com.ssafy.common.utils.clickableOnce
import java.text.NumberFormat
import java.util.Locale

@Composable
internal fun AccountCard(
    modifier: Modifier = Modifier,
    isFX: Boolean,
    account: String,
    currency: Long,
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
            text = formatAccountNumber(account),
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

            AutoSizeText(
                text = formatCurrency(currency),
                style = HeyFYTheme.typography.displayL,
                color = Color.White,
                modifier = Modifier.weight(1f)
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
            account = "1031243441238400",
            currency = 1227000122700012L
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
            currency = 1227000122700012L
        )
    }
}

/**
 * 16자리 계좌번호를 3-3-4-4-2 형식으로 포맷팅
 * 예: "1234567890123456" -> "123-456-7890-1234-56"
 */
private fun formatAccountNumber(accountNo: String): String {
    return if (accountNo.length == 16) {
        "${accountNo.substring(0, 3)}-${accountNo.substring(3, 6)}-${
            accountNo.substring(
                6,
                14
            )
        }-${accountNo.substring(14, 16)}"
    } else {
        accountNo // 16자리가 아니면 원본 그대로 반환
    }
}

/**
 * 숫자에 콤마(,) 추가
 * 예: 1227000 -> "1,227,000"
 */
private fun formatCurrency(amount: Long): String {
    return NumberFormat.getNumberInstance(Locale.US).format(amount)
}

/**
 * 자동 크기 조정 텍스트 컴포넌트
 * 텍스트가 길어지면 폰트 크기를 자동으로 줄여서 한 줄에 맞춤
 */
@Composable
private fun AutoSizeText(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle,
    color: Color,
    minTextSize: Dp = 12.dp,
    maxTextSize: Dp = style.fontSize.value.dp,
) {
    val density = LocalDensity.current
    var textStyle by remember { mutableStateOf(style) }
    var readyToDraw by remember { mutableStateOf(false) }

    Text(
        text = text,
        style = textStyle,
        color = color,
        maxLines = 1,
        overflow = TextOverflow.Clip,
        softWrap = false,
        modifier = modifier.onGloballyPositioned { coordinates ->
            if (!readyToDraw) {
                readyToDraw = true
            }
        },
        onTextLayout = { textLayoutResult ->
            if (textLayoutResult.didOverflowWidth) {
                val currentFontSize = textStyle.fontSize
                val newFontSize = with(density) {
                    (currentFontSize.value * 0.9f).sp
                }

                if (newFontSize.value >= minTextSize.value) {
                    textStyle = textStyle.copy(fontSize = newFontSize)
                }
            }
        }
    )
}
