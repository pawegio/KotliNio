package com.pawegio.kotlinio.example1

import com.pawegio.kotlinio.log
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
    val channel = SocketChannel.open(address)

    log("Connecting to server on port ${address.port}")

    listOf("Facebook", "Twitter", "IBM", "Google", "Crunchify", "Close").forEach {
        val message = it.toByteArray()
        val buffer = ByteBuffer.wrap(message)
        channel.write(buffer)

        log("Sending: $it")
        buffer.clear()

        Thread.sleep(2000)
    }
    channel.close()
}