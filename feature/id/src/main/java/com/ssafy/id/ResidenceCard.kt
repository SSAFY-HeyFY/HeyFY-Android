package com.ssafy.id

import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import coil.size.Size
import com.ssafy.common.theme.HeyFYTheme
import com.ssafy.common.utils.clickableOnce
import com.ssafy.common.R as commonR

@Composable
internal fun ResidenceCard(
    modifier: Modifier = Modifier,
    isBlurred: Boolean = false,
    onCardClick: () -> Unit = {},
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .clickableOnce(isRipple = false) { onCardClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        border = BorderStroke(1.dp, Color(0xFFF3F4F6)),
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {

            val context = LocalContext.current
            val imageLoader = ImageLoader.Builder(context)
                .components {
                    if (SDK_INT >= 28) {
                        add(ImageDecoderDecoder.Factory())
                    } else {
                        add(GifDecoder.Factory())
                    }
                }
                .build()
            Image(
                painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(context)
                        .data(data = commonR.drawable.gif_residence_card_background)
                        .apply(block = {
                            size(Size.ORIGINAL)
                        }).build(),
                    imageLoader = imageLoader
                ),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.FillWidth,
            )

            Box(
                modifier = Modifier
                    .padding(4.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    TitleSection()

                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 12.dp),
                        thickness = 1.dp,
                        color = Color(0xFFF3F4F6),
                    )
                    IdItem(
                        modifier = Modifier.weight(1f),
                        title = "외국인등록번호",
                        englishTitle = "Registration No.",
                        content = "123456-1234567",
                        isBlurred = isBlurred,
                        shouldBlur = true
                    )
                    IdItem(
                        modifier = Modifier.weight(1f),
                        title = "성명",
                        englishTitle = "Name",
                        content = "Nguyen Thi Hoa"
                    )
                    IdItem(
                        modifier = Modifier.weight(1f),
                        title = "국가 / 지역",
                        englishTitle = "Country / Region",
                        content = "VIETNAM"
                    )
                    IdItem(
                        modifier = Modifier.weight(1f),
                        title = "체류 자격",
                        englishTitle = "Status",
                        content = "유학(D-2)",
                        isBlurred = isBlurred,
                        shouldBlur = true
                    )
                }

                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .size(width = 90.dp, height = 110.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.White)
                        .align(Alignment.TopEnd)
                ) {
                    Image(
                        painter = painterResource(commonR.drawable.image_persona),
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    }
}

@Composable
private fun TitleSection(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .offset(x = 70.dp, y = 5.dp)
    ) {
        Text(
            text = "외국인등록증",
            style = HeyFYTheme.typography.labelM.copy(
                fontFamily = FontFamily(Font(commonR.font.pretendard_extra_bold))
            ),
            color = Color(0xFF354299)
        )

        Text(
            text = "RESIDENCE CARD",
            style = HeyFYTheme.typography.bodyS,
            color = Color(0xFF354299)
        )
    }
}


@Composable
private fun IdItem(
    modifier: Modifier = Modifier,
    title: String,
    englishTitle: String,
    content: String,
    isBlurred: Boolean = false,
    shouldBlur: Boolean = false,
) {

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.width(80.dp),
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = title, style = HeyFYTheme.typography.headlineS.copy(
                    fontSize = 11.sp,
                    lineHeight = 15.sp
                ), color = Color.Black
            )
            Text(text = englishTitle, style = HeyFYTheme.typography.bodyS2, color = Color.Black)
        }
        Spacer(Modifier.width(8.dp))

        val displayContent = if (shouldBlur && isBlurred) {
            when (title) {
                "외국인등록번호" -> {
                    val parts = content.split("-")
                    if (parts.size == 2) "${parts[0]}-*******" else content
                }

                "체류 자격" -> "****(D-*)"
                else -> content
            }
        } else {
            content
        }

        Text(
            text = displayContent,
            style = HeyFYTheme.typography.labelM,
            color = Color.Black,
            modifier = if (shouldBlur && isBlurred) {
                Modifier.blur(
                    radius = 4.dp,
                    edgeTreatment = BlurredEdgeTreatment.Unbounded
                )
            } else {
                Modifier
            }
        )
    }
}
