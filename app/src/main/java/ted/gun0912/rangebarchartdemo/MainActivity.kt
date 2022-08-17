package ted.gun0912.rangebarchartdemo

import android.graphics.Color
import android.os.Bundle
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.formatter.ValueFormatter
import ted.gun0912.rangebarchart.RangeBarChart
import ted.gun0912.rangebarchart.RangeBarData
import ted.gun0912.rangebarchart.RangeBarDataSet
import ted.gun0912.rangebarchart.RangeBarEntry

class MainActivity : AppCompatActivity() {

    private val chart: RangeBarChart by lazy { findViewById(R.id.chart) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        chart.setup()
        chart.addData()
        setupListener()
    }

    private fun RangeBarChart.setup() {
        setScaleEnabled(false)
        setDrawGridBackground(false)
        setBackgroundColor(Color.TRANSPARENT)
        description.isEnabled = false

        xAxis.apply {
            position = XAxis.XAxisPosition.BOTTOM
            setDrawGridLines(false)
        }

        axisLeft.apply {
            setDrawGridLines(false)
            axisMinimum = -20f
            axisMaximum = 40f
        }
        axisRight.isEnabled = false

        legend.isEnabled = false

        data = RangeBarData(createSet())
    }


    private fun createSet(): RangeBarDataSet =
        RangeBarDataSet(null, "RangeBar").apply {
            color = Color.parseColor("#2BDD9E")

            barWidth = 0.4f

            isHighlightEnabled = true
            highLightColor = Color.CYAN

            minColor = Color.parseColor("#22BCFE")
            maxColor = Color.parseColor("#FC351E")

            setDrawValues(false)
            valueTextSize = 12f
            valueFormatter = object : ValueFormatter() {
                override fun getFormattedValue(value: Float): String = "${value.toInt()}°C"
            }
        }

    private fun RangeBarChart.addData() {

        val data = data ?: return
        val rangeBarDataSet = data.getDataSetByIndex(0) ?: return

        val values = getWeatherEntries()

        rangeBarDataSet.values = values
        data.notifyDataChanged()
        notifyDataSetChanged()
    }

    private fun getWeatherEntries(): List<RangeBarEntry> {
        val values = mutableListOf<RangeBarEntry>()
        values.add(RangeBarEntry(1f, -7f, 1f))
        values.add(RangeBarEntry(2f, -4f, 4f))
        values.add(RangeBarEntry(3f, 2f, 11f))
        values.add(RangeBarEntry(4f, 8f, 18f))
        values.add(RangeBarEntry(5f, 14f, 23f))
        values.add(RangeBarEntry(6f, 19f, 26f))
        values.add(RangeBarEntry(7f, 23f, 28f))
        values.add(RangeBarEntry(8f, 25f, 33f))
        values.add(RangeBarEntry(9f, 17f, 25f))
        values.add(RangeBarEntry(10f, 10f, 18f))
        values.add(RangeBarEntry(11f, 3f, 11f))
        values.add(RangeBarEntry(12f, -3f, 4f))
        return values
    }

    private fun setupListener() {
        findViewById<CheckBox>(R.id.cb_show_min_max).setOnCheckedChangeListener { _, isChecked ->
            for (dataSet in chart.data.dataSets) {
                dataSet.isMinMaxEnabled = isChecked
            }
            chart.invalidate()
        }

        findViewById<CheckBox>(R.id.cb_show_value).setOnCheckedChangeListener { _, isChecked ->
            chart.data.setDrawValues(isChecked)
            chart.invalidate()
        }
    }
}
