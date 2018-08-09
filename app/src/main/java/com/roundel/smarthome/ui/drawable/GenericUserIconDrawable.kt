package com.roundel.smarthome.ui.drawable

import android.graphics.*
import android.graphics.drawable.Drawable
import android.support.annotation.ColorInt
import com.roundel.smarthome.drawTextCentred
import com.roundel.smarthome.random
import com.roundel.smarthome.ui.ColorUtils


class GenericUserIconDrawable : Drawable {

    var letter: String
    var backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    var textPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    constructor(letter: String, @ColorInt iconColor: Int = COLOR_PALETTE.random(0),
                @ColorInt textColor: Int = (Color.WHITE and 0x80ffffff.toInt())) : super() {
        this.letter = letter
        backgroundPaint.color = iconColor
        backgroundPaint.strokeWidth = 0f
        backgroundPaint.style = Paint.Style.FILL

        textPaint.color = textColor
        textPaint.typeface = Typeface.DEFAULT
        textPaint.style = Paint.Style.FILL
    }

    override fun draw(canvas: Canvas?) {
        canvas?.let {
            it.drawOval(RectF(0f, 0f, it.width.toFloat(), it.height.toFloat()), backgroundPaint)
            textPaint.textSize = it.height * .6f
            it.drawTextCentred(letter[0].toString(), it.width/2f, it.height/2f, textPaint)
        }
    }

    fun setIconColor(@ColorInt color: Int) {
        backgroundPaint.color = color
    }

    fun setTextColor(@ColorInt color: Int) {
        textPaint.color = color
    }

    override fun setAlpha(alpha: Int) {
        backgroundPaint.alpha = alpha
    }

    override fun getOpacity(): Int {
        return PixelFormat.TRANSPARENT
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        backgroundPaint.colorFilter = colorFilter
    }

    companion object {
        val COLOR_PALETTE: List<Int> = ColorUtils.MATERIAL500
    }
}