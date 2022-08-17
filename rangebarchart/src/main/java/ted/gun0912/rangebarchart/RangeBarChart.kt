package ted.gun0912.rangebarchart

import android.content.Context
import android.util.AttributeSet
import com.github.mikephil.charting.charts.BarLineChartBase

class RangeBarChart @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : BarLineChartBase<RangeBarData>(context, attrs, defStyleAttr), RangeBarDataProvider {

    override fun getRangeBarData(): RangeBarData = mData

    override fun init() {
        super.init()
        mRenderer = RangeBarChartRenderer(this, animator, viewPortHandler)

        xAxis.apply {
            spaceMin = 0.5f
            spaceMax = 0.5f
        }
    }
}
