package com.ssafy.finance

import android.text.Layout
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStart
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLine
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.cartesian.rememberVicoScrollState
import com.patrykandpatrick.vico.compose.common.fill
import com.patrykandpatrick.vico.compose.common.shader.verticalGradient
import com.patrykandpatrick.vico.core.cartesian.CartesianDrawingContext
import com.patrykandpatrick.vico.core.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.CartesianLayerRangeProvider
import com.patrykandpatrick.vico.core.cartesian.data.CartesianValueFormatter
import com.patrykandpatrick.vico.core.cartesian.data.lineSeries
import com.patrykandpatrick.vico.core.cartesian.layer.LineCartesianLayer
import com.patrykandpatrick.vico.core.cartesian.marker.CartesianMarker
import com.patrykandpatrick.vico.core.cartesian.marker.DefaultCartesianMarker
import com.patrykandpatrick.vico.core.common.Fill
import com.patrykandpatrick.vico.core.common.Position
import com.patrykandpatrick.vico.core.common.component.LineComponent
import com.patrykandpatrick.vico.core.common.component.TextComponent
import com.patrykandpatrick.vico.core.common.shader.ShaderProvider
import com.ssafy.common.text.TextFormat.formatDateEnglish1
import com.ssafy.common.theme.HeyFYTheme
import com.ssafy.finance.domain.model.ExchangeRateHistories
import timber.log.Timber
import java.text.DecimalFormat

@Composable
internal fun ExchangeRateChartSection(
    modifier: Modifier = Modifier,
    histories: ExchangeRateHistories,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
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
                    text = "USD/KRW Exchange Rate",
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
                        text = "2W",
                        style = HeyFYTheme.typography.bodyS,
                        color = Color.White
                    )
                }
            }

            var index by remember { mutableIntStateOf(0) }

            if (histories.rates.isNotEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                ) {
                    JetpackComposeElectricCarSales(
                        histories = histories.rates,
                        updateIndex = {
                            index = it
                        }
                    )

                    if (index > 0) {
                        Column(
                            modifier = Modifier
                                .offset(x = 40.dp)
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                                .align(Alignment.TopStart)
                        ) {

                            val color = if (histories.rates[index - 1].prediction) {
                                Color(0xFFFF6B6B)
                            } else {
                                Color(0xffa485e0)
                            }

                            Text(
                                text = formatDateEnglish1(histories.rates[index - 1].date),
                                style = HeyFYTheme.typography.labelS,
                                color = color
                            )

                            Text(
                                text = "Rate: ${histories.rates[index - 1].rate}",
                                style = HeyFYTheme.typography.labelS,
                                color = color
                            )
                            val modelName = histories.rates[index - 1].modelName

                            if (modelName.isNotEmpty()) {
                                Text(
                                    text = "Prediction: ${histories.rates[index - 1].modelName}",
                                    style = HeyFYTheme.typography.bodyS,
                                    color = color
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun JetpackComposeElectricCarSales1(
    modifier: Modifier = Modifier,
    modelProducer: CartesianChartModelProducer,
    updateIndex: (Int) -> Unit,
) {

    val lineColor1 = Color(0xffa485e0)
    val lineColor2 = Color(0xFFFF6B6B)

    val rangeProvider = CartesianLayerRangeProvider.fixed(maxY = 1500.0, minY = 1300.0)
    val yDecimalFormat = DecimalFormat("#.##")
    val startAxisValueFormatter = CartesianValueFormatter.decimal(yDecimalFormat)

    val delegateMarker = remember {
        DefaultCartesianMarker(
            label = TextComponent(),
            guideline = LineComponent( Fill(android.graphics.Color.LTGRAY)),
            labelPosition = DefaultCartesianMarker.LabelPosition.BelowPoint,
        )
    }

    val cartesianMarker = object : CartesianMarker {
        override fun drawUnderLayers(
            context: CartesianDrawingContext,
            targets: List<CartesianMarker.Target>,
        ) {
            updateIndex(targets.first().x.toInt())
            delegateMarker.drawUnderLayers(context, targets)
        }

        override fun drawOverLayers(
            context: CartesianDrawingContext,
            targets: List<CartesianMarker.Target>,
        ) {
            delegateMarker.drawOverLayers(context, targets)
        }
    }

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
                            areaFill =
                                LineCartesianLayer.AreaFill.single(
                                    fill(
                                        ShaderProvider.verticalGradient(
                                            arrayOf(
                                                lineColor2.copy(alpha = 0.4f),
                                                Color.Transparent
                                            ),
                                        )
                                    )
                                ),
                        ),

                    ),
                rangeProvider = rangeProvider,
            ),
            startAxis = VerticalAxis.rememberStart(
                valueFormatter = startAxisValueFormatter,
                label = TextComponent(),
                guideline = null,
                tick = null,
                line = null,
                verticalLabelPosition = Position.Vertical.Top
            ),
            marker = cartesianMarker,
        ),
        modelProducer,
        modifier = modifier.height(220.dp),
        scrollState = rememberVicoScrollState(scrollEnabled = false),
    )
}

@Composable
fun JetpackComposeElectricCarSales(
    modifier: Modifier = Modifier,
    updateIndex: (Int) -> Unit,
    histories: List<ExchangeRateHistories.Rate>,
) {
    val modelProducer = remember { CartesianChartModelProducer() }

    val list1 = histories.filter { it.prediction.not() }.map { it.rate }
    val list3 = histories.filter { it.prediction }.map { it.rate }

    val temp = (1..list1.size).toList()
    val temp1 = (list1.size..list1.size + list3.size).toList()

    LaunchedEffect(Unit) {
        modelProducer.runTransaction {
            lineSeries {
                series(temp, list1)
                series(temp1, list3)
            }
        }
    }
    JetpackComposeElectricCarSales1(
        modifier = modifier,
        modelProducer = modelProducer,
        updateIndex = updateIndex,
    )
}
