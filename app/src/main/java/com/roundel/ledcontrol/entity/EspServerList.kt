package com.roundel.ledcontrol.entity


/**
 * @param server [EspServer] a new server to either replace an existing one
 * @return [Int] index of replaced item or -1 if didn't exist
 */
fun MutableList<EspServer>.replaceServer(server: EspServer): Int {
    var replaceIndex = -1
    forEach({
        if (server.ip == it.ip) {
            replaceIndex = this.indexOf(it)
            this.remove(it)
            this.add(replaceIndex, server)
        }
    })
    return replaceIndex
}

class EspServerList(
        val rememberedServerList: MutableList<EspServer> = arrayListOf(),
        val discoveredServerList: MutableList<EspServer> = arrayListOf()
)