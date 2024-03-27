package org.netease.music

import com.google.gson.annotations.SerializedName

data class LyricResponse(
    val sgc: Boolean,
    val sfy: Boolean,
    val qfy: Boolean,
    val transUser: User,
    val lyricUser: User,
    @SerializedName("lrc")
    val lyric: Lyric,
    @SerializedName("tlyric")
    val tLyric: Lyric
)

data class User(
    val id: Long,
    val status: Int,
    val demand: Int,
    val userid: Long,
    val nickname: String,
    val  uptime: Long
)

data class Lyric(
    val version: Int,
    val lyric: String
)