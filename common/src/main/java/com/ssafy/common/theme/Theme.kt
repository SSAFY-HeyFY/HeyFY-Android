package com.ssafy.common.theme

import androidx.annotation.Keep
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable


@Composable
@Keep
fun HeyFYTheme(
    content: @Composable () -> Unit
) {
    val HeyFYTypography = HeyFYTheme.typography

    CompositionLocalProvider(
        LocalHeyFYTypography provides HeyFYTypography,
    ) {
        MaterialTheme(
            content = content
        )
    }
}

@Keep
object HeyFYTheme {

    val typography: HeyFYTypographys
        @Composable
        @ReadOnlyComposable
        get() = LocalHeyFYTypography.current
}

