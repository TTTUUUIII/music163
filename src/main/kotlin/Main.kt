import org.netease.music.MusicEntity
import org.netease.music.Spider
import org.netease.music.conf.FEATURE_OUT
import org.netease.music.net.HttpClient
import org.netease.music.utils.AutoGenerator
import java.io.FileOutputStream
import java.nio.file.Paths
import java.util.*

val spider = Spider(client = HttpClient(cookies = mapOf("MUSIC_U" to System.getenv("MUSIC_U"))))

fun main() {
    val playList = spider.fetchPlayList(9644567719)
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
    sleep()
    val musicList = spider.fetchMusicUrl(musicIds)
    musicList.forEach { music ->
        musicEntities.find {
            it.id == music.id
        }?.let {
            it.url = music.url
            it.bitRate = music.br
            it.type = music.type.lowercase(Locale.getDefault())
//            it.lyric = spider.fetchLyric(music.id)?.lyric
//            sleep()
        }
    }

    AutoGenerator.generateFfmpegScript(musicEntities, Paths.get(FEATURE_OUT), playList?.name)

}

private fun downloadLyric(id: Long) {
    val lyricResponse = spider.fetchLyric(id)
    lyricResponse?.lyric?.let { lyric ->
        FileOutputStream(Paths.get(FEATURE_OUT).resolve("$id.lrc").toFile())
            .use {
                it.write(lyric.lyric.encodeToByteArray())
            }
    }
}

private fun sleep() {
    val random = Random(System.nanoTime())
    Thread.sleep((random.nextFloat() * 4000 + 1000).toLong())
}

