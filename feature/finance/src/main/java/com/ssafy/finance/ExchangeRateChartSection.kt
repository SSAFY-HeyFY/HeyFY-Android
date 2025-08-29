package com.ssafy.finance

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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import java.text.DecimalFormat
import kotlin.math.min

@Composable
internal fun ExchangeRateChartSection(
    modifier: Modifier = Modifier,
    histories: ExchangeRateHistories,
    index: Int,
    updateIndex: (Int) -> Unit,
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
                        .background(Color(0xFFC78DEB), RoundedCornerShape(6.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    contentAlignment = Alignment.Center

                ) {
                    Text(
                        text = "1M",
                        style = HeyFYTheme.typography.bodyS.copy(

                        ),
                        color = Color.White
                    )
                }
            }

            val historiesRates = histories.rates.filter { it.prediction.not() }.map { it.rate }
            val oneDayPrediction =
                histories.rates.filter { it.prediction && it.modelName.contains("1D") }
                    .map { it.rate }
            val fiveDayPrediction =
                histories.rates.filter { it.prediction && it.modelName.contains("5D") }
                    .map { it.rate }

            if (histories.rates.isNotEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                ) {
                    JetpackComposeElectricCarSales(
                        historiesRates = historiesRates,
                        oneDayPrediction = oneDayPrediction,
                        fiveDayPrediction = fiveDayPrediction,
                        updateIndex = updateIndex
                    )

                    if (index > 0) {
                        Column(
                            modifier = Modifier
                                .offset(x = 40.dp)
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                                .align(Alignment.TopStart)
                        ) {
                            val (idx, color) = when {
                                historiesRates.size >= index -> {
                                    index - 1 to Color(0xffa485e0)
                                }

                                historiesRates.size + oneDayPrediction.lastIndex >= index -> {
                                    index to Color(0xFFFFC107)
                                }

                                else -> {
                                    index + 1 to Color(0xFFFF6B6B)
                                }
                            }

                            Text(
                                text = formatDateEnglish1(histories.rates[idx].date),
                                style = HeyFYTheme.typography.labelS,
                                color = color
                            )

                            Text(
                                text = "Rate: ${histories.rates[idx].rate}",
                                style = HeyFYTheme.typography.labelS,
                                color = color
                            )
                            val modelName = histories.rates[idx].modelName

                            if (modelName.isNotEmpty()) {
                                Text(
                                    text = "Prediction: ${histories.rates[idx].modelName}",
                                    style = HeyFYTheme.typography.labelS,
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
    maxValue: Double,
    minValue: Double,
    updateIndex: (Int) -> Unit,
) {

    val lineColor1 = Color(0xffa485e0)
    val lineColor2 = Color(0xFFFFC107)
    val lineColor3 = Color(0xFFFF6B6B)

    val rangeProvider = CartesianLayerRangeProvider.fixed(maxY = maxValue, minY = minValue)
    val yDecimalFormat = DecimalFormat("####")
    val startAxisValueFormatter = CartesianValueFormatter.decimal(yDecimalFormat)
    val delegateMarker = remember {
        DefaultCartesianMarker(
            label = TextComponent(),
            guideline = LineComponent(Fill(android.graphics.Color.LTGRAY)),
            labelPosition = DefaultCartesianMarker.LabelPosition.AbovePoint,
        )
    }

    val cartesianMarker = object : CartesianMarker {
        override fun drawOverLayers(
            context: CartesianDrawingContext,
            targets: List<CartesianMarker.Target>,
        ) {
            updateIndex(targets.first().x.toInt())
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

                        LineCartesianLayer.rememberLine(
                            fill = LineCartesianLayer.LineFill.single(fill(lineColor3)),
                            areaFill =
                                LineCartesianLayer.AreaFill.single(
                                    fill(
                                        ShaderProvider.verticalGradient(
                                            arrayOf(
                                                lineColor3.copy(alpha = 0.4f),
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
                verticalLabelPosition = Position.Vertical.Top,
                itemPlacer = VerticalAxis.ItemPlacer.count(
                    count = { 3 }
                ),
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
    historiesRates: List<Double>,
    oneDayPrediction: List<Double>,
    fiveDayPrediction: List<Double>,
) {
    val modelProducer = remember { CartesianChartModelProducer() }

    val historiesRange = (1..historiesRates.size).toList()
    val oneDayPredictionRange =
        (historiesRates.size..historiesRates.size + oneDayPrediction.size - 1).toList()
    val fiveDayPredictionRange =
        (historiesRates.size + oneDayPrediction.size - 1..historiesRates.size + oneDayPrediction.size + fiveDayPrediction.size).toList()

    val maxValue = ((historiesRates.max().toInt()/10) *10) + 20.0
    val minValue = ((historiesRates.min().toInt()/10) *10) - 40.0

    LaunchedEffect(Unit) {
        modelProducer.runTransaction {
            lineSeries {
                series(historiesRange, historiesRates)
                series(oneDayPredictionRange, oneDayPrediction)
                series(fiveDayPredictionRange, fiveDayPrediction)
            }
        }
    }
    JetpackComposeElectricCarSales1(
        modifier = modifier,
        modelProducer = modelProducer,
        maxValue = maxValue,
        minValue = minValue,
        updateIndex = updateIndex,
    )
}
