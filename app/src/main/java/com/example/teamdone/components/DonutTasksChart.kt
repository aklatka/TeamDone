package com.example.teamdone.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.yml.charts.common.model.PlotType
import co.yml.charts.ui.piechart.charts.DonutPieChart
import co.yml.charts.ui.piechart.models.PieChartConfig
import co.yml.charts.ui.piechart.models.PieChartData

@Composable
fun DonutTaskChart(
    slices: List<PieChartData.Slice>
) {
    val chartData = PieChartData(
        slices = slices,
        plotType = PlotType.Donut
    )
    val chartConfig = PieChartConfig(
        strokeWidth = 120f,
        activeSliceAlpha = .9f,
        isAnimationEnable = true,
        labelColor = Color.Black,
        labelVisible = true,
        showSliceLabels = true,
        labelFontSize = 30.sp,
        chartPadding = 40,
    )

    DonutPieChart(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        chartData,
        chartConfig
    )
}