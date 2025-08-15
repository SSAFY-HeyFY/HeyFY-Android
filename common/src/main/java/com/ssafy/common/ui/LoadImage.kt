package com.ssafy.common.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.request.ImageRequest
import coil.size.Size

@Composable
fun LoadImage(
    modifier: Modifier = Modifier,
    url: String,
) {
    val context = LocalContext.current

    AsyncImage(
        modifier = modifier,
        model = ImageRequest.Builder(context)
            .data(url)
            .size(Size.ORIGINAL)
            .decoderFactory(GifDecoder.Factory())
            .build(),
        contentScale = ContentScale.FillBounds,
        contentDescription = null
    )
}
