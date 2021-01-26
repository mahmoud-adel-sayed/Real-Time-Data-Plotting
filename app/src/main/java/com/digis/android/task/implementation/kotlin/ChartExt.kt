package com.digis.android.task.implementation.kotlin

import android.animation.ObjectAnimator
import android.graphics.Color
import android.view.animation.DecelerateInterpolator
import android.widget.ProgressBar
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.formatter.ValueFormatter

fun LineChart.setupChart() {
    description.isEnabled = false
    // Set background color & Bottom offset
    setBackgroundColor(Color.WHITE)
    extraBottomOffset = 16f
    // Enable touch gestures & pinch zooming
    setTouchEnabled(true)
    setPinchZoom(true)
    dragDecelerationFrictionCoef = 0.9f
    // Enable scaling and dragging
    isDragEnabled = true
    setScaleEnabled(true)
    setDrawGridBackground(false)
    isHighlightPerDragEnabled = true
}

fun LineChart.setupXAxis() = with(xAxis) {
    position = XAxis.XAxisPosition.BOTTOM
    textSize = 10f
    textColor = Color.BLACK
    setDrawAxisLine(true)
    setDrawGridLines(false)
    setCenterAxisLabels(false)
    granularity = 5f
    valueFormatter = object : ValueFormatter() {
        override fun getFormattedValue(value: Float): String {
            var remaining = value.toInt()

            val hours = remaining / 3600
            remaining -= hours * 3600

            val minutes = remaining / 60
            remaining -= minutes * 60

            val seconds = remaining

            return "${hours.getDigits()}:${minutes.getDigits()}:${seconds.getDigits()}"
        }
    }
}

fun LineChart.setupYAxis(min: Float, max: Float) {
    axisLeft.apply {
        setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)
        textSize = 10f
        textColor = Color.BLACK
        setDrawAxisLine(true)
        setDrawGridLines(true)
        isGranularityEnabled = true
        axisMinimum = min
        axisMaximum = max
    }
    axisRight.isEnabled = false
}

fun Int.getDigits(): String = if (this < 10) "0$this" else toString()

fun ProgressBar.animate(duration: Long) {
    ObjectAnimator.ofInt(this, "progress", 0, 100).run {
        this.duration = duration
        interpolator = DecelerateInterpolator()
        start()
    }
}