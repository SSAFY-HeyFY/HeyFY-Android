package com.ssafy.finance

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ssafy.common.text.TextFormat.formatDateEnglish
import com.ssafy.common.theme.HeyFYTheme
import com.ssafy.common.utils.clickableOnce
import com.ssafy.finance.domain.model.ExchangeRateTuition
import java.text.SimpleDateFormat
import java.util.Locale
import com.ssafy.common.R as commonR

@Composable
internal fun TuitionPaymentSection(
    modifier: Modifier = Modifier,
    tuition: ExchangeRateTuition,
) {

    val uriHandler = LocalUriHandler.current

    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                Icon(
                    painter = painterResource(id = commonR.drawable.icon_finance),
                    contentDescription = null,
                    modifier = Modifier.size(18.dp),
                    tint = Color.Black
                )

                Text(
                    text = "Tuition Payment Period",
                    style = HeyFYTheme.typography.labelL,
                    color = Color.Black
                )
            }


            Text(
                text = "Period: ${formatDateEnglish(tuition.period.start)} - ${
                    formatDateEnglish(
                        tuition.period.start
                    )
                }",
                style = HeyFYTheme.typography.bodyM.copy(fontWeight = FontWeight.Medium),
                color = Color.Black
            )

            Spacer(Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Column {
                    Text(
                        text = "AI-recommended",
                        style = HeyFYTheme.typography.bodyM,
                        color = Color.Black
                    )
                    Text(
                        text = "${formatDateEnglish(tuition.recommendedDate)}",
                        style = HeyFYTheme.typography.headlineL,
                        color = Color.Black
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .height(48.dp),
                    contentAlignment = Alignment.BottomEnd
                ) {

                    Button(
                        modifier = Modifier
                            .width(100.dp),
                        onClick = { uriHandler.openUri("https://www.shinhan.com/hpe/index.jsp#041007010000") },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFC78DEB)
                        ),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = "Payment",
                            style = HeyFYTheme.typography.bodyM.copy(
                                fontWeight = FontWeight.ExtraBold
                            ),
                            color = Color.White
                        )
                    }
                }

            }


//            Row(
//                modifier = Modifier.fillMaxWidth(),
//            ) {
//                Box(
//                    modifier = Modifier
//                        .weight(1f)
//                        .clip(RoundedCornerShape(8.dp))
//                        .background(Color(0xFFC78DEB))
//                        .padding(vertical = 12.dp)
//                        .clickableOnce { uriHandler.openUri("https://www.shinhan.com/hpe/index.jsp#041007010000") },
//                    contentAlignment = Alignment.Center,
//                ) {
//                    Text(
//                        text = "Tuition Payment",
//                        style = HeyFYTheme.typography.bodyM.copy(
//                            fontFamily = FontFamily(Font(commonR.font.pretendard_bold))
//                        ),
//                        color = Color.White
//                    )
//                }
//
//                Spacer(modifier = Modifier.width(8.dp))
//
//                Box(
//                    modifier = Modifier
//                        .weight(1f)
//                        .clip(RoundedCornerShape(8.dp))
//                        .background(Color(0xFFC78DEB))
//                        .padding(vertical = 12.dp)
//                        .clickableOnce { uriHandler.openUri("https://www.shinhan.com/hpe/index.jsp#041007020000") },
//                    contentAlignment = Alignment.Center,
//                ) {
//                    Text(
//                        text = "Tuition History",
//                        style = HeyFYTheme.typography.bodyM.copy(
//                            fontFamily = FontFamily(Font(commonR.font.pretendard_bold))
//                        ),
//                        color = Color.White
//                    )
//                }
//            }
        }



    }
}

