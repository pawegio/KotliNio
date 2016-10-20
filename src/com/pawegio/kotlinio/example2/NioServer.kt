package com.pawegio.kotlinio.example2

import com.pawegio.kotlinio.data
import com.pawegio.kotlinio.log
import com.pawegio.kotlinio.toByteBuffer
import java.io.IOException
import java.net.InetSocketAddress
import java.nio.ByteBuffer
import java.nio.channels.SelectionKey
import java.nio.channels.Selector
import java.nio.channels.ServerSocketChannel
import java.nio.channels.SocketChannel

/**
 * @author pawegio
 */
@Throws(IOException::class)
fun main(args: Array<String>) {
    val selector = Selector.open()

    val channel = ServerSocketChannel.open()
    val address = InetSocketAddress("localhost", 1111)
    channel.apply {
        bind(address)
        configureBlocking(false)
        register(selector, channel.validOps())
    }

    while (true) {
        log("Waiting")
        selector.select()

        val keys = selector.selectedKeys()
        val iterator = keys.iterator()
        while (iterator.hasNext()) {
            val key = iterator.next()
            when {
                key.isAcceptable -> accept(key)
                key.isReadable -> read(key)
            }
            iterator.remove()
        }
    }
}

private fun accept(key: SelectionKey) {
    val channel = key.channel() as ServerSocketChannel
    channel.accept().apply {
        configureBlocking(false)
        register(key.selector(), SelectionKey.OP_READ)
        log("Connection accepted: $localAddress")
    }
}

private fun read(key: SelectionKey) {
    val channel = key.channel() as SocketChannel
    val buffer = ByteBuffer.allocate(256)
    channel.read(buffer)
    val message = buffer.data()
    log("Message received: $message")

    if (message == "Close") {
        channel.close()
        log("Connection closed")
    } else {
        Thread.sleep(500)
        write(key, message)
    }
}

fun write(key: SelectionKey, message: String) {
    val channel = key.channel() as SocketChannel
    channel.write("Sent: $message".toByteBuffer())
}