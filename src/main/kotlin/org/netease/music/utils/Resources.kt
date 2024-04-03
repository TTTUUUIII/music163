package org.netease.music.utils

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