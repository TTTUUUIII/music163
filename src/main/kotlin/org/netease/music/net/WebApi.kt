package org.netease.music.net


const val BASE_URL = "https://music.163.com"

/*获取歌曲播放链接*/
const val API_SONG = "$BASE_URL/weapi/song/enhance/player/url/v1?csrf_token=c7e1c88fbff88d2a5897e3c5e22af657"

const val API_SONG_GOLD = "$BASE_URL/song/media/outer/url?id="

/*获取播放列表*/
const val API_PLAY_LIST = "$BASE_URL/weapi/v6/playlist/detail?csrf_token=bfc4ad13014f5066cdee4e18aba3e682"

//{
//    "id": "1335835527",
//    "lv": -1,
//    "tv": -1,
//    "csrf_token": "bfc4ad13014f5066cdee4e18aba3e682"
//}
/*获取歌词*/
const val  API_SONG_LYRIC = "$BASE_URL/weapi/song/lyric?csrf_token=bfc4ad13014f5066cdee4e18aba3e682"