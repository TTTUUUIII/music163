package org.netease.music

/**
 * Music Entity
 * @property id Long
 * @property name String
 * @property artists List<MusicArtist>
 * @property album MusicAlbum
 * @property qualities List<MusicQuality>
 * @property bitRate Int
 * @property url String?
 * @property type String?
 * @property lyric Lyric?
 * @constructor
 */
data class MusicEntity(
    val id: Long,
    val name: String,
    val artists: List<MusicArtist>,
    val album: MusicAlbum,
    val qualities: List<MusicQuality>,
    var bitRate: Int = 44100,
    var url: String? = null,
    var type: String? = null,
    var lyric: Lyric? = null
)
