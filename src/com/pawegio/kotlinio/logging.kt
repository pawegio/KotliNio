package com.pawegio.kotlinio

import java.time.Instant

/**
 * @author pawegio
 */
fun log(message: String) {
    println("[${Instant.now()}] $message")
}