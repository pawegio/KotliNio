package com.pawegio.kotlinio.example2

import com.pawegio.kotlinio.log
import com.pawegio.kotlinio.toByteBuffer
import java.io.IOException
import java.net.InetSocketAddress
import java.nio.ByteBuffer
import java.nio.channels.SelectionKey
import java.nio.channels.Selector
import java.nio.channels.SocketChannel

/**
 * @author pawegio
 */
@Throws(IOException::class)
fun main(args: Array<String>) {
    val messages = mutableListOf("Facebook", "Twitter", "IBM", "Google", "Crunchify", "Close")
    val client = Client(messages)
    Thread(client).start()
}

class Client(val messages: MutableList<String>) : Runnable {

    override fun run() {
        val selector = Selector.open()

        val address = InetSocketAddress("localhost", 1111)
        val channel = SocketChannel.open()
        channel.apply {
            configureBlocking(false)
            register(selector, SelectionKey.OP_CONNECT)
            connect(address)
        }

        log("Connecting to server on port ${address.port}")

        while (!Thread.interrupted()) {
            selector.select()

            val keysIterator = selector.selectedKeys().iterator()
            while (keysIterator.hasNext()) {
                val key = keysIterator.next()
                when {
                    key.isConnectable -> {
                        connect(key)
                        Thread.sleep(2000)
                        write(key, messages.removeAt(0))
                    }
                    key.isReadable -> {
                        read(key)
                        if (messages.isNotEmpty()) {
                            Thread.sleep(2000)
                            write(key, messages.removeAt(0))
                        } else {
                            channel.close()
                        }
                    }
                }
                keysIterator.remove()
            }
        }
    }

    private fun connect(key: SelectionKey) {
        val channel = key.channel() as SocketChannel
        if (channel.isConnectionPending) {
            channel.finishConnect()
        }
        log("Connected")
        channel.apply {
            configureBlocking(false)
            register(key.selector(), SelectionKey.OP_READ)
        }
    }

    private fun read(key: SelectionKey) {
        val channel = key.channel() as SocketChannel
        val buffer = ByteBuffer.allocate(512)
        val bytesRead = channel.read(buffer)
        if (bytesRead != -1) {
            val result = String(buffer.array()).trim('\u0000')
            log(result)
        }
    }

    private fun write(key: SelectionKey, message: String) {
        val channel = key.channel() as SocketChannel
        val buffer = message.toByteBuffer()
        channel.write(buffer)
        log("Sending: $message")
        if (messages.isEmpty()) {
            channel.close()
        }
    }
}