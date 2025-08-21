package com.ssafy.finance

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottom
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStart
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLine
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.cartesian.rememberVicoScrollState
import com.patrykandpatrick.vico.compose.common.fill
import com.patrykandpatrick.vico.compose.common.shader.verticalGradient
import com.patrykandpatrick.vico.core.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.core.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.CartesianLayerRangeProvider
import com.patrykandpatrick.vico.core.cartesian.data.CartesianValueFormatter
import com.patrykandpatrick.vico.core.cartesian.data.lineSeries
import com.patrykandpatrick.vico.core.cartesian.layer.LineCartesianLayer
import com.patrykandpatrick.vico.core.cartesian.marker.DefaultCartesianMarker
import com.patrykandpatrick.vico.core.common.Fill
import com.patrykandpatrick.vico.core.common.Position
import com.patrykandpatrick.vico.core.common.component.LineComponent
import com.patrykandpatrick.vico.core.common.component.TextComponent
import com.patrykandpatrick.vico.core.common.shader.ShaderProvider
import com.ssafy.common.theme.HeyFYTheme
import java.text.DecimalFormat

@Composable
internal fun ExchangeRateChartSection() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "USD Exchange Rate",
                    style = HeyFYTheme.typography.headlineM,
                    color = Color(0xFF111827)
                )

                Box(
                    modifier = Modifier
                        .background(Color(0xFF9333EA), CircleShape)
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    contentAlignment = Alignment.Center

                ) {
                    Text(
                        text = "1M",
                        style = HeyFYTheme.typography.bodyS,
                        color = Color.White
                    )
                }
            }

            JetpackComposeElectricCarSales(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            )
        }
    }
}

private val RangeProvider = CartesianLayerRangeProvider.fixed(maxY = 1400.0, minY = 1300.0)
private val x = (1..14).toList() // 날짜
private val y = listOf<Number>(
    1350.48,
    1351.40,
    1353.51,
    1360.21,
    1358.44,
    1356.81,
    1357.99,
    1361.56,
    1363.55,
    1368.25,
    1371.54,
    1373.33,
    1375.11,
    1374.33,
) // 환율

private val x2 = (14..17).toList()
private val y2 = listOf(1374.33, 1375.11, 1376.33, 1378.31)

private val x3 = (14..17).toList()
private val y3 = listOf(1374.33, 1373.11, 1372.33, 1373.31)

private val x4 = (14..17).toList()
private val y4 = listOf(1374.33, 1374.11, 1374.33, 1374.31)
private val YDecimalFormat = DecimalFormat("#.##")
private val AIDecimalFormat = DecimalFormat("환율정보: #.##")
private val StartAxisValueFormatter = CartesianValueFormatter.decimal(YDecimalFormat)

private val MarkerValueFormatter = DefaultCartesianMarker.ValueFormatter.default(YDecimalFormat)

@Composable
private fun JetpackComposeElectricCarSales1(
    modelProducer: CartesianChartModelProducer,
    modifier: Modifier = Modifier,
) {

    val lineColor1 = Color(0xffa485e0)
    val lineColor2 = Color(0xFFFF6B6B)
    val lineColor3 = Color(0xFF4ECDC4)
    val lineColor4 = Color(0xFFFFE66D)

    CartesianChartHost(
        rememberCartesianChart(
            rememberLineCartesianLayer(
                lineProvider =
                    LineCartesianLayer.LineProvider.series(
                        LineCartesianLayer.rememberLine(
                            fill = LineCartesianLayer.LineFill.single(fill(lineColor1)),
                            areaFill =
                                LineCartesianLayer.AreaFill.single(
                                    fill(
                                        ShaderProvider.verticalGradient(
                                            arrayOf(
                                                lineColor1.copy(alpha = 0.4f),
                                                Color.Transparent
                                            ),
                                        )
                                    )
                                ),
                        ),

                        LineCartesianLayer.rememberLine(
                            fill = LineCartesianLayer.LineFill.single(fill(lineColor2)),
                        ),

                        LineCartesianLayer.rememberLine(
                            fill = LineCartesianLayer.LineFill.single(fill(lineColor3)),
                        ),

                        LineCartesianLayer.rememberLine(
                            fill = LineCartesianLayer.LineFill.single(fill(lineColor4)),
                        )
                    ),
                rangeProvider = RangeProvider,
            ),
            startAxis = VerticalAxis.rememberStart(
                valueFormatter = StartAxisValueFormatter,
                label = TextComponent(),
                guideline = null,
                tick = null,
                line = null,
                verticalLabelPosition = Position.Vertical.Top
            ),
            bottomAxis = HorizontalAxis.rememberBottom(
                label = TextComponent(),
                guideline = null,
                tick = null,
                line = null,
                itemPlacer = remember { HorizontalAxis.ItemPlacer.aligned(spacing = { 4 }) },
            ),
            marker = DefaultCartesianMarker(
                label = TextComponent(
                    lineCount = 1,
                ),
                valueFormatter = MarkerValueFormatter,
                guideline = LineComponent(
                    Fill.Black
                ),
                labelPosition = DefaultCartesianMarker.LabelPosition.Top,
            ),
        ),
        modelProducer,
        modifier = modifier.height(220.dp),
        scrollState = rememberVicoScrollState(scrollEnabled = false),
    )
}

@Composable
fun JetpackComposeElectricCarSales(modifier: Modifier = Modifier) {
    val modelProducer = remember { CartesianChartModelProducer() }
    LaunchedEffect(Unit) {
        modelProducer.runTransaction {
            lineSeries {
                series(x, y)
                series(x2, y2)
                series(x3, y3)
                series(x4, y4)
            }
        }
    }
    JetpackComposeElectricCarSales1(modelProducer, modifier)
}
