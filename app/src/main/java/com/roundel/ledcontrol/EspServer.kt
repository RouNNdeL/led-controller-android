package com.roundel.ledcontrol

import android.content.Context
import android.support.annotation.NonNull
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import java.net.InetAddress

class EspServer(val name: String, val ip: InetAddress) {
    companion object {
        @JvmField
        val TAG: String = this::class.java.simpleName;
    }

    fun sendColor(context: Context, color: Int) {
        val queue = Volley.newRequestQueue(context)
        val url = this.ip.toString() + "/color"
        val body = color.toString(16).substring(2);
        val request = object : StringRequest(Request.Method.PUT, url,
                Response.Listener<String> { Log.d(TAG, it) },
                Response.ErrorListener { Log.d(TAG, it.toString()) }) {
            override fun getBody(): ByteArray {
                return body.toByteArray();
            }
        }

        queue.add(request);
    }
}