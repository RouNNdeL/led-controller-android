package com.roundel.ledcontrol.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.roundel.ledcontrol.R
import com.roundel.ledcontrol.entity.EspServer
import com.roundel.ledcontrol.entity.EspServerList
import com.roundel.ledcontrol.entity.replaceServer
import com.roundel.ledcontrol.net.ServerDiscoveryThread
import com.roundel.ledcontrol.ui.adapter.EspServerAdapter
import java.net.InetAddress

import kotlinx.android.synthetic.main.activity_server_list.server_list_recyclerview as mRecyclerView
import kotlinx.android.synthetic.main.activity_server_list.server_list_swiperefresh as mSwipeRefreshLayout

class ServerListActivity : AppCompatActivity(), ServerDiscoveryThread.ServerDiscoveryListener {

    private lateinit var mServerAdapter: EspServerAdapter
    private lateinit var mLayoutManager: LinearLayoutManager
    private var mDiscoveryTimeout = 2000;
    private val mServerList = EspServerList()

    /*
     * Used to mark some earlier discovered servers as offline
     * if they do not respond to a new discovery request
     */
    private val mNewScanServerList: MutableList<EspServer> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_server_list)

        //TODO Implement saving the servers to favourites
        mServerAdapter = EspServerAdapter(mServerList, this)

        mLayoutManager = LinearLayoutManager(this)
        mRecyclerView.layoutManager = mLayoutManager
        mRecyclerView.adapter = mServerAdapter

        mSwipeRefreshLayout.setOnRefreshListener { EspServer.udpScan(this, mDiscoveryTimeout) }
    }

    override fun onServerFound(server: EspServer) {
        mNewScanServerList.add(server)
        val position = mServerList.discoveredServerList.replaceServer(server)
        if (position == -1)
            mServerList.discoveredServerList.add(server)
        runOnUiThread {
            if (position != -1)
                mServerAdapter.notifyDiscoveredServerChanged(position)
            else
                mServerAdapter.notifyDiscoveredServerInserted(
                        mServerList.discoveredServerList.indexOf(server)
                )
        }

    }

    override fun onSocketClosed() {
        mSwipeRefreshLayout.isRefreshing = false;
        for (server in mServerList.discoveredServerList) {
            if (!mNewScanServerList.contains(server)) {
                server.online = false
                runOnUiThread {
                    mServerAdapter.notifyDiscoveredServerChanged(mServerList.discoveredServerList.indexOf(server))
                }
            }
        }
    }

    override fun onSocketOpened() {
        mNewScanServerList.removeAll(mNewScanServerList)
        mSwipeRefreshLayout.isRefreshing = true;
    }
}
