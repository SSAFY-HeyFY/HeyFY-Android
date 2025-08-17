package com.ssafy.send_money

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.ssafy.common.theme.HeyFYTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun RecipientAccountSection(
    selectedBank: String,
    onBankSelected: (String) -> Unit,
    accountNumber: String,
    onAccountNumberChange: (String) -> Unit,
) {
    val bankList = listOf(
        "Shinhan Bank",
        "KB Kookmin Bank",
        "Hana Bank",
        "Woori Bank",
        "NH Bank",
        "IBK",
        "Korea Development Bank",
        "SC First Bank",
        "Citibank Korea",
        "KakaoBank",
        "K Bank",
        "Toss Bank"
    )
    var expanded by remember { mutableStateOf(false) }
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
                text = "Bank Name",
                style = HeyFYTheme.typography.labelM,
                color = Color(0xFF374151),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                        .height(50.dp),
                    value = selectedBank.ifEmpty { "Select Bank" },
                    onValueChange = { },
                    readOnly = true,
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFFE5E7EB),
                        unfocusedBorderColor = Color(0xFFE5E7EB),
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    ),
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    }
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.White)
                        .padding(vertical = 12.dp)
                ) {
                    bankList.forEach { bank ->
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = bank,
                                    style = HeyFYTheme.typography.bodyL,
                                    color = Color(0xFF111827)
                                )
                            },
                            onClick = {
                                onBankSelected(bank)
                                expanded = false
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp),
                            colors = MenuDefaults.itemColors(
                                textColor = Color(0xFF111827)
                            )
                        )
                    }
                }
            }

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
                    .height(50.dp),
                value = accountNumber,
                onValueChange = onAccountNumberChange,
                placeholder = {
                    Text(
                        text = "Enter account number",
                        style = HeyFYTheme.typography.bodyL,
                        color = Color(0xFFADAEBC)
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFE5E7EB),
                    unfocusedBorderColor = Color(0xFFE5E7EB),
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    cursorColor = Color(0xFF9333EA)
                ),
                singleLine = true
            )
        }
    }
}
