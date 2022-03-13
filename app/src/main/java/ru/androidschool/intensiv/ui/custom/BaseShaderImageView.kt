package ru.androidschool.intensiv.ui.custom

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.graphics.drawable.toBitmap
import timber.log.Timber
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt

@SuppressLint("AppCompatCustomView")
abstract class BaseShaderImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ImageView(context, attrs, defStyleAttr) {

    abstract val paint: Paint

    abstract override fun onDraw(canvas: Canvas)

    override fun setImageBitmap(bm: Bitmap) {
        super.setImageBitmap(bm)
        Timber.d("setImageBitmap")
        prepareShader(width, height)
    }

    override fun setImageDrawable(drawable: Drawable?) {
        super.setImageDrawable(drawable)
        Timber.d("setImageDrawable")
        prepareShader(width, height)
    }

    override fun setImageResource(@DrawableRes resId: Int) {
        super.setImageResource(resId)
        Timber.d("setImageResource")
        prepareShader(width, height)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        Timber.d("onSizeChanged")

        if (w == 0) {
            return
        }
        doOnSizeChanged(w, h, oldw, oldh)
        prepareShader(w, h)
    }

    abstract fun doOnSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int)

    private fun prepareShader(width: Int, height: Int) {
        Timber.d("prepareShader")
        if (width == 0 || drawable == null) {
            return
        }

        val drawableWidth = drawable.intrinsicWidth
        val drawableHeight = drawable.intrinsicHeight
        Timber.d("prepareShader: drawableWidth=$drawableWidth, drawableHeight=$drawableHeight")

        val viewWidth = width - paddingLeft - paddingRight
        val viewHeight = height - paddingTop - paddingBottom
        Timber.d("prepareShader: viewWidth=$viewWidth, viewHeight=$viewHeight")

        val resultBitmap = if (scaleType == ScaleType.CENTER_CROP) {
            val srcBitmap =
                drawable.toBitmap(drawableWidth, drawableHeight, Bitmap.Config.ARGB_8888)
            cropBitmap(srcBitmap, drawableWidth, drawableHeight, viewWidth, viewHeight)
        } else {
            drawable.toBitmap(width, height, Bitmap.Config.ARGB_8888)
        }

        paint.shader = BitmapShader(resultBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
    }

    private fun cropBitmap(
        srcBitmap: Bitmap,
        drawableWidth: Int,
        drawableHeight: Int,
        viewWidth: Int,
        viewHeight: Int
    ): Bitmap {
        if (viewWidth == drawableWidth && viewHeight == drawableHeight) {
            return srcBitmap
        }

        val m = Matrix()
        val scale =
            max(viewWidth.toFloat() / drawableWidth, viewHeight.toFloat() / drawableHeight)
        Timber.d("cropBitmap: scale=$scale")
        m.setScale(scale, scale);

        val srcCroppedW = (viewWidth / scale).roundToInt()
        Timber.d("cropBitmap: srcCroppedW=$srcCroppedW")
        val srcCroppedH = (viewHeight / scale).roundToInt()
        Timber.d("cropBitmap: srcCroppedH=$srcCroppedH")

        var srcX = (drawableWidth * 0.5 - srcCroppedW / 2).roundToInt()
        Timber.d("cropBitmap: srcX=$srcX")
        var srcY = (drawableHeight * 0.5 - srcCroppedH / 2).roundToInt()
        Timber.d("cropBitmap: srcY=$srcY")

        srcX = max(min(srcX, drawableWidth - srcCroppedW), 0)
        Timber.d("cropBitmap: drawableWidth - srcCroppedW=${drawableWidth - srcCroppedW}")
        Timber.d("cropBitmap: srcX=$srcX")
        srcY = max(min(srcY, drawableHeight - srcCroppedH), 0)
        Timber.d("cropBitmap: drawableHeight - srcCroppedH=${drawableHeight - srcCroppedH}")
        Timber.d("cropBitmap: srcY=$srcY")

        return Bitmap.createBitmap(srcBitmap, srcX, srcY, srcCroppedW, srcCroppedH, m, true)
    }

}