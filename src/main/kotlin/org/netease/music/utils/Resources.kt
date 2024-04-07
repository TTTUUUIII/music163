package org.netease.music.utils

val OS = System.getProperty("os.name").lowercase()

val WIN = OS.contains("windows")

val LB = if (WIN) "\r\n" else "\n"

class Resources {
    companion object {
        fun readString(fn: String): String {
            Companion::class.java.classLoader.getResource(fn)?.let {
                return it.openStream().bufferedReader(Charsets.UTF_8)
                    .readText()
            }
            return ""
        }
    }
}