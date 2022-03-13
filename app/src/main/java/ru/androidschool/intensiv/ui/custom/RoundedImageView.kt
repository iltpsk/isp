package ru.androidschool.intensiv.ui.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.ui.extensions.dpToPx

class RoundedImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BaseShaderImageView(context, attrs, defStyleAttr) {

    companion object {
        private const val DEFAULT_RADIUS_IN_DP = 16
    }

    override val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.colorPrimary)
        style = Paint.Style.FILL
    }
    private var radius = context.dpToPx(DEFAULT_RADIUS_IN_DP)
    private val viewRect = Rect()
    private val roundedViewRectF = RectF()

    init {
        if (attrs != null) {
            val ta = context.obtainStyledAttributes(attrs, R.styleable.RoundedImageView)
            radius = ta.getDimension(
                R.styleable.RoundedImageView_radius,
                context.dpToPx(DEFAULT_RADIUS_IN_DP)
            )
            ta.recycle()
        }
    }

    override fun onDraw(canvas: Canvas) =
        canvas.drawRoundRect(roundedViewRectF, radius, radius, paint)


    override fun doOnSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        with(viewRect) {
            left = 0
            top = 0
            right = w
            bottom = h
        }

        with(roundedViewRectF) {
            left = 0f
            top = -radius
            right = w.toFloat()
            bottom = h.toFloat()
        }
    }
}