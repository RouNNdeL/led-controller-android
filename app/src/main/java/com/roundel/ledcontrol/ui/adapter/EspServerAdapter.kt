package com.roundel.ledcontrol.ui.adapter

import android.content.Context
import android.content.res.ColorStateList
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.roundel.ledcontrol.R
import com.roundel.ledcontrol.entity.EspServer
import com.roundel.ledcontrol.entity.EspServerList

typealias ItemType = Int

class EspServerAdapter(private var mServerList: EspServerList, private val context: Context) : RecyclerView.Adapter<EspServerAdapter.ViewHolder>() {

    companion object {
        private const val TYPE_ITEM: ItemType = 0;
        private const val TYPE_HEADER: ItemType = 1;
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: ItemType): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(when (viewType) {
            TYPE_ITEM -> R.layout.list_item_server
            TYPE_HEADER -> R.layout.list_header
            else -> throw IllegalArgumentException("Invalid ItemType: $viewType")
        }, parent, false))
    }

    override fun getItemCount(): Int {
        var count = mServerList.rememberedServerList.size + mServerList.discoveredServerList.size
        if (mServerList.rememberedServerList.isNotEmpty())
            count++
        if (mServerList.discoveredServerList.isNotEmpty())
            count++
        return count
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var p = position
        if (mServerList.rememberedServerList.isNotEmpty()) {
            if (p == 0) {
                holder.itemView.findViewById<TextView>(R.id.list_text_header).text = "Remembered devices"
                return
            } else if (p - 1 < mServerList.rememberedServerList.size) {
                val divider = mServerList.discoveredServerList.isNotEmpty() &&
                        p == mServerList.rememberedServerList.size

                fillEspView(mServerList.rememberedServerList[p - 1], holder.itemView, divider); return
            }
            p -= mServerList.rememberedServerList.size + 1
        }
        if (mServerList.discoveredServerList.isNotEmpty()) {
            if (p == 0) {
                holder.itemView.findViewById<TextView>(R.id.list_text_header).text = "Discovered devices"
            } else if (p - 1 < mServerList.discoveredServerList.size) {
                fillEspView(mServerList.discoveredServerList[p - 1], holder.itemView)
            }
        }
    }

    override fun getItemViewType(position: Int): ItemType {
        var p = position
        if (mServerList.rememberedServerList.isNotEmpty()) {
            if (p == 0)
                return TYPE_HEADER
            else if (p - 1 < mServerList.rememberedServerList.size)
                return TYPE_ITEM
            p -= mServerList.rememberedServerList.size + 1
        }
        if (mServerList.discoveredServerList.isNotEmpty()) {
            if (p == 0)
                return TYPE_HEADER
            else if (p - 1 < mServerList.discoveredServerList.size)
                return TYPE_ITEM
        }
        throw IllegalStateException("Calling getItemViewType with empty mServerList")
    }

    fun notifyDiscoveredServerChanged(position: Int) {
        super.notifyItemChanged(getDiscoveredServerPosition(position))
    }

    fun notifyDiscoveredServerInserted(position: Int) {
        super.notifyItemInserted(getDiscoveredServerPosition(position))
    }

    fun notifyRememberedServerChanged(position: Int) {
        super.notifyItemChanged(getRememberedServerPosition(position))
    }

    fun notifyRememberedServerInserted(position: Int) {
        super.notifyItemInserted(getRememberedServerPosition(position))
    }

    private fun getDiscoveredServerPosition(position: Int): Int {
        return position + 1 +
                mServerList.rememberedServerList.size +
                if (mServerList.rememberedServerList.isNotEmpty()) 1 else 0
    }

    private fun getRememberedServerPosition(position: Int): Int {
        return position + 1
    }

    private fun fillEspView(server: EspServer, v: View, divider: Boolean = false) {
        v.findViewById<TextView>(R.id.list_text_primary).text =
                server.name
        v.findViewById<TextView>(R.id.list_text_secondary).text =
                server.ip.hostAddress
        v.findViewById<View>(R.id.list_server_status_icon).backgroundTintList =
                ColorStateList.valueOf(if (server.online)
                    context.getColor(R.color.color_success) else
                    context.getColor(R.color.color_failure))
        v.findViewById<View>(R.id.list_divider_bottom).visibility =
                if (divider) View.VISIBLE else View.GONE
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}