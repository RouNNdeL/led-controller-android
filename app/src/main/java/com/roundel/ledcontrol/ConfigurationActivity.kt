package com.roundel.ledcontrol

import android.content.res.ColorStateList
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.CardView
import com.jaredrummler.android.colorpicker.ColorPickerDialog
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener
import java.net.InetAddress

import kotlinx.android.synthetic.main.activity_config.circle_change_color as mChangeColorCardView
import kotlinx.android.synthetic.main.activity_config.server_name as mServerNameTextView
import kotlinx.android.synthetic.main.activity_config.server_ip as mServerIpTextView

class ConfigurationActivity : AppCompatActivity(), ColorPickerDialogListener {
    companion object {
        private val KEY_COLOR = "color"
    }

    private var mCurrentColor: Int = 0xFFFFFFFF.toInt()
    private lateinit var server: EspServer

    override fun onDialogDismissed(dialogId: Int) {
        mChangeColorCardView.setCardBackgroundColor(mCurrentColor);
    }

    override fun onColorSelected(dialogId: Int, color: Int) {
        mCurrentColor = color
        server.sendColor(this, mCurrentColor)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_config)
        server = EspServer("LED Controller", InetAddress.getByName("192.168.1.113"))

        mServerNameTextView.text = server.name
        mServerIpTextView.text = server.ip.hostAddress;
        mChangeColorCardView.setCardBackgroundColor(ColorStateList.valueOf(mCurrentColor))
        mChangeColorCardView.setOnClickListener {
            ColorPickerDialog.newBuilder().setColor(mCurrentColor).show(this)
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putInt(KEY_COLOR, mCurrentColor)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        if(savedInstanceState != null)
        {
            mCurrentColor = savedInstanceState.getInt(KEY_COLOR)
            mChangeColorCardView.setCardBackgroundColor(ColorStateList.valueOf(mCurrentColor))
        }
    }
}
