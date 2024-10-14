package org.netease.music

import com.google.gson.Gson
import org.netease.music.net.API_PLAY_LIST
import org.netease.music.net.API_SONG
import org.netease.music.net.API_SONG_LYRIC
import org.netease.music.net.HttpClient
import org.netease.music.utils.Resources
import org.netease.music.utils.error
import org.netease.music.utils.warning
import org.openjdk.nashorn.api.scripting.ScriptObjectMirror

class Spider(val client: HttpClient = HttpClient()) {

    private val gson = Gson()
    private val engine = JSEngine().apply {
        include("https://cdn.bootcdn.net/ajax/libs/crypto-js/4.0.0/crypto-js.min.js")
        compile(Resources.readString("main.js"))
    }

    /**
     * Fetch lyric by song id.
     * @param id Long - song id
     * @return LyricResponse?
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
     * Fetch playlist by playlist id.
     * @param id Long - playlist id
     * @return PlayList?
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
            } else {
                error("Unable request play list id=$id. code=${response.code}")
            }
        }
        return null
    }


    /**
     * Fetch songs urls by songs id
     * @param ids LongArray
     * @return List<Music>
     */
    fun fetchMusicUrl(ids: LongArray): List<Music> {
        val collector = mutableListOf<Music>()
        val requestBody = mapOf(
            "ids" to ids,
            "level" to "standard",
            "encodeType" to "ac3",
            "csrf_token" to "",
        ).let {
            encrypt(gson.toJson(it))
        }
       if (requestBody.isNotEmpty()) {
           val response = client.doPost(API_SONG, requestBody)
           if (response.code == 200) {
               response.body?.string()
                   ?.run {
                       val musicResponse = gson.fromJson(this, MusicResponse::class.java)
                       musicResponse.data.forEach {
                           if (it.md5 != null) {
                               collector.add(it)
                           } else {
                               warning("Unable fetch music ${it.id}.")
                           }
                       }
                   }
           } else {
               error("Unable fetch music ids=${ids.contentToString()}. code=${response.code}")
           }
       }
        return collector
    }

    /**
     * Encrypt request body.
     * @param content String
     * @return Map<String, String>
     */
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