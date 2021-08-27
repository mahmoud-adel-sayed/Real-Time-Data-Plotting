package com.example.android.rtdp.implementation.java;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.rtdp.R;
import com.example.android.rtdp.implementation.java.data.model.NetInfo;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;

import javax.inject.Inject;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

@SuppressLint("NonConstantResourceId")
public class MainActivity extends AppCompatActivity implements
        HasSupportFragmentInjector {
    private static final int WAIT_DURATION = 2000; // In millis
    private static final int ANIMATION_DURATION = 500; // In millis
    private static final int MAX_VISIBLE_X_RANGE = 20;

    private static final int BLUE_DATA_SET_INDEX = 0;
    private static final int RED_DATA_SET_INDEX = 1;
    private static final int GREEN_DATA_SET_INDEX = 2;

    private static final String LABEL_RSRP_P = "RSRP P";
    private static final String LABEL_RSRP_S1 = "RSRP S1";
    private static final String LABEL_RSRP_S2 = "RSRP S2";

    private static final String LABEL_RSRQ_P = "RSRQ P";
    private static final String LABEL_RSRQ_S1 = "RSRQ S1";
    private static final String LABEL_RSRQ_S2 = "RSRQ S2";

    private static final String LABEL_SINR_P = "SINR P";
    private static final String LABEL_SINR_S1 = "SINR S1";
    private static final String LABEL_SINR_S2 = "SINR S2";

    @Inject
    ViewModelProvider.Factory mViewModelFactory;

    @Inject
    DispatchingAndroidInjector<Fragment> mDispatchingAndroidInjector;

    @BindView(R.id.root)
    View mRoot;

    @BindView(R.id.chart_RSRP)
    LineChart mChartRSRP;

    @BindView(R.id.chart_RSRQ)
    LineChart mChartRSRQ;

    @BindView(R.id.chart_SINR)
    LineChart mChartSINR;

    @BindView(R.id.progress_RSRP)
    ProgressBar mProgressRSRP;

    @BindView(R.id.progress_RSRQ)
    ProgressBar mProgressRSRQ;

    @BindView(R.id.progress_SINR)
    ProgressBar mProgressSINR;

    @BindView(R.id.current_RSRP_value)
    TextView mCurrentRSRP;

    @BindView(R.id.current_RSRQ_value)
    TextView mCurrentRSRQ;

    @BindView(R.id.current_SINR_value)
    TextView mCurrentSINR;

    private LineData mLineDataRSRP, mLineDataRSRQ, mLineDataSINR;

    private MainViewModel mViewModel;
    private Handler mHandler;
    private Runnable mRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(getString(R.string.title_main_screen));
        ButterKnife.bind(this);

        setupChartRSRP();
        setupChartRSRQ();
        setupChartSINR();

        mHandler = new Handler();
        mRunnable = this::getNextEntries;

        mViewModel = new ViewModelProvider(this, mViewModelFactory).get(MainViewModel.class);
        observeData();
        mViewModel.getNetInfo();
    }

    private void observeData() {
        mViewModel.getNetInfoObservable().observe(this, response -> {
            if (response.isSuccessful()) {
                NetInfo netInfo = response.getData();
                addEntriesToCharts(netInfo);
                setTableData(netInfo);
                scheduleNextFetch();
            } else {
                showError(response.getMessage());
            }
        });
    }

    private void scheduleNextFetch() {
        mHandler.postDelayed(mRunnable, WAIT_DURATION);
    }

    private void getNextEntries() {
        mViewModel.getNetInfo();
    }

    private void showError(@Nullable String message) {
        showSnackBar(message, __ -> mViewModel.getNetInfo());
    }

    private void showSnackBar(String message, View.OnClickListener listener) {
        Snackbar snackbar = Snackbar.make(mRoot, message, Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction(getString(R.string.retry), listener);
        snackbar.show();
        View view = snackbar.getView();
        TextView tv = view.findViewById(com.google.android.material.R.id.snackbar_text);
        if (tv != null) {
            tv.setGravity(Gravity.CENTER_HORIZONTAL);
        }
    }

    private void setupChartRSRP() {
        setupChart(mChartRSRP);
        setupXAxis(mChartRSRP);
        setupYAxis(mChartRSRP, ChartType.RSRP.getMin(), ChartType.RSRP.getMax());
        mLineDataRSRP = new LineData(
                createLineDataSet(LABEL_RSRP_P, Color.BLUE),
                createLineDataSet(LABEL_RSRP_S1, Color.RED),
                createLineDataSet(LABEL_RSRP_S2, Color.GREEN)
        );
        mChartRSRP.setData(mLineDataRSRP);
        mChartRSRP.invalidate();
        mChartRSRP.getLegend().setEnabled(true);
    }

    private void setupChartRSRQ() {
        setupChart(mChartRSRQ);
        setupXAxis(mChartRSRQ);
        setupYAxis(mChartRSRQ, ChartType.RSRQ.getMin(), ChartType.RSRQ.getMax());
        mLineDataRSRQ = new LineData(
                createLineDataSet(LABEL_RSRQ_P, Color.BLUE),
                createLineDataSet(LABEL_RSRQ_S1, Color.RED),
                createLineDataSet(LABEL_RSRQ_S2, Color.GREEN)
        );
        mChartRSRQ.setData(mLineDataRSRQ);
        mChartRSRQ.invalidate();
        mChartRSRQ.getLegend().setEnabled(true);
    }

    private void setupChartSINR() {
        setupChart(mChartSINR);
        setupXAxis(mChartSINR);
        setupYAxis(mChartSINR, ChartType.SINR.getMin(), ChartType.SINR.getMax());
        mLineDataSINR = new LineData(
                createLineDataSet(LABEL_SINR_P, Color.BLUE),
                createLineDataSet(LABEL_SINR_S1, Color.RED),
                createLineDataSet(LABEL_SINR_S2, Color.GREEN)
        );
        mChartSINR.setData(mLineDataSINR);
        mChartSINR.invalidate();
        mChartSINR.getLegend().setEnabled(true);
    }

    private static void setupChart(LineChart chart) {
        chart.getDescription().setEnabled(false);
        chart.setBackgroundColor(Color.WHITE);
        chart.setExtraBottomOffset(16f);
        chart.setTouchEnabled(true);
        chart.setPinchZoom(true);
        chart.setDragDecelerationFrictionCoef(0.9f);
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);
        chart.setDrawGridBackground(false);
        chart.setHighlightPerDragEnabled(true);
    }

    private static void setupXAxis(LineChart chart) {
        XAxis axis = chart.getXAxis();
        axis.setPosition(XAxis.XAxisPosition.BOTTOM);
        axis.setTextSize(10f);
        axis.setTextColor(Color.BLACK);
        axis.setDrawAxisLine(true);
        axis.setDrawGridLines(false);
        axis.setCenterAxisLabels(false);
        axis.setGranularity(5f);
        axis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                int remaining = (int)value;

                int hours = remaining / 3600;
                remaining -= hours * 3600;

                int minutes = remaining / 60;
                remaining -= minutes * 60;

                int seconds = remaining;

                return getDigits(hours) + ":" + getDigits(minutes) + ":" + getDigits(seconds);
            }
        });
    }

    private static void setupYAxis(LineChart chart, float min, float max) {
        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setTextSize(10f);
        leftAxis.setTextColor(Color.BLACK);
        leftAxis.setDrawAxisLine(true);
        leftAxis.setDrawGridLines(true);
        leftAxis.setGranularityEnabled(true);
        leftAxis.setAxisMinimum(min);
        leftAxis.setAxisMaximum(max);

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setEnabled(false);
    }

    @NonNull
    private static Entry getEntry(float y) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);

        float x = (hour * 3600) + (minute * 60) + second;
        return new Entry(x, y);
    }

    @NonNull
    private static LineDataSet createLineDataSet(@Nullable String label, @ColorInt int color) {
        LineDataSet set = new LineDataSet(null, label);
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setColor(color);
        set.setHighLightColor(Color.BLACK);
        set.setLineWidth(1.5f);
        set.setDrawCircles(true);
        set.setCircleColor(color);
        set.setDrawValues(false);
        set.setDrawCircleHole(false);
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        return set;
    }

    @NonNull
    private static String getDigits(int number) {
        return (number < 10) ? "0" + number : String.valueOf(number);
    }

    private void addEntriesToCharts(@NonNull NetInfo netInfo) {
        addEntriesToChartRSRP(netInfo);
        addEntriesToChartRSRQ(netInfo);
        addEntriesToChartSINR(netInfo);
    }

    private void addEntriesToChartRSRP(NetInfo netInfo) {
        int value = netInfo.getRSRP();
        Entry mainEntry = getEntry(value);
        mLineDataRSRP.addEntry(mainEntry, BLUE_DATA_SET_INDEX);
        mLineDataRSRP.addEntry(getEntry(ChartType.RSRP.scale(value)), RED_DATA_SET_INDEX);
        mLineDataRSRP.addEntry(getEntry(ChartType.RSRP.scale(value)), GREEN_DATA_SET_INDEX);
        mLineDataRSRP.notifyDataChanged();

        mChartRSRP.notifyDataSetChanged();
        mChartRSRP.setVisibleXRangeMaximum(MAX_VISIBLE_X_RANGE);
        mChartRSRP.moveViewToX(mainEntry.getX());
    }

    private void addEntriesToChartRSRQ(NetInfo netInfo) {
        int value = netInfo.getRSRQ();
        Entry mainEntry = getEntry(value);
        mLineDataRSRQ.addEntry(mainEntry, BLUE_DATA_SET_INDEX);
        mLineDataRSRQ.addEntry(getEntry(ChartType.RSRQ.scale(value)), RED_DATA_SET_INDEX);
        mLineDataRSRQ.addEntry(getEntry(ChartType.RSRQ.scale(value)), GREEN_DATA_SET_INDEX);
        mLineDataRSRQ.notifyDataChanged();

        mChartRSRQ.notifyDataSetChanged();
        mChartRSRQ.setVisibleXRangeMaximum(MAX_VISIBLE_X_RANGE);
        mChartRSRQ.moveViewToX(mainEntry.getX());
    }

    private void addEntriesToChartSINR(NetInfo netInfo) {
        int value = netInfo.getSINR();
        Entry mainEntry = getEntry(value);
        mLineDataSINR.addEntry(mainEntry, BLUE_DATA_SET_INDEX);
        mLineDataSINR.addEntry(getEntry(ChartType.SINR.scale(value)), RED_DATA_SET_INDEX);
        mLineDataSINR.addEntry(getEntry(ChartType.SINR.scale(value)), GREEN_DATA_SET_INDEX);
        mLineDataSINR.notifyDataChanged();

        mChartSINR.notifyDataSetChanged();
        mChartSINR.setVisibleXRangeMaximum(MAX_VISIBLE_X_RANGE);
        mChartSINR.moveViewToX(mainEntry.getX());
    }

    private void setTableData(@NonNull NetInfo netInfo) {
        mProgressRSRP.setProgressDrawable(ContextCompat.getDrawable(
                this, ChartType.RSRP.getProgressDrawable(netInfo.getRSRP())
        ));
        animateProgressBar(mProgressRSRP);
        mCurrentRSRP.setText(String.valueOf(netInfo.getRSRP()));

        mProgressRSRQ.setProgressDrawable(ContextCompat.getDrawable(
                this, ChartType.RSRQ.getProgressDrawable(netInfo.getRSRQ())
        ));
        animateProgressBar(mProgressRSRQ);
        mCurrentRSRQ.setText(String.valueOf(netInfo.getRSRQ()));

        mProgressSINR.setProgressDrawable(ContextCompat.getDrawable(
                this, ChartType.SINR.getProgressDrawable(netInfo.getSINR())
        ));
        animateProgressBar(mProgressSINR);
        mCurrentSINR.setText(String.valueOf(netInfo.getSINR()));
    }

    private static void animateProgressBar(ProgressBar pb) {
        ObjectAnimator animation = ObjectAnimator.ofInt(pb, "progress", 0, 100);
        animation.setDuration(ANIMATION_DURATION);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.start();
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return mDispatchingAndroidInjector;
    }
}