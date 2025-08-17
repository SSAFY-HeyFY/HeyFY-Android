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
fun SplashScreen() {
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
        targetValue = if (startMoveUp) -200f else 0f,
        animationSpec = tween(
            durationMillis = 1000,
        ),
        label = "offset_y"
    )

    LaunchedEffect(Unit) {
        delay(1500)
        startAnimation = true
        delay(500)
        startMoveUp = true
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.offset(y = offsetY.dp)
        ) {
            Text(
                text = "Hey",
                fontSize = fontSize.sp,
                fontFamily = FontFamily(Font(commonR.font.pretendard_black)),
                fontWeight = FontWeight(900),
                color = Color(0xFF9784ED).copy(alpha = alpha)
            )

            if (!startAnimation) Text(text = " ")

            Text(
                text = fullTextF.take(visibleCharsF) +
                        shortTextF.drop(visibleCharsF.coerceAtMost(shortTextF.length)),
                fontSize = fontSize.sp,
                fontFamily = FontFamily(Font(commonR.font.pretendard_black)),
                fontWeight = FontWeight(900),
                color = Color(0xFF9784ED).copy(alpha = alpha)
            )

            if (!startAnimation) Text(text = " ") // 공백

            Text(
                text = fullTextY.take(visibleCharsY) +
                        shortTextY.drop(visibleCharsY.coerceAtMost(shortTextY.length)),
                fontSize = fontSize.sp,
                fontFamily = FontFamily(Font(commonR.font.pretendard_black)),
                fontWeight = FontWeight(900),
                color = Color(0xFF9784ED).copy(alpha = alpha)
            )
        }
    }
}

@Preview
@Composable
private fun SplashScreenPreview() {
    SplashScreen()
}
