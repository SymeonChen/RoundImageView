package com.symeonchen.roundimageview

import android.annotation.TargetApi
import android.content.Context
import android.graphics.Path
import android.os.Build
import android.util.AttributeSet
import androidx.annotation.RequiresApi


/**
 * Created by SymeonChen on 2020/3/7.
 */
class RoundImageView @JvmOverloads constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int = 0)
    : AbstractRoundImageView(context, attrs, defStyleAttr) {

    var radius = 0f
    private var topStart = true
    private var topEnd = true
    private var bottomStart = true
    private var bottomEnd = true

    init {
        initAttr(attrs)
    }

    private fun initAttr(attrs: AttributeSet?) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.RoundImageView)
        radius = a.getDimension(R.styleable.RoundImageView_corner_radius, 0f)
        topStart = a.getBoolean(R.styleable.RoundImageView_top_start, true)
        topEnd = a.getBoolean(R.styleable.RoundImageView_top_end, true)
        bottomStart = a.getBoolean(R.styleable.RoundImageView_bottom_start, true)
        bottomEnd = a.getBoolean(R.styleable.RoundImageView_bottom_end, true)
        a.recycle()
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    override fun provideDefaultPath(): Path {
        val path = Path()
        path.addPath(provideFullCornerPath())
        if (topStart && topEnd && bottomStart && bottomEnd) {
            return path
        }
        if (!topStart) {
            path.addPath(provideTopStartCornerPath())
        }
        if (!topEnd) {
            path.addPath(provideTopEndCornerPath())
        }
        if (!bottomStart) {
            path.addPath(provideBottomStartCornerPath())
        }
        if (!bottomEnd) {
            path.addPath(provideBottomEndCornerPath())
        }
        return path
    }

    @TargetApi(Build.VERSION_CODES.P)
    override fun provideInversePath(): Path {
        if (radius <= 0f) {
            return Path()
        }
        if (!topStart && !topEnd && !bottomStart && !bottomEnd) {
            return Path()
        }
        val result = Path()
        if (topStart) {
            result.addPath(provideTopStartCornerPath())
        }
        if (topEnd) {
            result.addPath(provideTopEndCornerPath())
        }
        if (bottomStart) {
            result.addPath(provideBottomStartCornerPath())
        }
        if (bottomEnd) {
            result.addPath(provideBottomEndCornerPath())
        }
        return result
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun provideFullCornerPath(): Path {
        val cornerRadius = minOf(width.toFloat(), height.toFloat(), radius)
        val path = Path()
        path.addRoundRect(
                paddingStart.toFloat(),
                paddingTop.toFloat(),
                width - paddingEnd.toFloat(),
                height - paddingBottom.toFloat(),
                floatArrayOf(
                        cornerRadius,
                        cornerRadius,
                        cornerRadius,
                        cornerRadius,
                        cornerRadius,
                        cornerRadius,
                        cornerRadius,
                        cornerRadius
                ),
                Path.Direction.CW
        )
        return path
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun provideTopStartCornerPath(): Path {
        val cornerRadius = minOf(width / 2.toFloat(), height / 2.toFloat(), radius)
        val path = Path()
        path.moveTo(paddingStart + cornerRadius, paddingTop.toFloat())
        path.arcTo(
                paddingStart.toFloat(),
                paddingTop.toFloat(),
                paddingStart.toFloat() + 2 * cornerRadius,
                paddingTop.toFloat() + 2 * cornerRadius,
                -90f,
                -90f,
                false
        )

        path.lineTo(paddingStart.toFloat(), paddingTop.toFloat())
        path.lineTo(paddingStart.toFloat() + cornerRadius, paddingTop.toFloat())
        path.close()
        return path
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun provideTopEndCornerPath(): Path {
        val cornerRadius = minOf(width / 2.toFloat(), height / 2.toFloat(), radius)
        val path = Path()
        path.moveTo(width - paddingEnd.toFloat() - cornerRadius, paddingTop.toFloat())
        path.arcTo(
                width - paddingEnd - 2 * cornerRadius,
                paddingTop.toFloat(),
                width - paddingEnd.toFloat(),
                paddingTop + 2 * cornerRadius,
                -90f,
                90f,
                false
        )
        path.lineTo(width - paddingEnd.toFloat(), paddingTop.toFloat())
        path.lineTo(width - paddingEnd.toFloat() - cornerRadius, paddingTop.toFloat())
        path.close()
        return path
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun provideBottomStartCornerPath(): Path {
        val cornerRadius = minOf(width / 2.toFloat(), height / 2.toFloat(), radius)
        val path = Path()
        path.moveTo(paddingStart + cornerRadius, height - paddingBottom.toFloat())
        path.arcTo(
                paddingStart.toFloat(),
                height - paddingBottom.toFloat() - 2 * cornerRadius,
                paddingStart.toFloat() + 2 * cornerRadius,
                height - paddingBottom.toFloat(),
                90f,
                90f,
                false
        )
        path.lineTo(paddingStart.toFloat(), height - paddingBottom.toFloat())
        path.lineTo(paddingStart + cornerRadius, height - paddingBottom.toFloat())
        path.close()
        return path
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun provideBottomEndCornerPath(): Path {
        val cornerRadius = minOf(width / 2.toFloat(), height / 2.toFloat(), radius)
        val path = Path()
        path.moveTo(width - paddingEnd.toFloat() - cornerRadius, height - paddingBottom.toFloat())
        path.arcTo(
                (width - paddingEnd) - 2 * cornerRadius,
                (height - paddingBottom) - 2 * cornerRadius,
                (width - paddingEnd).toFloat(),
                (height - paddingBottom).toFloat(),
                90f,
                -90f,
                false
        )
        path.lineTo(width - paddingEnd.toFloat(), height - paddingBottom.toFloat())
        path.lineTo(width - paddingEnd.toFloat() - cornerRadius, height - paddingBottom.toFloat())
        path.close()
        return path
    }

}