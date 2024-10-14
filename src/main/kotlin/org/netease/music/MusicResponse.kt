package org.netease.music

data class MusicResponse(val data: List<Music>)

data class Music(
    val id: Long,
    val url: String,
    val br: Int,
    val size: String,
    val md5: String?,
    val type: String,
    val encodeType: String
)
