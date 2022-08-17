package ted.gun0912.rangebarchart

import com.github.mikephil.charting.data.Entry

data class RangeBarEntry(
    private val xPosition: Float,
    var min: Float,
    var max: Float,
) : Entry(xPosition, (min + max) / 2) {

    override fun copy(): RangeBarEntry = copy()
}
