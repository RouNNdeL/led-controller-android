package com.roundel.ledcontrol.net

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.roundel.ledcontrol.entity.EspServer

import org.json.JSONException
import org.json.JSONObject

import java.io.IOException
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.net.InterfaceAddress
import java.net.NetworkInterface
import java.net.SocketException
import java.nio.charset.Charset
import java.util.Enumeration
import java.util.Objects

/**
 * Created by Krzysiek on 2017-01-29. Adapted from https://github.com/RouNNdeL/csgo-dashboard-app/
 */

/**
 * This thread is used to send a UDP broadcast and detect servers running on the local network
 */
class ServerDiscoveryThread : Thread() {

    companion object {
        private val TAG = ServerDiscoveryThread::class.java.simpleName
        private const val DISCOVERY_MESSAGE = "ROUNDEL_IOT_DISCOVERY"
        private const val DISCOVERY_RESPONSE = "ROUNDEL_IOT_RESPONSE"
    }

    private lateinit var socket: DatagramSocket

    /**
     * @param listener a [ServerDiscoveryListener] that notifies about the process
     */
    var listener: ServerDiscoveryListener? = null
    var discoveryTimeout = 500

    override fun run() {
        // Find the server using UDP broadcast
        try {
            //Open a random port to send the package
            socket = DatagramSocket()
            socket.broadcast = true

            listener?.onSocketOpened()

            val sendData = DISCOVERY_MESSAGE.toByteArray(Charset.defaultCharset())

            //Try the 255.255.255.255 first
            try {
                val sendPacket = DatagramPacket(sendData, sendData.size, InetAddress.getByName("255.255.255.255"), 8888)
                socket.send(sendPacket)
            } catch (e: Exception) {
            }

            // Broadcast the message over all the network interfaces
            val interfaces = NetworkInterface.getNetworkInterfaces()
            while (interfaces.hasMoreElements()) {
                val networkInterface = interfaces.nextElement() as NetworkInterface

                if (networkInterface.isLoopback || !networkInterface.isUp) {
                    continue // Don't want to broadcast to the loopback interface
                }

                for (interfaceAddress in networkInterface.interfaceAddresses) {
                    val broadcast = interfaceAddress.broadcast ?: continue

                    // Send the broadcast package!
                    try {
                        val sendPacket = DatagramPacket(sendData, sendData.size, broadcast, 8888)
                        socket.send(sendPacket)
                    } catch (e: Exception) {
                    }

                }
            }

            Handler(Looper.getMainLooper()).postDelayed({ socket.close() }, discoveryTimeout.toLong())

            //Wait for a response
            while (true) {
                val recvBuf = ByteArray(1024)
                val receivePacket = DatagramPacket(recvBuf, recvBuf.size)
                socket.receive(receivePacket)

                //We have a response
                Log.i(TAG, "Broadcast response from " + receivePacket.address.hostName + ": " + receivePacket.address.hostAddress + ":" + receivePacket.port)

                //Check if the message is correct
                val message = String(receivePacket.data, Charset.defaultCharset()).trim()

                Log.i(TAG, "Message: $message")
                try {
                    val response = JSONObject(message
                    if (response.getString("message") == DISCOVERY_RESPONSE) {
                        val name = response.getString("name")
                        val hostAddress = receivePacket.address

                        Log.i(TAG, "New server \"$name\"at:$hostAddress")

                        listener?.onServerFound(EspServer(name, hostAddress, true))
                    }
                } catch (e: JSONException) {
                    Log.e(TAG, e.toString())
                    e.printStackTrace()
                }

            }
        } catch (e: SocketException) {
            listener?.onSocketClosed()
        } catch (e: IOException) {
            e.printStackTrace()
            Log.e(TAG, e.toString())
        }

    }

    interface ServerDiscoveryListener {
        fun onServerFound(server: EspServer)

        fun onSocketClosed()

        fun onSocketOpened()
    }
}