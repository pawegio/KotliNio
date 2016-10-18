package com.pawegio.kotlinio

import java.io.IOException
import java.net.InetSocketAddress
import java.nio.ByteBuffer
import java.nio.channels.SocketChannel

/**
 * @author Crunchify
 * @author pawegio
 */
@Throws(IOException::class, InterruptedException::class)
fun main(args: Array<String>) {
    val address = InetSocketAddress("localhost", 1111)
    val client = SocketChannel.open(address)

    log("Connecting to server on port ${address.port}")

    listOf("Facebook", "Twitter", "IBM", "Google", "Crunchify", "Close").forEach {
        val message = it.toByteArray()
        val buffer = ByteBuffer.wrap(message)
        client.write(buffer)

        log("Sending: $it")
        buffer.clear()

        Thread.sleep(2000)
    }
    client.close()
}