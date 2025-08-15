package com.ssafy.common.theme

import androidx.annotation.Keep
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.ssafy.common.R as commonR

internal val pretendardBold = FontFamily(Font(commonR.font.pretendard_bold))
internal val pretendardSemiBold = FontFamily(Font(commonR.font.pretendard_semi_bold))
internal val pretendardRegular = FontFamily(Font(commonR.font.pretendard_regular))

internal object HeyFYTypography {
    val DisplayL = TextStyle(
        fontFamily = pretendardBold,
        fontSize = 28.sp,
        letterSpacing = (-0.02).em,
        lineHeight = 40.sp,
        lineHeightStyle = DefaultLineHeightStyle
    )

    val DisplayM = TextStyle(
        fontFamily = pretendardBold,
        fontSize = 24.sp,
        letterSpacing = (-0.02).em,
        lineHeight = 34.sp,
        lineHeightStyle = DefaultLineHeightStyle
    )

    val HeadlineL = TextStyle(
        fontFamily = pretendardBold,
        fontSize = 20.sp,
        letterSpacing = (-0.02).em,
        lineHeight = 28.sp,
        lineHeightStyle = DefaultLineHeightStyle
    )

    val HeadlineM = TextStyle(
        fontFamily = pretendardBold,
        fontSize = 18.sp,
        letterSpacing = (-0.02).em,
        lineHeight = 25.sp,
        lineHeightStyle = DefaultLineHeightStyle
    )
    val HeadlineS = TextStyle(
        fontFamily = pretendardBold,
        fontSize = 16.sp,
        letterSpacing = (-0.02).em,
        lineHeight = 22.sp,
        lineHeightStyle = DefaultLineHeightStyle
    )

    val LabelL = TextStyle(
        fontFamily = pretendardSemiBold,
        fontSize = 16.sp,
        letterSpacing = (-0.02).em,
        lineHeight = 22.sp,
        lineHeightStyle = DefaultLineHeightStyle,
    )

    val LabelM = TextStyle(
        fontFamily = pretendardSemiBold,
        fontSize = 14.sp,
        letterSpacing = (-0.02).em,
        lineHeight = 20.sp,
        lineHeightStyle = DefaultLineHeightStyle
    )

    val LabelS = TextStyle(
        fontFamily = pretendardSemiBold,
        fontSize = 11.sp,
        letterSpacing = (-0.02).em,
        lineHeight = 15.sp,
        lineHeightStyle = DefaultLineHeightStyle
    )

    val BodyL = TextStyle(
        fontFamily = pretendardRegular,
        fontSize = 16.sp,
        letterSpacing = (-0.02).em,
        lineHeight = 22.sp,
        lineHeightStyle = DefaultLineHeightStyle
    )

    val BodyM = TextStyle(
        fontFamily = pretendardRegular,
        fontSize = 14.sp,
        letterSpacing = (-0.02).em,
        lineHeight = 20.sp,
        lineHeightStyle = DefaultLineHeightStyle
    )

    val BodyS = TextStyle(
        fontFamily = pretendardRegular,
        fontSize = 12.sp,
        letterSpacing = (-0.02).em,
        lineHeight = 17.sp,
        lineHeightStyle = DefaultLineHeightStyle
    )

    val BodyS2 = TextStyle(
        fontFamily = pretendardRegular,
        fontSize = 11.sp,
        letterSpacing = (-0.02).em,
        lineHeight = 15.sp,
        lineHeightStyle = DefaultLineHeightStyle
    )
}

internal val DefaultLineHeightStyle = LineHeightStyle(
    alignment = LineHeightStyle.Alignment.Center,
    trim = LineHeightStyle.Trim.None
)

@Immutable
@Keep
data class HeyFYTypographys(
    val displayL: TextStyle,
    val displayM: TextStyle,
    val headlineL: TextStyle,
    val headlineM: TextStyle,
    val headlineS: TextStyle,
    val labelL: TextStyle,
    val labelM: TextStyle,
    val labelS: TextStyle,
    val bodyL: TextStyle,
    val bodyM: TextStyle,
    val bodyS: TextStyle,
    val bodyS2: TextStyle,
)

fun HeyFYTypography(
    displayL: TextStyle = HeyFYTypography.DisplayL,
    displayM: TextStyle = HeyFYTypography.DisplayM,
    headlineL: TextStyle = HeyFYTypography.HeadlineL,
    headlineM: TextStyle = HeyFYTypography.HeadlineM,
    headlineS: TextStyle = HeyFYTypography.HeadlineS,
    labelL: TextStyle = HeyFYTypography.LabelL,
    labelM: TextStyle = HeyFYTypography.LabelM,
    labelS: TextStyle = HeyFYTypography.LabelS,
    bodyL: TextStyle = HeyFYTypography.BodyL,
    bodyM: TextStyle = HeyFYTypography.BodyM,
    bodyS: TextStyle = HeyFYTypography.BodyS,
    bodyS2: TextStyle = HeyFYTypography.BodyS2,
): HeyFYTypographys = HeyFYTypographys(
    displayL = displayL,
    displayM = displayM,
    headlineL = headlineL,
    headlineM = headlineM,
    headlineS = headlineS,
    labelL = labelL,
    labelM = labelM,
    labelS = labelS,
    bodyL = bodyL,
    bodyM = bodyM,
    bodyS = bodyS,
    bodyS2 = bodyS2
)

internal val LocalHeyFYTypography = staticCompositionLocalOf { HeyFYTypography() }

@Composable
@Preview(
    apiLevel = 33,
    showBackground = true,
    backgroundColor = 0xFFFFFF,
    device = "spec:width=4160px,height=5340px"
)
private fun HeyFYTypographyPreview() {
    val typo = listOf(
        HeyFYTheme.typography.displayL,
        HeyFYTheme.typography.displayM,
        HeyFYTheme.typography.headlineL,
        HeyFYTheme.typography.headlineM,
        HeyFYTheme.typography.headlineS,
        HeyFYTheme.typography.labelL,
        HeyFYTheme.typography.labelM,
        HeyFYTheme.typography.labelS,
        HeyFYTheme.typography.bodyL,
        HeyFYTheme.typography.bodyM,
        HeyFYTheme.typography.bodyS,
        HeyFYTheme.typography.bodyS2,
    )

    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .padding(10.dp, 10.dp)
    ) {
        TypographySingleLinePreview(typo)
        Spacer(modifier = Modifier.height(10.dp))
        TypographyMultiLinePreview(typo)
    }
}

@Composable
private fun TypographySingleLinePreview(typo: List<TextStyle>) {
    Column {
        typo.forEach {
            Text(
                text = "Hello HeyFy",
                style = it
            )
        }
    }
}


@Composable
private fun TypographyMultiLinePreview(typo: List<TextStyle>) {
    Column {
        typo.forEach {
            Text(
                text = "Hello HeyFy",
                style = it
            )
        }
    }
}
