package com.example.android.rtdp.implementation.kotlin

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import butterknife.BindView
import butterknife.ButterKnife
import com.example.android.rtdp.R
import com.example.android.rtdp.implementation.kotlin.data.model.NetInfo
import com.example.android.rtdp.implementation.kotlin.data.source.remote.Response
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.google.android.material.snackbar.Snackbar
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import java.util.*
import javax.inject.Inject

@SuppressLint("NonConstantResourceId")
class MainActivity : AppCompatActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @BindView(R.id.root)
    lateinit var root: View

    @BindView(R.id.chart_RSRP)
    lateinit var chartRSRP: LineChart

    @BindView(R.id.chart_RSRQ)
    lateinit var chartRSRQ: LineChart

    @BindView(R.id.chart_SINR)
    lateinit var chartSINR: LineChart

    @BindView(R.id.progress_RSRP)
    lateinit var progressRSRP: ProgressBar

    @BindView(R.id.progress_RSRQ)
    lateinit var progressRSRQ: ProgressBar

    @BindView(R.id.progress_SINR)
    lateinit var progressSINR: ProgressBar

    @BindView(R.id.current_RSRP_value)
    lateinit var currentRSRP: TextView

    @BindView(R.id.current_RSRQ_value)
    lateinit var currentRSRQ: TextView

    @BindView(R.id.current_SINR_value)
    lateinit var currentSINR: TextView

    private lateinit var lineDataRSRP: LineData
    private lateinit var lineDataRSRQ: LineData
    private lateinit var lineDataSINR: LineData

    private val viewModel by viewModels<MainViewModel> { viewModelFactory }
    private val handler = Handler(Looper.getMainLooper())
    private val runnable = Runnable { getNextEntries() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = getString(R.string.title_main_screen)
        ButterKnife.bind(this)

        setupChartRSRP()
        setupChartRSRQ()
        setupChartSINR()

        observeData()
        viewModel.getNetInfo()
    }

    private fun observeData() {
        viewModel.netInfo.observe(this, Observer {
            when (it) {
                is Response.Success -> {
                    val netInfo = it.data
                    addEntriesToCharts(netInfo)
                    setTableData(netInfo)
                    scheduleNextFetch()
                }
                is Response.Error -> showError(it.message)
                is Response.Empty -> handleEmptyResponse()
            }
        })
    }

    private fun scheduleNextFetch() = handler.postDelayed(runnable, WAIT_DURATION)

    private fun getNextEntries() = viewModel.getNetInfo()

    private fun handleEmptyResponse(): Nothing = TODO()

    private fun showError(message: String?) = showSnackBar(message) { viewModel.getNetInfo() }

    private fun showSnackBar(message: String?, listener: (View) -> Unit) {
        if (message == null) {
            return
        }
        Snackbar.make(root, message, Snackbar.LENGTH_INDEFINITE).run {
            setAction(getString(R.string.retry), listener)
            show()
            view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)?.let {
                it.gravity = Gravity.CENTER_HORIZONTAL
            }
        }
    }

    private fun setupChartRSRP() {
        lineDataRSRP = LineData(
                createLineDataSet(LABEL_RSRP_P, Color.BLUE),
                createLineDataSet(LABEL_RSRP_S1, Color.RED),
                createLineDataSet(LABEL_RSRP_S2, Color.GREEN)
        )
        with(chartRSRP) {
            setupChart()
            setupXAxis()
            setupYAxis(min = ChartType.RSRP.min, max = ChartType.RSRP.max)
            data = lineDataRSRP
            invalidate()
            legend.isEnabled = true
        }
    }

    private fun setupChartRSRQ() {
        lineDataRSRQ = LineData(
                createLineDataSet(LABEL_RSRQ_P, Color.BLUE),
                createLineDataSet(LABEL_RSRQ_S1, Color.RED),
                createLineDataSet(LABEL_RSRQ_S2, Color.GREEN)
        )
        with(chartRSRQ) {
            setupChart()
            setupXAxis()
            setupYAxis(min = ChartType.RSRQ.min, max = ChartType.RSRQ.max)
            data = lineDataRSRQ
            invalidate()
            legend.isEnabled = true
        }
    }

    private fun setupChartSINR() {
        lineDataSINR = LineData(
                createLineDataSet(LABEL_SINR_P, Color.BLUE),
                createLineDataSet(LABEL_SINR_S1, Color.RED),
                createLineDataSet(LABEL_SINR_S2, Color.GREEN)
        )
        with(chartSINR) {
            setupChart()
            setupXAxis()
            setupYAxis(min = ChartType.SINR.min, max = ChartType.SINR.max)
            data = lineDataSINR
            invalidate()
            legend.isEnabled = true
        }
    }

    private fun addEntriesToCharts(netInfo: NetInfo) {
        addEntriesToChartRSRP(netInfo)
        addEntriesToChartRSRQ(netInfo)
        addEntriesToChartSINR(netInfo)
    }

    private fun addEntriesToChartRSRP(netInfo: NetInfo) {
        val mainEntry = getEntry(netInfo.RSRP)
        with(lineDataRSRP) {
            addEntry(mainEntry, BLUE_DATA_SET_INDEX)
            addEntry(getEntry(ChartType.RSRP.scale(netInfo.RSRP)), RED_DATA_SET_INDEX)
            addEntry(getEntry(ChartType.RSRP.scale(netInfo.RSRP)), GREEN_DATA_SET_INDEX)
            notifyDataChanged()
        }
        with(chartRSRP) {
            notifyDataSetChanged()
            setVisibleXRangeMaximum(MAX_VISIBLE_X_RANGE)
            moveViewToX(mainEntry.x)
        }
    }

    private fun addEntriesToChartRSRQ(netInfo: NetInfo) {
        val mainEntry = getEntry(netInfo.RSRQ)
        with(lineDataRSRQ) {
            addEntry(mainEntry, BLUE_DATA_SET_INDEX)
            addEntry(getEntry(ChartType.RSRQ.scale(netInfo.RSRQ)), RED_DATA_SET_INDEX)
            addEntry(getEntry(ChartType.RSRQ.scale(netInfo.RSRQ)), GREEN_DATA_SET_INDEX)
            notifyDataChanged()
        }
        with(chartRSRQ) {
            notifyDataSetChanged()
            setVisibleXRangeMaximum(MAX_VISIBLE_X_RANGE)
            moveViewToX(mainEntry.x)
        }
    }

    private fun addEntriesToChartSINR(netInfo: NetInfo) {
        val mainEntry = getEntry(netInfo.SINR)
        with(lineDataSINR) {
            addEntry(mainEntry, BLUE_DATA_SET_INDEX)
            addEntry(getEntry(ChartType.SINR.scale(netInfo.SINR)), RED_DATA_SET_INDEX)
            addEntry(getEntry(ChartType.SINR.scale(netInfo.SINR)), GREEN_DATA_SET_INDEX)
            notifyDataChanged()
        }
        with(chartSINR) {
            notifyDataSetChanged()
            setVisibleXRangeMaximum(MAX_VISIBLE_X_RANGE)
            moveViewToX(mainEntry.x)
        }
    }

    private fun setTableData(netInfo: NetInfo) {
        with(progressRSRP) {
            progressDrawable = ContextCompat.getDrawable(
                    this@MainActivity, ChartType.RSRP.getProgressDrawable(netInfo.RSRP)
            )
            animate(duration = ANIMATION_DURATION)
        }
        with(progressRSRQ) {
            progressDrawable = ContextCompat.getDrawable(
                    this@MainActivity, ChartType.RSRQ.getProgressDrawable(netInfo.RSRQ)
            )
            animate(duration = ANIMATION_DURATION)
        }
        with(progressSINR) {
            progressDrawable = ContextCompat.getDrawable(
                    this@MainActivity, ChartType.SINR.getProgressDrawable(netInfo.SINR)
            )
            animate(duration = ANIMATION_DURATION)
        }
        currentRSRP.text = netInfo.RSRP.toString()
        currentRSRQ.text = netInfo.RSRQ.toString()
        currentSINR.text = netInfo.SINR.toString()
    }

    override fun supportFragmentInjector() = dispatchingAndroidInjector

    companion object {
        private const val WAIT_DURATION = 2000L // In millis
        private const val ANIMATION_DURATION = 500L // In millis
        private const val MAX_VISIBLE_X_RANGE = 20f

        private const val BLUE_DATA_SET_INDEX = 0
        private const val RED_DATA_SET_INDEX = 1
        private const val GREEN_DATA_SET_INDEX = 2

        private const val LABEL_RSRP_P = "RSRP P"
        private const val LABEL_RSRP_S1 = "RSRP S1"
        private const val LABEL_RSRP_S2 = "RSRP S2"

        private const val LABEL_RSRQ_P = "RSRQ P"
        private const val LABEL_RSRQ_S1 = "RSRQ S1"
        private const val LABEL_RSRQ_S2 = "RSRQ S2"

        private const val LABEL_SINR_P = "SINR P"
        private const val LABEL_SINR_S1 = "SINR S1"
        private const val LABEL_SINR_S2 = "SINR S2"

        private fun getEntry(y: Int): Entry {
            val calendar = Calendar.getInstance().also {
                it.timeInMillis = System.currentTimeMillis()
            }
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)
            val second = calendar.get(Calendar.SECOND)

            val x = (hour * 3600) + (minute * 60) + second
            return Entry(x.toFloat(), y.toFloat())
        }

        private fun createLineDataSet(label: String?, @ColorInt color: Int) =
                LineDataSet(null, label).apply {
                    axisDependency = YAxis.AxisDependency.LEFT
                    setColor(color)
                    highLightColor = Color.BLACK
                    lineWidth = 1.5f
                    setDrawCircles(true)
                    setCircleColor(color)
                    setDrawValues(false)
                    setDrawCircleHole(false)
                    mode = LineDataSet.Mode.CUBIC_BEZIER
                }
    }
}