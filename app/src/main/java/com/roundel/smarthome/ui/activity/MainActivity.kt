package com.roundel.smarthome.ui.activity

import android.graphics.PorterDuff
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.TypedValue
import co.zsmb.materialdrawerkt.builders.accountHeader
import co.zsmb.materialdrawerkt.builders.drawer
import co.zsmb.materialdrawerkt.draweritems.badgeable.primaryItem
import co.zsmb.materialdrawerkt.draweritems.profile.profile
import com.roundel.smarthome.R
import com.roundel.smarthome.bind
import com.roundel.smarthome.random
import com.roundel.smarthome.ui.ColorUtils
import com.roundel.smarthome.ui.drawable.GenericUserIconDrawable


class MainActivity : AppCompatActivity() {

    val toolbar: Toolbar by bind(R.id.main_toolbar)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val typedValue = TypedValue()
        theme.resolveAttribute(android.R.attr.textColorSecondary, typedValue, true)
        val textColorSecondaryInverse = typedValue.data

        val enabledDrawerItem = 1

        val name = "Krzysztof Zdulski"
        drawer {
            accountHeader {
                profile(name) {
                    iconDrawable = GenericUserIconDrawable(name)
                }

                selectionListEnabled = false
                profileImagesClickable = false

                backgroundDrawable = getDrawable(R.drawable.bg_smart_devices_drawable).apply {
                    setTint(ColorUtils.MATERIAL900.random(0))
                }
            }

            primaryItem("Devices") {
                icon = R.drawable.ic_lightbulb
                iconTintingEnabled = true
            }

            primaryItem("Effects") {
                icon = R.drawable.ic_auto_fix
                iconTintingEnabled = true
            }
        }
    }
}
