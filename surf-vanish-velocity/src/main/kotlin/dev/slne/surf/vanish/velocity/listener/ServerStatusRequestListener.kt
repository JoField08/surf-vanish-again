package dev.slne.surf.vanish.velocity.listener

import com.github.retrooper.packetevents.event.PacketListener
import com.github.retrooper.packetevents.event.PacketSendEvent
import com.github.retrooper.packetevents.protocol.packettype.PacketType
import com.github.retrooper.packetevents.wrapper.status.server.WrapperStatusServerResponse

class ServerStatusRequestListener : PacketListener {
    override fun onPacketSend(event: PacketSendEvent) {
        when(event.packetType) {
            PacketType.Status.Server.RESPONSE -> {
                val packet = WrapperStatusServerResponse(event)
            }
        }
    }
}