package com.example.android.rtdp.implementation.kotlin

import androidx.annotation.DrawableRes
import com.example.android.rtdp.R
import java.util.*

private val RANDOM: Random = Random()

@DrawableRes
private const val EXCELLENT_SIGNAL = R.drawable.green_gradient_progress

@DrawableRes
private const val GOOD_SIGNAL = R.drawable.yellow_gradient_progress

@DrawableRes
private const val FAIR_SIGNAL = R.drawable.orange_gradient_progress

@DrawableRes
private const val POOR_SIGNAL = R.drawable.red_gradient_progress

enum class ChartType(val min: Float, val max: Float, private val scalar: Float) {
    RSRP(-140f, -60f, 0.3f) {
        @DrawableRes
        override fun getProgressDrawable(value: Int): Int = when {
            value >= -80 -> EXCELLENT_SIGNAL
            value >= -90 -> GOOD_SIGNAL
            value > -100 -> FAIR_SIGNAL
            else -> POOR_SIGNAL
        }
    },
    RSRQ(-30f, 0f, 0.5f) {
        @DrawableRes
        override fun getProgressDrawable(value: Int): Int = when {
            value >= -10 -> EXCELLENT_SIGNAL
            value >= -15 -> GOOD_SIGNAL
            value > -20 -> FAIR_SIGNAL
            else -> POOR_SIGNAL
        }

    },
    SINR(-10f, 30f, 0.8f) {
        @DrawableRes
        override fun getProgressDrawable(value: Int): Int = when {
            value >= 20 -> EXCELLENT_SIGNAL
            value >= 13 -> GOOD_SIGNAL
            value > 0 -> FAIR_SIGNAL
            else -> POOR_SIGNAL
        }
    };

    fun scale(value: Int): Int {
        val fraction = RANDOM.nextFloat() * scalar * value
        val random = if (RANDOM.nextBoolean()) value + fraction else value - fraction
        // Ensuring the random value is between the specified range
        return random.coerceIn(min, max).toInt()
    }

    @DrawableRes
    abstract fun getProgressDrawable(value: Int): Int
}