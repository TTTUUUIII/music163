import org.netease.music.MusicEntity
import org.netease.music.Spider
import org.netease.Context.Companion.OUT_PATH
import org.netease.music.net.HttpClient
import org.netease.music.utils.AutoGenerator
import org.netease.music.utils.warning
import java.io.FileOutputStream
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

private class Application {
    fun launch(args: Array<String>) {
        var index = 0
        while (index < args.size) {
            when(args[index]) {
                "--music" -> {
                    downloadMusic(args[++index].toLong())
                }
                "-m" -> {
                    downloadMusic(args[++index].toLong())
                }
                "--playlist" -> {
                    downloadPlayList(args[++index].toLong())
                }
                "-l" -> {
                    downloadPlayList(args[++index].toLong())
                }
                "--lyric" -> {
                    downloadLyric(args[++index].toLong())
                }
            }
            index++
        }
    }

    private fun downloadPlayList(listId: Long) {
        val playList = spider.fetchPlayList(listId)
        val musicEntities = mutableListOf<MusicEntity>()
        playList?.tracks?.forEach { item ->
            musicEntities.add(
                MusicEntity(
                    item.musicId,
                    item.musicName,
                    item.artists,
                    item.album,
                    listOf(item.high, item.medium, item.low)
                )
            )
        }
        val musicIds = musicEntities.map {
            it.id
        }.toLongArray()
        val musicList = spider.fetchMusicUrl(musicIds)
        musicList.forEach { music ->
            musicEntities.find {
                it.id == music.id
            }?.let {
                it.url = music.url
                it.bitRate = music.br
                it.type = music.type.lowercase(Locale.getDefault())
            }
        }

        AutoGenerator.generateFfmpegScript(musicEntities, Paths.get(OUT_PATH), playList?.name)
    }

    private fun downloadMusic(musicId: Long) {
        val musicList = spider.fetchMusicUrl(longArrayOf(musicId))
        musicList.firstOrNull()?.let { music ->
            val response = httpClient.doGet(music.url)
            if (response.code == 200) {
                response.body?.let {
                    Files.copy(it.byteStream(), Paths.get(OUT_PATH).resolve("$musicId.${music.type.lowercase()}"))
                }
            }
        }
    }

    private fun downloadLyric(musicId: Long) {
        val lyricResponse = spider.fetchLyric(musicId)
        lyricResponse?.lyric?.let { lyric ->
            FileOutputStream(Paths.get(OUT_PATH).resolve("$musicId.lrc").toFile())
                .use {
                    it.write(lyric.lyric.encodeToByteArray())
                }
        }
    }

    companion object {
        val httpClient = HttpClient(cookies = mapOf("MUSIC_U" to System.getenv("MUSIC_U").also {
            if (it.isEmpty()) {
                warning("Will visit the target website as a guest because the \"MUSIC_U\" environment variable is not set.")
            }
        }))
        val spider = Spider(client = httpClient)
    }
}

fun main(args: Array<String>) {
    Application().launch(args)
}

