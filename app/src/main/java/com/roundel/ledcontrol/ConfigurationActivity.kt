package com.roundel.ledcontrol

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.CardView
import com.jaredrummler.android.colorpicker.ColorPickerDialog
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener

import kotlinx.android.synthetic.main.activity_config.circle_change_color as mChangeColorCardView

class ConfigurationActivity : AppCompatActivity(), ColorPickerDialogListener {
    var mCurrentColor : Int = 0xFFFFFFFF.toInt();

    override fun onDialogDismissed(dialogId: Int) {
        mChangeColorCardView.setCardBackgroundColor(mCurrentColor);
    }

    override fun onColorSelected(dialogId: Int, color: Int) {
        mCurrentColor = color;
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_config)

        mChangeColorCardView.setOnClickListener {
            ColorPickerDialog.newBuilder().setColor(mCurrentColor).show(this)
        }
    }

}
