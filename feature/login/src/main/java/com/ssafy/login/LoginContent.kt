package com.ssafy.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
internal fun LoginContent(
    modifier: Modifier = Modifier,
) {

    var studentId by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        LoginHeaderSection()

        Spacer(modifier = Modifier.height(64.dp))

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
            placeholder = "Enter your password",
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
    }
}

@Preview(
    name = "LoginContent",
    showBackground = true,
    backgroundColor = 0xFFF9FAFB
)
@Composable
private fun LoginContentPreview() {
    HeyFYTheme {
        LoginContent()
    }
}
