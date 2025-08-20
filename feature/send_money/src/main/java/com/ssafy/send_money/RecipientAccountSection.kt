package com.ssafy.send_money

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.ssafy.common.theme.HeyFYTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun RecipientAccountSection(
    memo: String,
    updateMemo: (String) -> Unit,
    accountNumber: String,
    onAccountNumberChange: (String) -> Unit,
    memoFocusRequester: FocusRequester,
    accountNumberFocusRequester: FocusRequester,
    onMemoNext: () -> Unit,
    onAccountNumberNext: () -> Unit,
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = "Recipient Account",
                style = HeyFYTheme.typography.labelL,
                color = Color(0xFF111827)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Transfer Note",
                style = HeyFYTheme.typography.labelM,
                color = Color(0xFF374151),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .focusRequester(memoFocusRequester),
                value = memo,
                onValueChange = { updateMemo(it) },
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFE5E7EB),
                    unfocusedBorderColor = Color(0xFFE5E7EB),
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                ),
                placeholder = {
                    Text(
                        text = "Add a note for this transfer...",
                        style = HeyFYTheme.typography.bodyL,
                        color = Color(0xFFADAEBC)
                    )
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(
                    onNext = { onMemoNext() }
                ),
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Account Number",
                style = HeyFYTheme.typography.labelM,
                color = Color(0xFF374151),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .focusRequester(accountNumberFocusRequester),
                value = accountNumber,
                onValueChange = onAccountNumberChange,
                placeholder = {
                    Text(
                        text = "Enter account number",
                        style = HeyFYTheme.typography.bodyL,
                        color = Color(0xFFADAEBC)
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFE5E7EB),
                    unfocusedBorderColor = Color(0xFFE5E7EB),
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    cursorColor = Color(0xFF9333EA)
                ),
                singleLine = true,
                keyboardActions = KeyboardActions(
                    onNext = { onAccountNumberNext() }
                ),
            )
        }
    }
}
