package com.roundel.ledcontrol.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.roundel.ledcontrol.R
import com.roundel.ledcontrol.entity.EspServer
import com.roundel.ledcontrol.entity.EspServerList
import com.roundel.ledcontrol.ui.adapter.EspServerAdapter
import java.net.InetAddress

import kotlinx.android.synthetic.main.activity_server_list.server_list_recyclerview as mRecyclerView

class ServerListActivity : AppCompatActivity() {

    private lateinit var mServerAdapter: EspServerAdapter
    private lateinit var mLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_server_list)

        //TODO Just and example, implement server saving and discovery
        val serverList = EspServerList(
                listOf(EspServer("Michal's LEDs", InetAddress.getByName("192.168.1.130"), true),
                        EspServer("Krzysiek's LEDs", InetAddress.getByName("192.168.1.121"))),
                listOf(EspServer("LED Controller #2", InetAddress.getByName("192.168.1.143"), true),
                        EspServer("Living Room Remote", InetAddress.getByName("192.168.1.150"), true),
                        EspServer("PC LEDs", InetAddress.getByName("192.168.1.167"), true)))
        mServerAdapter = EspServerAdapter(serverList, this)

        mLayoutManager = LinearLayoutManager(this)
        mRecyclerView.layoutManager = mLayoutManager
        mRecyclerView.adapter = mServerAdapter
    }
}
