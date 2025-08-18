package com.ssafy.finance

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ssafy.common.theme.HeyFYTheme
import com.ssafy.common.R as commonR

@Composable
internal fun TuitionPaymentSection() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier.padding(17.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    painter = painterResource(id = commonR.drawable.icon_finance),
                    contentDescription = null,
                    modifier = Modifier.size(18.dp),
                    tint = Color(0xFF9333EA)
                )

                Text(
                    text = "Tuition Payment Period",
                    style = HeyFYTheme.typography.labelL,
                    color = Color(0xFF9333EA)
                )
            }

            Text(
                text = "Payment available: March 1 - March 31, 2024",
                style = HeyFYTheme.typography.bodyM.copy(fontWeight = FontWeight.Medium),
                color = Color(0xFF9333EA)
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF9784ED).copy(alpha = 0.2f)
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    contentAlignment = Alignment.TopEnd
                ) {
                    Column {
                        Text(
                            text = "Recommended Date",
                            style = HeyFYTheme.typography.bodyM,
                            color = Color(0xFF9333EA)
                        )

                        Text(
                            text = "March 15, 2024",
                            style = HeyFYTheme.typography.headlineL,
                            color = Color(0xFF9333EA)
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "The exchange rate is expected to be highest on this day",
                            style = HeyFYTheme.typography.bodyS,
                            color = Color(0xFF9333EA)
                        )
                    }

                    Icon(
                        painter = painterResource(id = commonR.drawable.icon_graph),
                        contentDescription = null,
                        modifier = Modifier
                            .size(48.dp)
                            .padding(10.dp),
                        tint = Color(0xFF9333EA)
                    )
                }
            }
        }
    }
}
