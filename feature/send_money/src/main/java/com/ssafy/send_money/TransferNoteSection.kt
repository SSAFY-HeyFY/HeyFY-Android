package com.ssafy.send_money

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ssafy.common.theme.HeyFYTheme

@Composable
internal fun TransferNoteSection(
    transferNote: String,
    onNoteChange: (String) -> Unit,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier.padding(24.dp)
        ) {
            Text(
                text = "Transfer Note (Optional)",
                style = HeyFYTheme.typography.labelM,
                color = Color(0xFF374151),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            OutlinedTextField(
                value = transferNote,
                onValueChange = onNoteChange,
                placeholder = {
                    Text(
                        text = "Add a note for this transfer...",
                        style = HeyFYTheme.typography.bodyL,
                        color = Color(0xFFADAEBC)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFE5E7EB),
                    unfocusedBorderColor = Color(0xFFE5E7EB),
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    cursorColor = Color(0xFF9333EA)
                ),
                maxLines = 3
            )
        }
    }
}
