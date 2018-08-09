package com.roundel.smarthome.ui.view

import android.animation.ValueAnimator
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.graphics.drawable.Animatable
import android.graphics.drawable.AnimatedVectorDrawable
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.ImageView
import com.roundel.smarthome.R


class LightOnOffButton : ImageView {
    var lightStatus = false
        set(value) {
            refreshView(value)
            field = value
        }

    var onStatusChangedListener: OnStatusChangedListener? = null

    var iconColorEnabled: Int = 0
    var iconColorDisabled: Int = 0
    var backgroundColorEnabled: Int = 0
    var backgroundColorDisabled: Int = 0

    var animationDuration: Int = 250

    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, 0, 0)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int)
            : super(context, attrs, defStyleAttr, defStyleRes) {
        val theme = context.theme

        val iconColorEnabledValue = TypedValue()
        val iconColorDisabledValue = TypedValue()
        val backgroundColorEnabledValue = TypedValue()
        val backgroundColorDisabledValue = TypedValue()

        theme.resolveAttribute(android.R.attr.textColorPrimaryInverse, iconColorEnabledValue, true)
        theme.resolveAttribute(android.R.attr.textColorPrimaryInverse, iconColorDisabledValue, true)
        theme.resolveAttribute(android.R.attr.colorAccent, backgroundColorEnabledValue, true)
        theme.resolveAttribute(android.R.attr.textColorSecondary, backgroundColorDisabledValue, true)

        iconColorEnabled = iconColorEnabledValue.data
        iconColorDisabled = iconColorDisabledValue.data
        backgroundColorEnabled = backgroundColorEnabledValue.data
        backgroundColorDisabled = backgroundColorDisabledValue.data


        val typedArray = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.LightOnOffButton,
                0, 0)
        try {
            iconColorEnabled = typedArray.getColor(R.styleable.LightOnOffButton_iconColorEnabled, iconColorEnabled)
            iconColorDisabled = typedArray.getColor(R.styleable.LightOnOffButton_iconColorDisabled, iconColorDisabled)
            backgroundColorEnabled = typedArray.getColor(R.styleable.LightOnOffButton_backgroundColorEnabled, backgroundColorEnabled)
            backgroundColorDisabled = typedArray.getColor(R.styleable.LightOnOffButton_backgroundColorDisabled, backgroundColorDisabled)

            animationDuration = typedArray.getInteger(R.styleable.LightOnOffButton_animationDuration, animationDuration)
        } finally {
            typedArray.recycle()
        }

        backgroundTintMode = PorterDuff.Mode.SRC_IN
        imageTintMode = PorterDuff.Mode.SRC_IN
        this.setOnClickListener {
            lightStatus = !lightStatus
            onStatusChangedListener?.onStatusChanged(lightStatus)
        }
        refreshView(false)
    }

    private fun refreshView(lightStatus: Boolean) {
        val backgroundAnimator =
                if (lightStatus) ValueAnimator.ofArgb(backgroundColorDisabled, backgroundColorEnabled)
                else ValueAnimator.ofArgb(backgroundColorEnabled, backgroundColorDisabled)

        backgroundAnimator.apply {
            duration = this@LightOnOffButton.animationDuration.toLong()
            addUpdateListener { updatedAnimation ->
                this@LightOnOffButton.backgroundTintList =
                        ColorStateList.valueOf(updatedAnimation.animatedValue as Int)
            }
            start()
        }


        val imageAnimator =
                if (lightStatus) ValueAnimator.ofArgb(iconColorDisabled, iconColorEnabled)
                else ValueAnimator.ofArgb(iconColorEnabled, iconColorDisabled)

        imageAnimator.apply {
            duration = this@LightOnOffButton.animationDuration.toLong()
            addUpdateListener { updatedAnimation ->
                this@LightOnOffButton.imageTintList =
                        ColorStateList.valueOf(updatedAnimation.animatedValue as Int)
            }
            start()
        }
        setImageResource(if (lightStatus) R.drawable.avd_lightbulb_off else R.drawable.avd_lightbulb_on)
        if(animationDuration > 0)
            (drawable as AnimatedVectorDrawable).start()
    }

    interface OnStatusChangedListener {
        fun onStatusChanged(status: Boolean)
    }
}