package com.ssafy.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ssafy.common.theme.HeyFYTheme
import com.ssafy.common.ui.ErrorPopUp
import com.ssafy.common.utils.ApiUtils.PIN_ATTEMPTS_EXCEEDED_MESSAGE
import kotlinx.coroutines.delay
import com.ssafy.common.R as commonR

@Composable
fun AuthScreen(
    viewModel: AuthViewModel = hiltViewModel()
) {

    val password by viewModel.pinNumber.collectAsStateWithLifecycle()
    val isPasswordError by viewModel.isPasswordError.collectAsStateWithLifecycle()
    val errorMessage by viewModel.errorMessage.collectAsStateWithLifecycle()

    LaunchedEffect(password) {
        if (password.length < 6) return@LaunchedEffect
        viewModel.refreshSid()
    }

    LaunchedEffect(isPasswordError) {
        if (isPasswordError) {
            delay(700)
            viewModel.updatePinNumber("")
            viewModel.updateIsPasswordError(false)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        PasswordContent(
            password = password,
            isError = isPasswordError,
            onPasswordChange = {
                viewModel.updatePinNumber(it)
            }
        )
    }

    if (errorMessage.isNotEmpty()) {
        ErrorPopUp(
            message = errorMessage,
            onDismiss = {
                viewModel.updateErrorMessage("")
               viewModel.goToLogin()
            }
        )
    }
}

@Composable
private fun PasswordContent(
    password: String,
    isError: Boolean,
    onPasswordChange: (String) -> Unit,
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Passward",
            style = HeyFYTheme.typography.headlineM,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                repeat(6) { index ->
                    PasswordBox(
                        isFilled = index < password.length,
                    )
                }
            }


            Text(
                text = if (isError) "Passwords do not match." else "",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.error
            )

        }

        Spacer(modifier = Modifier.height(16.dp))

        RandomNumberKeypad(
            onNumberClick = { number ->
                if (password.length < 6) {
                    onPasswordChange(password + number)
                }
            },
            onBackspaceClick = {
                if (password.isNotEmpty()) {
                    onPasswordChange(password.dropLast(1))
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun PasswordBox(
    isFilled: Boolean,
    modifier: Modifier = Modifier,
) {
    Spacer(
        modifier = modifier
            .size(18.dp)
            .clip(CircleShape)
            .background(
                color = if (isFilled) Color(0xFF9333EA) else Color.LightGray,
            )
    )
}

@Composable
private fun RandomNumberKeypad(
    onNumberClick: (String) -> Unit,
    onBackspaceClick: () -> Unit,
) {
    val randomNumbers = remember { (0..9).shuffled() }

    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            repeat(3) { index ->
                NumberButton(
                    number = randomNumbers[index].toString(),
                    onClick = { onNumberClick(randomNumbers[index].toString()) },
                    modifier = Modifier.weight(1f)
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            repeat(3) { index ->
                NumberButton(
                    number = randomNumbers[index + 3].toString(),
                    onClick = { onNumberClick(randomNumbers[index + 3].toString()) },
                    modifier = Modifier.weight(1f)
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            repeat(3) { index ->
                NumberButton(
                    number = randomNumbers[index + 6].toString(),
                    onClick = { onNumberClick(randomNumbers[index + 6].toString()) },
                    modifier = Modifier.weight(1f)
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Spacer(modifier = Modifier.weight(1f))

            NumberButton(
                number = randomNumbers[9].toString(),
                onClick = { onNumberClick(randomNumbers[9].toString()) },
                modifier = Modifier.weight(1f)
            )

            BackspaceButton(
                onClick = onBackspaceClick,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun NumberButton(
    number: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .height(80.dp)
            .clickable { onClick() },
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = number,
            style = HeyFYTheme.typography.headlineL,
            color = Color.Black
        )
    }
}

@Composable
private fun BackspaceButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {

    Box(
        modifier = modifier
            .height(80.dp)
            .clickable { onClick() },
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            painter = painterResource(commonR.drawable.icon_back),
            contentDescription = "삭제",
            tint = Color.Black
        )
    }
}
