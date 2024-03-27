package org.netease.music

data class MusicEntity(
    val id: Long,
    val name: String,
    val artists: List<MusicArtist>,
    val album: MusicAlbum,
    val qualities: List<MusicQuality>,
    var bitRate: Int = 44100,
    var url: String? = null,
    var type: String? = null
)
