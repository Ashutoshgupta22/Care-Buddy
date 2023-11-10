package com.aspark.carebuddy.chat

import android.util.Log
import org.jivesoftware.smack.StanzaListener
import org.jivesoftware.smack.packet.Stanza
import org.jivesoftware.smack.tcp.XMPPTCPConnection
import org.slf4j.LoggerFactory


class StanzaLoggingListener: StanzaListener {

    override fun processStanza(packet: Stanza?) {

        Log.i("StanzaLoggingListener", "processStanza: Sent packet: ${packet?.toXML()}")
//        if (packet.from.equals(connection!!.user)) {
//        }
    }
}
