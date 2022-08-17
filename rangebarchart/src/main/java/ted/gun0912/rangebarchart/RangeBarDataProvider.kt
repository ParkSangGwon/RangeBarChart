package ted.gun0912.rangebarchart

import com.github.mikephil.charting.interfaces.dataprovider.BarLineScatterCandleBubbleDataProvider
import ted.gun0912.rangebarchart.RangeBarData

interface RangeBarDataProvider : BarLineScatterCandleBubbleDataProvider {

    fun getRangeBarData(): RangeBarData
}
