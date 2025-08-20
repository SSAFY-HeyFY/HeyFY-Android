package com.ssafy.common.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import kotlinx.coroutines.delay

inline fun Modifier.clickableOnce(
    isRipple: Boolean = false,
    isEnabled: Boolean = true,
    crossinline onClick: () -> Unit,
): Modifier = composed(
    inspectorInfo = {
        name = "clickableOnce"
        value = onClick()
    },
) {
    var enableAgain by remember { mutableStateOf(true) }
    LaunchedEffect(
        enableAgain,
        block = {
            if (enableAgain) return@LaunchedEffect
            delay(timeMillis = 300L)
            enableAgain = true
        },
    )
    if (isRipple) {
        this.clickable(
            enabled = isEnabled,
        ) {
            if (enableAgain) {
                enableAgain = false
                onClick()
            }
        }
    } else {
        this.clickable(
            indication = null,
            interactionSource = remember { MutableInteractionSource() },
            enabled = isEnabled,
        ) {
            if (enableAgain) {
                enableAgain = false
                onClick()
            }
        }
    }
}
