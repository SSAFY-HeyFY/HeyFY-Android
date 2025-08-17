package com.ssafy.sign_up

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssafy.common.theme.HeyFYTheme
import com.ssafy.common.ui.InputField
import com.ssafy.common.R as commonR

@Composable
fun SignUpContent(
    modifier: Modifier = Modifier,
) {
    var studentId by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        Column {
            Spacer(modifier = Modifier.height(32.dp))

            SignUpHeaderSection()

            Spacer(modifier = Modifier.height(32.dp))

            InputField(
                label = "Student ID",
                value = studentId,
                onValueChange = { studentId = it },
                placeholder = "Enter your student ID",
                trailingIcon = {
                    Icon(
                        painter = painterResource(id = commonR.drawable.icon_id),
                        contentDescription = null,
                        tint = Color(0xFFADAEBC),
                        modifier = Modifier.size(18.dp)
                    )
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            InputField(
                label = "Password",
                value = password,
                onValueChange = { password = it },
                placeholder = "Create a password",
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(
                        onClick = { passwordVisible = !passwordVisible },
                        modifier = Modifier.size(18.dp)
                    ) {
                        Icon(
                            painter = painterResource(
                                id = if (passwordVisible) commonR.drawable.icon_eye_close
                                else commonR.drawable.icon_eye_open
                            ),
                            contentDescription = null,
                            tint = Color(0xFFADAEBC),
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            InputField(
                label = "Confirm Password",
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                placeholder = "Confirm your password",
                visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(
                        onClick = { confirmPasswordVisible = !confirmPasswordVisible },
                        modifier = Modifier.size(18.dp)
                    ) {
                        Icon(
                            painter = painterResource(
                                id = if (confirmPasswordVisible) commonR.drawable.icon_eye_open
                                else commonR.drawable.icon_eye_close
                            ),
                            contentDescription = null,
                            tint = Color(0xFFADAEBC),
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            )
        }
    }
}

@Preview(
    name = "SignUpContent",
    showBackground = true,
    backgroundColor = 0xFFF9FAFB
)
@Composable
private fun SignUpContentPreview() {
    HeyFYTheme {
        SignUpContent()
    }
}

