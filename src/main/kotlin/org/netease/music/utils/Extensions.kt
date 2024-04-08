package org.netease.music.utils

import java.util.*

const val TAG_WARN  =  "[WARNING]"
const val TAG_ERROR =  "[ERROR]"
const val TAG_INFO  =  "[INFO]"

fun <T> T.warning(msg: String) {
    println("$TAG_WARN $msg")
}

fun <T> T.error(msg: String) {
    System.err.println("$TAG_ERROR $msg")
}

fun <T> T.info(msg: String) {
    println("$TAG_INFO $msg")
}

fun <T> T.sleep(min: Long, max: Long) {
    val random = Random(System.nanoTime())
    Thread.sleep((random.nextFloat() * max + min).toLong())
}