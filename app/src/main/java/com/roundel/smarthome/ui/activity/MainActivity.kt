package com.roundel.smarthome.ui.activity

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.github.ivbaranov.mli.MaterialLetterIcon
import com.mikepenz.materialdrawer.AccountHeaderBuilder
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.ProfileDrawerItem
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

        val icon = MaterialLetterIcon.Builder(this).create()

        val accountHeader = AccountHeaderBuilder()
                .withActivity(this)
                .addProfiles(
                        ProfileDrawerItem().withName("Krzysztof Zdulski")
                                .withEmail("roundel").withIcon(GenericUserIconDrawable("K")))
                .withSelectionListEnabled(false)
                .withHeaderBackground(getDrawable(R.drawable.bg_smart_devices_drawable).apply {
                    setTint(ColorUtils.MATERIAL900.random(0))
                })
                .build()

        DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(accountHeader)
                .addDrawerItems(
                        PrimaryDrawerItem().withIdentifier(1)
                                .withName("Devices")
                                .withIcon(R.drawable.ic_lightbulb))
                .build()
    }
}
