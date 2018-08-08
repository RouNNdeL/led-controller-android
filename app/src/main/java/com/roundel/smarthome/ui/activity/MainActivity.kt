package com.roundel.smarthome.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.mikepenz.materialdrawer.AccountHeaderBuilder
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.ProfileDrawerItem
import com.roundel.smarthome.R
import com.roundel.smarthome.bind
import com.roundel.smarthome.ui.view.LightOnOffButton
import okhttp3.*


class MainActivity : AppCompatActivity() {

    val lightButton: LightOnOffButton by bind(R.id.device_state_button)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.device_simple_light)

        val accountHeader = AccountHeaderBuilder().withActivity(this).addProfiles(
                ProfileDrawerItem().withName("Krzysztof Zdulski").withEmail("rounndel")).build()

        DrawerBuilder().withActivity(this).withAccountHeader(accountHeader).addDrawerItems(
                PrimaryDrawerItem().withIdentifier(1).withName("Devices").withIcon(R.drawable.ic_lightbulb)
        ).build()

        lightButton.onStatusChangedListener = object : LightOnOffButton.OnStatusChangedListener {
            override fun onStatusChanged(status: Boolean) {

                //TODO: Temporary demo
                val client = OkHttpClient()


                val body = RequestBody.create(MediaType.parse("text/plain; charset=utf-8"), "ff${if (status) "01" else "00"}*")
                val request = Request.Builder()
                        .url("http://192.168.1.11/globals")
                        .put(body)
                        .build()
                Thread(Runnable {
                    val response = client.newCall(request).execute()
                    Log.d("TAG", response.message())
                }).start()

            }
        }
    }
}
