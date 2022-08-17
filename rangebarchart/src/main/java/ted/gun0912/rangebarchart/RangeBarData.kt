package ted.gun0912.rangebarchart

import com.github.mikephil.charting.data.BarLineScatterCandleBubbleData

class RangeBarData(dataSets: MutableList<RangeBarDataSet> = mutableListOf()) :
    BarLineScatterCandleBubbleData<RangeBarDataSet>(dataSets) {

    constructor(dataSet: RangeBarDataSet) : this(mutableListOf(dataSet))
}
