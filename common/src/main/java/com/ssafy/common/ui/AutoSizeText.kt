package com.ssafy.common.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AutoSizeText(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle,
    color: Color,
    minTextSize: Dp = 12.dp,
) {
    val density = LocalDensity.current
    var textStyle by remember { mutableStateOf(style) }
    var readyToDraw by remember { mutableStateOf(false) }

    Text(
        text = text,
        style = textStyle,
        color = color,
        maxLines = 1,
        overflow = TextOverflow.Clip,
        softWrap = false,
        modifier = modifier.onGloballyPositioned { coordinates ->
            if (!readyToDraw) {
                readyToDraw = true
            }
        },
        onTextLayout = { textLayoutResult ->
            if (textLayoutResult.didOverflowWidth) {
                val currentFontSize = textStyle.fontSize
                val newFontSize = with(density) {
                    (currentFontSize.value * 0.9f).sp
                }

                if (newFontSize.value >= minTextSize.value) {
                    textStyle = textStyle.copy(fontSize = newFontSize)
                }
            }
        }
    )
}
