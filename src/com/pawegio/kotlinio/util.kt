package com.pawegio.kotlinio

import java.nio.ByteBuffer

/**
 * @author pawegio
 */

fun String.toByteBuffer(): ByteBuffer = ByteBuffer.wrap(toByteArray())

fun ByteBuffer.data(): String = String(array()).trim('\u0000')