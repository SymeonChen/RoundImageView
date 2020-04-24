package com.symeonchen.roundimageview

import android.annotation.TargetApi
import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView


/**
 * Created by SymeonChen on 2020/3/7.
 */
abstract class AbstractRoundImageView(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int = 0)
    : AppCompatImageView(
        context,
        attributeSet,
        defStyleAttr) {

    //draw
    private var paint = Paint()
    private var defaultMode = PorterDuffXfermode(PorterDuff.Mode.DST_IN)
    private var transparentMode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)

    override fun onDraw(canvas: Canvas?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas ?: return
            paint.isAntiAlias = true
            paint.color = Color.WHITE
            val saveCount = canvas.saveLayer(
                    0.toFloat(),
                    0.toFloat(),
                    (width).toFloat(),
                    (height).toFloat(),
                    null
            )
            super.onDraw(canvas)
            paint.xfermode = defaultMode
            canvas.drawPath(provideDefaultPath(), paint)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                paint.xfermode = transparentMode
                paint.color = Color.TRANSPARENT
                canvas.drawPath(provideInversePath(), paint)
            }
            canvas.restoreToCount(saveCount)
            paint.xfermode = null
        } else {
            super.onDraw(canvas)
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    protected abstract fun provideDefaultPath(): Path

    @TargetApi(Build.VERSION_CODES.P)
    protected abstract fun provideInversePath(): Path


}