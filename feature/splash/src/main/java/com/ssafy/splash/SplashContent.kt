package com.ssafy.splash

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import com.ssafy.common.R as commonR

@Composable
internal fun SplashContent(
    modifier: Modifier = Modifier,
    isHome: Boolean = true,
    goToLogin: () -> Unit = {},
    goToMain: () -> Unit = {},
) {
    val fullTextF = "Foreigner"
    val shortTextF = "F"

    val fullTextY = "Young"
    val shortTextY = "Y"

    var startAnimation by remember { mutableStateOf(false) }
    var startMoveUp by remember { mutableStateOf(false) }

    val visibleCharsF by animateIntAsState(
        targetValue = if (startAnimation) shortTextF.length else fullTextF.length,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "chars_f"
    )

    val visibleCharsY by animateIntAsState(
        targetValue = if (startAnimation) shortTextY.length else fullTextY.length,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "chars_y"
    )

    val fontSize by animateFloatAsState(
        targetValue = if (startAnimation) 40f else 30f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "font_size"
    )

    val alpha by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0.7f,
        animationSpec = tween(
            durationMillis = 1200,
            easing = LinearOutSlowInEasing
        ),
        label = "alpha"
    )

    val offsetY by animateFloatAsState(
        targetValue = if (startMoveUp) -232f else 0f,
        animationSpec = tween(
            durationMillis = 1000,
        ),
        label = "offset_y"
    )
    val offsetYForMain by animateFloatAsState(
        targetValue = if (startMoveUp) -330f else 0f,
        animationSpec = tween(
            durationMillis = 1000,
        ),
        label = "offset_y"
    )

    val offsetXForMain by animateFloatAsState(
        targetValue = if (startMoveUp) -120f else 0f,
        animationSpec = tween(
            durationMillis = 1000,
        ),
        label = "offset_y"
    )

    val sizeForMain by animateFloatAsState(
        targetValue = if (startMoveUp) 24f else 40f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "font_size"
    )

    LaunchedEffect(Unit) {
        delay(1500)
        startAnimation = true
        delay(500)
        startMoveUp = true
        delay(1000)
        if (isHome) goToMain() else goToLogin()
    }

    val y = if (isHome) offsetYForMain else offsetY
    val x = if (isHome) offsetXForMain else 0f
    val size = if (isHome && startMoveUp) sizeForMain else fontSize

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {

        Row(
            modifier = Modifier.offset(
                y = y.dp,
                x = x.dp,
            )
        ) {
            Text(
                text = "Hey",
                fontSize = size.sp,
                fontFamily = FontFamily(Font(commonR.font.pretendard_black)),
                fontWeight = FontWeight(900),
                color = Color(0xFF9784ED).copy(alpha = alpha)
            )

            if (!startAnimation) Text(text = " ")

            Text(
                text = fullTextF.take(visibleCharsF) +
                        shortTextF.drop(visibleCharsF.coerceAtMost(shortTextF.length)),
                fontSize = size.sp,
                fontFamily = FontFamily(Font(commonR.font.pretendard_black)),
                fontWeight = FontWeight(900),
                color = Color(0xFF9784ED).copy(alpha = alpha)
            )

            if (!startAnimation) Text(text = " ") // 공백

            Text(
                text = fullTextY.take(visibleCharsY) +
                        shortTextY.drop(visibleCharsY.coerceAtMost(shortTextY.length)),
                fontSize = size.sp,
                fontFamily = FontFamily(Font(commonR.font.pretendard_black)),
                fontWeight = FontWeight(900),
                color = Color(0xFF9784ED).copy(alpha = alpha)
            )
        }
    }
}

@Preview
@Composable
private fun SplashContentPreview() {
    SplashContent()
}
