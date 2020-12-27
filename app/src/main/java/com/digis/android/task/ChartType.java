package com.digis.android.task;

import java.util.Random;

import androidx.annotation.DrawableRes;

public enum ChartType {
    RSRP(-140f, -60f, 0.3f) {
        @Override
        int getProgressDrawable(int value) {
            if (value >= -80)
                return EXCELLENT_SIGNAL;
            else if (value >= -90)
                return GOOD_SIGNAL;
            else if (value > -100)
                return FAIR_SIGNAL;
            else
                return POOR_SIGNAL;
        }
    },
    RSRQ(-30f, 0f, 0.5f) {
        @Override
        int getProgressDrawable(int value) {
            if (value >= -10)
                return EXCELLENT_SIGNAL;
            else if (value >= -15)
                return GOOD_SIGNAL;
            else if (value > -20)
                return FAIR_SIGNAL;
            else
                return POOR_SIGNAL;
        }
    },
    SINR(-10f, 30f, 0.8f) {
        @Override
        int getProgressDrawable(int value) {
            if (value >= 20)
                return EXCELLENT_SIGNAL;
            else if (value >= 13)
                return GOOD_SIGNAL;
            else if (value > 0)
                return FAIR_SIGNAL;
            else
                return POOR_SIGNAL;
        }
    };

    private static final Random RANDOM = new Random();

    @DrawableRes
    private static final int EXCELLENT_SIGNAL = R.drawable.green_gradient_progress;

    @DrawableRes
    private static final int GOOD_SIGNAL = R.drawable.yellow_gradient_progress;

    @DrawableRes
    private static final int FAIR_SIGNAL = R.drawable.orange_gradient_progress;

    @DrawableRes
    private static final int POOR_SIGNAL = R.drawable.red_gradient_progress;

    private final float min;
    private final float max;
    private final float scalar;

    ChartType(float min, float max, float scalar) {
        this.min = min;
        this.max = max;
        this.scalar = scalar;
    }

    public float getMin() {
        return min;
    }

    public float getMax() {
        return max;
    }

    public int scale(int value) {
        float random;
        float fraction = RANDOM.nextFloat() * scalar * value;
        if (RANDOM.nextBoolean())
            random = value + fraction;
        else
            random = value - fraction;
        // Ensuring the random value is between the specified range
        if (random < min)
            random = min;
        else if (random > max)
            random = max;

        return (int)random;
    }

    @DrawableRes
    abstract int getProgressDrawable(int value);
}
