package ru.androidschool.intensiv.ui.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import androidx.core.graphics.toRectF

class ActorImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BaseShaderImageView(context, attrs, defStyleAttr) {

    override val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val viewRect = Rect()

    override fun onDraw(canvas: Canvas) = canvas.drawOval(viewRect.toRectF(), paint)

    override fun doOnSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        with(viewRect) {
            left = 0
            top = 0
            right = w
            bottom = h
        }
    }
}