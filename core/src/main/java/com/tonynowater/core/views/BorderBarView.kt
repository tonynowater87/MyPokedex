package com.tonynowater.core.views

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.view.animation.DecelerateInterpolator
import com.tonynowater.core.R
import kotlin.math.ceil

class BorderBarView : View {

    enum class Orientation {
        Vertical, Horizontal
    }

    companion object {
        private const val MAX_VALUE = 100F
        const val BAR_SIZE = 10
        const val TENTH = (MAX_VALUE / BAR_SIZE).toInt()
    }

    private val BAR_MARGIN = 2 * resources.displayMetrics.density
    private val LEFT_MARGIN = 4 * resources.displayMetrics.density
    private val RIGHT_MARGIN = 4 * resources.displayMetrics.density

    private var mWidth: Int = 0
    private var mHeight: Int = 0
    private var mBarHeight = 0F
    private var mOrientation = Orientation.Horizontal

    private val paintBorder = Paint().apply {
        style = Paint.Style.STROKE
        strokeWidth = 0.5F * resources.displayMetrics.density
    }

    private val paintBar = Paint().apply {
        style = Paint.Style.FILL
    }

    var currentValue: Int = 0
        set(value) {
            field = value
            startAnimation()
        }

    private var barsBorderRect = mutableListOf<RectF>()
    private var barsRect = mutableListOf<RectF>()

    constructor(context: Context?) : super(context)

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initAttrs(attrs)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initAttrs(attrs)
    }

    private fun initAttrs(attributeSet: AttributeSet?) {
        val typeArray = context.obtainStyledAttributes(attributeSet, R.styleable.BorderBarView)
        paintBar.color = typeArray.getColor(R.styleable.BorderBarView_bar_fill_color, Color.DKGRAY)
        paintBorder.color = typeArray.getColor(R.styleable.BorderBarView_bar_border_color, Color.GRAY)
        mOrientation = Orientation.values()[typeArray.getInt(
            R.styleable.BorderBarView_bar_stack_orientation,
            0
        )]
        typeArray.recycle()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = if (mOrientation == Orientation.Vertical) w else h
        mHeight = if (mOrientation == Orientation.Vertical) h else w

        mBarHeight = (mHeight - BAR_SIZE * BAR_MARGIN - BAR_SIZE * 2 * getBorderStrokeWidth()) / BAR_SIZE

        // Timber.d("[DEBUG] w => $mWidth, h => $mHeight, barSize => $mBarHeight, borderStrokeWidth => ${paintBorder.strokeWidth}")
        initBarsBorderRect()
    }

    private fun initBarsBorderRect() {

        barsBorderRect.clear()

        for (index in 0 until BAR_SIZE) {

            val borderRect = if (mOrientation == Orientation.Vertical) {
                RectF(
                    LEFT_MARGIN,
                    index * mBarHeight + index * BAR_MARGIN + mBarHeight + getBorderStrokeWidth(),
                    mWidth - RIGHT_MARGIN,
                    index * BAR_MARGIN + index * mBarHeight + getBorderStrokeWidth()
                )
            } else {
                RectF(
                    index * BAR_MARGIN + index * mBarHeight + getBorderStrokeWidth(),
                    LEFT_MARGIN,
                    index * mBarHeight + index * BAR_MARGIN + mBarHeight + getBorderStrokeWidth(),
                    mWidth - RIGHT_MARGIN
                )
            }
            barsBorderRect.add(index, borderRect)
        }
    }

    private fun getBorderStrokeWidth() = if (paintBorder.strokeWidth == 0F) 1F else paintBorder.strokeWidth

    private fun updateBarsRect(value: Float) {

        var origin = value
        val ceil = ceil(value / TENTH)

        val startIndex = if (mOrientation == Orientation.Vertical) BAR_SIZE - 1 else 0
        val endIndex =
            if (mOrientation == Orientation.Vertical) BAR_SIZE - ceil.toInt() else ceil.toInt()

        if (mOrientation == Orientation.Vertical) { // vertical mode

            for (index in startIndex downTo endIndex) {

                val barRect = if (origin - TENTH > 0F) {
                    // full bar
                    RectF(
                        LEFT_MARGIN,
                        index * mBarHeight + index * BAR_MARGIN + mBarHeight + getBorderStrokeWidth(),
                        mWidth - RIGHT_MARGIN,
                        index * mBarHeight + index * BAR_MARGIN + getBorderStrokeWidth()
                    )
                } else {
                    // not full bar
                    RectF(
                        LEFT_MARGIN,
                        index * mBarHeight + index * BAR_MARGIN + mBarHeight + getBorderStrokeWidth(),
                        mWidth - RIGHT_MARGIN,
                        index * mBarHeight + index * BAR_MARGIN + mBarHeight * (1 - origin / TENTH) + getBorderStrokeWidth()
                    )
                }

                barsRect.add(barRect)

                origin -= TENTH
            }
        } else { // horizontal mode

            for (index in startIndex until endIndex) {

                val barRect = if (origin - TENTH > 0F) {
                    // full bar
                    RectF(
                        index * mBarHeight + index * BAR_MARGIN + getBorderStrokeWidth(),
                        LEFT_MARGIN,
                        index * mBarHeight + index * BAR_MARGIN + mBarHeight + getBorderStrokeWidth(),
                        mWidth - RIGHT_MARGIN
                    )
                } else {
                    // not full bar
                    RectF(
                        index * mBarHeight + index * BAR_MARGIN + getBorderStrokeWidth(),
                        LEFT_MARGIN,
                        index * mBarHeight + index * BAR_MARGIN + mBarHeight * (origin / TENTH) + getBorderStrokeWidth(),
                        mWidth - RIGHT_MARGIN
                    )
                }

                barsRect.add(barRect)

                origin -= TENTH
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (!barsRect.isNullOrEmpty()) {
            barsRect.forEach {
                canvas.drawRect(it, paintBar)
            }
        }

        barsBorderRect.forEach {
            canvas.drawRect(it, paintBorder)
        }
    }

    private fun startAnimation() {
        ValueAnimator
            .ofFloat(0F, currentValue.toFloat())
            .apply {
                duration = resources.getInteger(android.R.integer.config_longAnimTime).toLong()
                interpolator = DecelerateInterpolator()
                addUpdateListener {
                    barsRect.clear()
                    updateBarsRect(it.animatedValue as Float)
                    invalidate()
                }
            }
            .start()
    }
}
