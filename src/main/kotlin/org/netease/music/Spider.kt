package org.netease.music

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.netease.music.net.API_PLAY_LIST
import org.netease.music.net.API_SONG
import org.netease.music.net.API_SONG_LYRIC
import org.netease.music.net.HttpClient
import org.netease.music.utils.WIN
import java.io.BufferedReader
import java.io.InputStreamReader

val gson = Gson();

private var temp = ""

class Spider(val client: HttpClient = HttpClient(), var js: String = "") {

    /**
     * 根据歌曲ID获取歌词
     */
    fun fetchLyric(id: Long): LyricResponse? {
        val status = mapOf(
            "id" to id,
            "lv" to -1,
            "tv" to -1,
            "limit" to 1000,
            "csrf_token" to "bfc4ad13014f5066cdee4e18aba3e682"
        ).let {
            encrypt(gson.toJson(it))
        }

        if (status) {
            val type = object: TypeToken<Map<String, String>>(){}.type;
            val params = gson.fromJson<Map<String, String>>(temp, type)
            val response = client.doPost(API_SONG_LYRIC, params)
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
        val status = mapOf(
            "id" to id,
            "offset" to 0,
            "total" to true,
            "limit" to 1000,
            "n" to 1000,
            "csrf_token" to "bfc4ad13014f5066cdee4e18aba3e682"
        ).let {
            encrypt(gson.toJson(it))
        }
        if (status) {
            val type = object: TypeToken<Map<String, String>>(){}.type;
            val params = gson.fromJson<Map<String, String>>(temp, type)
            val response = client.doPost(API_PLAY_LIST, params)
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
        val status = mapOf(
            "ids" to ids,
            "level" to "standard",
            "encodeType" to "ac3",
            "csrf_token" to "c7e1c88fbff88d2a5897e3c5e22af657",
        ).let {
            encrypt(gson.toJson(it))
        }
        if (status) {
            val type = object: TypeToken<Map<String, String>>(){}.type;
            val params = gson.fromJson<Map<String, String>>(temp, type)
            val response = client.doPost(API_SONG, params)
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

    private fun encrypt(content: String): Boolean {
        if (js.isEmpty()) js = JS_PATH
        (if (WIN) content.replace("\"", "\\\"") else content)
            .let {
                val runtime = Runtime.getRuntime()
                val exec = runtime.exec("node $js $content")
                exec.waitFor()
                val status = exec.exitValue()
                BufferedReader(InputStreamReader(if (status == 0) exec.inputStream else exec.errorStream))
                    .use {
                        temp = it.readText()
                    }
                return status == 0
            }
    }

    companion object {
        val JS_PATH = Companion::class.java.classLoader.getResource("core.js")!!.path
    }
}