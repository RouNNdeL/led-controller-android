package com.roundel.ledcontrol.entity

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.roundel.ledcontrol.net.ServerDiscoveryThread
import java.net.InetAddress

class EspServer(val name: String, val ip: InetAddress, var online: Boolean = false) {
    companion object {
        private val TAG: String = EspServer::class.java.simpleName
        fun udpScan(listener: ServerDiscoveryThread.ServerDiscoveryListener, timeout: Int){
            val thread = ServerDiscoveryThread()
            thread.listener = listener
            thread.discoveryTimeout = timeout
            thread.start()
        }
    }

    fun sendColor(context: Context, color: Int) {
        val queue = Volley.newRequestQueue(context)
        val url = "http://${this.ip.hostAddress}/color"
        val body = color.toString(16).substring(2);
        val request = object : StringRequest(Request.Method.PUT, url,
                Response.Listener<String> { Log.d(TAG, it); online = true },
                Response.ErrorListener { Log.d(TAG, it.toString()); online = false }){
            override fun getBody(): ByteArray {
                return body.toByteArray();
            }
        }

        queue.add(request);
    }
}