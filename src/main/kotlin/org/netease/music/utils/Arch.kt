package org.netease.music.utils

val OS = System.getProperty("os.name").lowercase()

val WIN = OS.contains("windows")

val LB = if (WIN) "\r\n" else "\n"