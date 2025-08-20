package com.ssafy.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
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
    studentId: String,
    password: String,
    passwordVisible: Boolean,
    updateStudentId: (String) -> Unit,
    updatePassword: (String) -> Unit,
    updatePasswordVisible: (Boolean) -> Unit,
) {


    val scrollState = rememberScrollState()

    val passwordFocusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = modifier
            .verticalScroll(scrollState)
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        LoginHeaderSection()

        Spacer(modifier = Modifier.height(64.dp))

        InputField(

            label = "Student ID",
            value = studentId,
            onValueChange = { updateStudentId(it) },
            placeholder = "Enter your student ID",
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    passwordFocusRequester.requestFocus()
                }
            ),
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
            onValueChange = { updatePassword(it) },
            placeholder = "Enter your password",
            modifier = Modifier.focusRequester(passwordFocusRequester),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                    // TODO: 로그인 처리
                }
            ),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(
                    onClick = { updatePasswordVisible(!passwordVisible) },
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
        LoginContent(
            studentId = "",
            password = "",
            passwordVisible = false,
            updateStudentId = {},
            updatePassword = {},
            updatePasswordVisible = {}
        )
    }
}
