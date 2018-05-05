package com.roundel.ledcontrol

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.CardView
import com.jaredrummler.android.colorpicker.ColorPickerDialog
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener

import kotlinx.android.synthetic.main.activity_config.circle_change_color as mChangeColorCardView

class ConfigurationActivity : AppCompatActivity(), ColorPickerDialogListener {
    override fun onDialogDismissed(dialogId: Int) {

    }

    override fun onColorSelected(dialogId: Int, color: Int) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_config)

        mChangeColorCardView.setOnClickListener {
            ColorPickerDialog.newBuilder().setColor(0xffffff).show(this)
        }
    }

}
