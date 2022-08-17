package ted.gun0912.rangebarchart

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import com.github.mikephil.charting.animation.ChartAnimator
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.renderer.BarLineScatterCandleBubbleRenderer
import com.github.mikephil.charting.utils.Transformer
import com.github.mikephil.charting.utils.Utils
import com.github.mikephil.charting.utils.ViewPortHandler

class RangeBarChartRenderer(
    private val chart: RangeBarDataProvider,
    animator: ChartAnimator,
    viewPortHandler: ViewPortHandler
) : BarLineScatterCandleBubbleRenderer(animator, viewPortHandler) {

    override fun initBuffers() {

    }

    override fun drawData(canvas: Canvas) {

        val rangeBarData: RangeBarData = chart.getRangeBarData()

        for (index in 0 until rangeBarData.dataSetCount) {
            val set = rangeBarData.getDataSetByIndex(index)
            if (set.isVisible) {
                drawDataSet(canvas, set)
            }
        }
    }

    protected fun drawDataSet(canvas: Canvas, dataSet: RangeBarDataSet) {

        val transformer: Transformer = chart.getTransformer(dataSet.axisDependency)
        mRenderPaint.style = Paint.Style.FILL

        val minEntry = dataSet.minEntry
        val maxEntry = dataSet.maxEntry
        val barWidth = dataSet.barWidth

        mXBounds[chart] = dataSet
        for (index in mXBounds.min..mXBounds.range + mXBounds.min) {
            val entry: RangeBarEntry = dataSet.getEntryForIndex(index)
            val rect = getRect(entry, transformer, barWidth)

            when {
                !dataSet.isMinMaxEnabled -> mRenderPaint.color = dataSet.color
                entry == minEntry -> {
                    mRenderPaint.color = dataSet.minColor
                    dataSet.minEntry?.let { drawMinMaxValue(canvas, dataSet, it) }
                }
                entry == maxEntry -> {
                    mRenderPaint.color = dataSet.maxColor
                    dataSet.maxEntry?.let { drawMinMaxValue(canvas, dataSet, it) }
                }
                else -> mRenderPaint.color = dataSet.color
            }
            canvas.drawRect(rect, mRenderPaint)

        }

    }

    private fun drawMinMaxValue(
        canvas: Canvas,
        dataSet: RangeBarDataSet,
        entry: RangeBarEntry
    ) {
        if (dataSet.isDrawValuesEnabled) {
            return
        }
        val transformer: Transformer = chart.getTransformer(dataSet.axisDependency)
        applyValueTextStyle(dataSet)
        drawValue(
            canvas,
            entry,
            transformer,
            dataSet.valueFormatter,
            dataSet.valueTextSize,
            dataSet.valueTextColor
        )
    }

    private fun getRect(entry: RangeBarEntry, transformer: Transformer, barWidth: Float) =
        RectF().apply {
            val xPosition = entry.x

            left = xPosition - barWidth / 2
            right = xPosition + barWidth / 2
            top = entry.max
            bottom = entry.min

            transformer.rectToPixelPhase(this, mAnimator.phaseY)
        }

    override fun drawValues(canvas: Canvas) {
        if (!isDrawingValuesAllowed(chart)) {
            return
        }
        chart.getRangeBarData().dataSets
            .filter { shouldDrawValues(it) && it.entryCount > 0 }
            .forEach { dataSet ->
                drawValue(canvas, dataSet)
            }
    }

    private fun drawValue(canvas: Canvas, dataSet: RangeBarDataSet) {
        if (!dataSet.isDrawValuesEnabled) {
            return
        }
        applyValueTextStyle(dataSet)
        mXBounds[chart] = dataSet
        val transformer: Transformer = chart.getTransformer(dataSet.axisDependency)
        for (index in mXBounds.min..mXBounds.range + mXBounds.min) {
            val entry: RangeBarEntry = dataSet.getEntryForIndex(index)
            drawValue(
                canvas,
                entry,
                transformer,
                dataSet.valueFormatter,
                dataSet.valueTextSize,
                dataSet.valueTextColor
            )
        }
    }

    private fun drawValue(
        canvas: Canvas,
        entry: RangeBarEntry,
        transformer: Transformer,
        valueFormatter: ValueFormatter,
        textSize: Float,
        textColor: Int
    ) {
        val yOffset = Utils.convertDpToPixel(TEXT_VALUE_MARGIN_DP)

        val rect = getRect(entry, transformer, 0f)
        val maxY = rect.top
        val minY = rect.bottom

        // draw max
        drawValue(
            canvas,
            valueFormatter.getFormattedValue(entry.max),
            rect.centerX(),
            (maxY - yOffset),
            textColor,
        )

        // draw min
        drawValue(
            canvas,
            valueFormatter.getFormattedValue(entry.min),
            rect.centerX(),
            (minY + yOffset + textSize * 0.7f),
            textColor,
        )
    }

    override fun drawValue(c: Canvas, valueText: String, x: Float, y: Float, color: Int) {
        mValuePaint.color = color
        c.drawText(valueText, x, y, mValuePaint)
    }

    override fun drawExtras(c: Canvas) {

    }

    override fun drawHighlighted(canvas: Canvas, indices: Array<out Highlight>) {

        val rangeBarData = chart.getRangeBarData()
        for (highlight in indices) {
            val rangeBarDataSet = rangeBarData.getDataSetByIndex(highlight.dataSetIndex)
            if (rangeBarDataSet == null || !rangeBarDataSet.isHighlightEnabled) {
                continue
            }
            val entry = rangeBarDataSet.getEntryForXValue(highlight.x, highlight.y)
            if (!isInBoundsX(entry, rangeBarDataSet)) continue
            mHighlightPaint.apply {
                color = rangeBarDataSet.highLightColor
                style = Paint.Style.FILL
            }
            drawHighlight(canvas, mHighlightPaint, rangeBarDataSet, entry)
        }
    }

    private fun drawHighlight(
        canvas: Canvas,
        paint: Paint,
        dataSet: RangeBarDataSet,
        entry: RangeBarEntry
    ) {
        val transformer = chart.getTransformer(dataSet.axisDependency)
        val barWidth = dataSet.barWidth
        val rect = getRect(entry, transformer, barWidth)
        canvas.drawRect(rect, paint)

        if (!dataSet.isDrawValuesEnabled) {
            applyValueTextStyle(dataSet)
            drawValue(
                canvas,
                entry,
                transformer,
                dataSet.valueFormatter,
                dataSet.valueTextSize,
                dataSet.valueTextColor
            )
        }
    }

    companion object {

        private const val TEXT_VALUE_MARGIN_DP = 6f
    }
}
