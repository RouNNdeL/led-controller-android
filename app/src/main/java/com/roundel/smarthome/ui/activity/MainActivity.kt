package com.roundel.smarthome.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.mikepenz.materialdrawer.AccountHeaderBuilder
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.ProfileDrawerItem
import com.roundel.smarthome.R


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.device_simple_light)

        val accountHeader = AccountHeaderBuilder().withActivity(this).addProfiles(
                ProfileDrawerItem().withName("Krzysztof Zdulski").withEmail("rounndel")).build()

        DrawerBuilder().withActivity(this).withAccountHeader(accountHeader).addDrawerItems(
                PrimaryDrawerItem().withIdentifier(1).withName("Devices").withIcon(R.drawable.ic_lightbulb)
        ).build()
    }
}
