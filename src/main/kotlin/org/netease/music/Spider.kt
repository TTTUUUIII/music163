package org.netease.music

import com.google.gson.Gson
import org.netease.music.net.API_PLAY_LIST
import org.netease.music.net.API_SONG
import org.netease.music.net.API_SONG_LYRIC
import org.netease.music.net.HttpClient
import org.netease.music.utils.Resources
import org.openjdk.nashorn.api.scripting.ScriptObjectMirror

class Spider(val client: HttpClient = HttpClient()) {

    private val gson = Gson()
    private val engine = JSEngine().apply {
        compile(Resources.readString("crypto-js.js"))
        compile(Resources.readString("main.js"))
    }

    /**
     * 根据歌曲ID获取歌词
     */
    fun fetchLyric(id: Long): LyricResponse? {
        val requestBody = mapOf(
            "id" to id,
            "lv" to -1,
            "tv" to -1,
            "csrf_token" to ""
        ).let {
            encrypt(gson.toJson(it))
        }

        if (requestBody.isNotEmpty()) {
            val response = client.doPost(API_SONG_LYRIC, requestBody)
            if (response.code == 200) {
                response.body?.string()
                    ?.let {
                        val lyricResponse = gson.fromJson(it, LyricResponse::class.java)
                        return lyricResponse;
                    }
            }
        }
        return null
    }

    /**
     * 根据歌单ID获取歌单信息
     */
    fun fetchPlayList(id: Long): PlayList? {
        val requestBody = mapOf(
            "id" to id,
            "offset" to 0,
            "total" to true,
            "limit" to 1000,
            "n" to 1000,
            "csrf_token" to ""
        ).let {
            encrypt(gson.toJson(it))
        }
        if (requestBody.isNotEmpty()) {
            val response = client.doPost(API_PLAY_LIST, requestBody)
            if (response.code == 200) {
                val playListResponse = response.body?.string()
                    ?.let {
                        gson.fromJson(it, PlayListResponse::class.java)
                    }
                return playListResponse?.playlist
            }
        } else {
            System.err.println("Unable request play list.")
        }
        return null
    }

    /**
     * 根据歌曲ID获取音乐链接
     */
    fun fetchMusicUrl(ids: LongArray): List<Music> {
        val collector = mutableListOf<Music>()
        val requestBody = mapOf(
            "ids" to ids,
            "level" to "standard",
            "encodeType" to "ac3",
            "csrf_token" to "c7e1c88fbff88d2a5897e3c5e22af657",
        ).let {
            encrypt(gson.toJson(it))
        }
       if (requestBody.isNotEmpty()) {
           val response = client.doPost(API_SONG, requestBody)
           if (response.code == 200) {
               response.body?.string()
                   ?.run {
                       val musicResponse = gson.fromJson(this, MusicResponse::class.java)
                       collector.addAll(musicResponse.data)
                   }
           }
       }
        return collector
    }

    private fun encrypt(content: String): Map<String, String> {
        val map = mutableMapOf<String, String>()
        val obj = engine.call(
            "encrypt",
            content
        ) as? ScriptObjectMirror

        obj?.let {
            it.forEach {k, v ->
                if (k == "params" || k == "encSecKey") {
                    map[k] = v as String
                }
            }
        }
        return map;
    }
}