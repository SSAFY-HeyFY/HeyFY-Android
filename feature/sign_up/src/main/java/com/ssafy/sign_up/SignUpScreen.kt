package com.ssafy.sign_up

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.ssafy.common.ui.DetailTopBar

@Composable
fun SignUpScreen() {


    Scaffold(
        modifier = Modifier
            .systemBarsPadding(),
        topBar = {
            DetailTopBar(
                title = "Sign Up",
                onBack = { /* TODO: 뒤로가기 처리 */ }
            )
        },
        bottomBar = {
            SignUpBottomSection()
        },
        containerColor = Color.White
    ) { innerPadding ->
        SignUpContent(
            modifier = Modifier.padding(innerPadding)
        )
    }
}
