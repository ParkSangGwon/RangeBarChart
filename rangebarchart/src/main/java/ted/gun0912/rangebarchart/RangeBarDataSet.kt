package ted.gun0912.rangebarchart

import android.graphics.Color
import androidx.annotation.ColorInt
import com.github.mikephil.charting.data.BarLineScatterCandleBubbleDataSet
import com.github.mikephil.charting.data.DataSet

class RangeBarDataSet(yVals: List<RangeBarEntry>?, label: String) :
    BarLineScatterCandleBubbleDataSet<RangeBarEntry>(yVals, label) {

    var barWidth: Float = 0.85f
        set(value) {
            field = value.coerceIn(0f, 1f)
        }

    var isMinMaxEnabled = true

    @ColorInt
    var minColor: Int = Color.BLUE
    var maxColor: Int = Color.RED

    val maxEntry
        get() = mValues
            .sortedByDescending { it.min }
            .maxByOrNull { it.max }

    val minEntry
        get() = mValues
            .sortedBy { it.max }
            .minByOrNull { it.min }

    override fun getEntryIndex(entry: RangeBarEntry): Int = super.getEntryIndex(entry)

    override fun copy(): DataSet<RangeBarEntry> {
        val values: List<RangeBarEntry> = mValues ?: return RangeBarDataSet(null, label)
        val entries = values.map { it.copy() }
        return RangeBarDataSet(entries, label).also {
            copy(it)
        }
    }

    protected fun copy(rangeBarDataSet: RangeBarDataSet) {
        super.copy(rangeBarDataSet)
        rangeBarDataSet.barWidth = barWidth
        rangeBarDataSet.isMinMaxEnabled = isMinMaxEnabled
        rangeBarDataSet.minColor = minColor
        rangeBarDataSet.maxColor = maxColor
    }
}
