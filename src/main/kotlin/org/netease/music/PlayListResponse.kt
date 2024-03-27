package org.netease.music

import com.google.gson.annotations.SerializedName

data class PlayListResponse(val playlist: PlayList)

data class PlayList(
    val id: Long,
    val name: String,
    val coverImgUrl: String,
    @SerializedName("coverImgId_str")
    val coverImgId: String,
    val userId: Long,
    val createTime: Long,
    val updateTime: Long,
    val trackCount: Int,
    val tracks: List<PlayListItem>
)

data class PlayListItem(
    @SerializedName("name")
    val musicName: String,
    @SerializedName("id")
    val musicId: Long,
    @SerializedName("ar")
    val artists: List<MusicArtist>,
    @SerializedName("al")
    val album: MusicAlbum,
    @SerializedName("h")
    val high: MusicQuality,
    @SerializedName("m")
    val medium: MusicQuality,
    @SerializedName("l")
    val low: MusicQuality

)

data class MusicArtist(
    @SerializedName("name")
    val artistName: String,
    @SerializedName("id")
    val artistId: Long,
)

data class MusicAlbum(
    @SerializedName("id")
    val albumId: Long,
    @SerializedName("name")
    val albumName: String,
    val picUrl: String,
)

data class MusicQuality(
    @SerializedName("br")
    val bitRate: Int,
    val fid: Int,
    val size: Long,
    val vd: Int
)

