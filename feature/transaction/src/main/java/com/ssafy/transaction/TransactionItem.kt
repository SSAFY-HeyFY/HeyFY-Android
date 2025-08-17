package com.ssafy.transaction

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ssafy.common.theme.HeyFYTheme
import com.ssafy.common.R as commonR

@Composable
internal fun TransactionItem(
    transaction: Transaction
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
                        color = if (transaction.isIncome) Color(0xFFDCFCE7) else Color(0xFFFEE2E2),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                val rotate = if (transaction.isIncome) 0f else 180f

                Icon(
                    painter = painterResource(
                        id = commonR.drawable.icon_vector_bottom
                    ),
                    contentDescription = null,
                    tint = if (transaction.isIncome) Color(0xFF16A34A) else Color(0xFFDC2626),
                    modifier = Modifier
                        .size(18.dp)
                        .rotate(rotate)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = transaction.title,
                    style = HeyFYTheme.typography.bodyM,
                    color = Color(0xFF111827)
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = transaction.date,
                    style = HeyFYTheme.typography.bodyS,
                    color = Color(0xFF6B7280)
                )
            }

            Text(
                text = "${if (transaction.isIncome) "+" else "-"}$${String.format("%.2f", transaction.amount)}",
                style = HeyFYTheme.typography.bodyM,
                color = if (transaction.isIncome) Color(0xFF16A34A) else Color(0xFFDC2626),
                fontWeight = FontWeight.Normal
            )
        }
    }
}
