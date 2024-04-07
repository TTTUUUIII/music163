import org.netease.music.MusicEntity
import org.netease.music.Spider
import org.netease.Context.Companion.OUT_PATH
import org.netease.music.net.HttpClient
import org.netease.music.utils.AutoGenerator
import java.io.FileOutputStream
import java.net.URL
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

val spider = Spider(client = HttpClient(cookies = mapOf("MUSIC_U" to System.getenv("MUSIC_U"))))

fun main(args: Array<String>) {
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
        Files.copy(URL(music.url).openStream(), Paths.get(OUT_PATH).resolve("$musicId.${music.type.lowercase()}"))
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

private fun sleep() {
    val random = Random(System.nanoTime())
    Thread.sleep((random.nextFloat() * 4000 + 1000).toLong())
}

