import org.netease.music.MusicEntity
import org.netease.music.Spider
import org.netease.music.conf.FEATURE_OUT
import org.netease.music.net.HttpClient
import org.netease.music.utils.AutoGenerator
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
    val musicList = spider.fetchMusicUrl(musicIds)
    Thread.sleep(3000)
    musicList.forEach { music ->
        musicEntities.find {
            it.id == music.id
        }?.let {
            it.url = music.url
            it.bitRate = music.br
            it.type = music.type.lowercase(Locale.getDefault())
        }
    }

    AutoGenerator.generateFfmpegScript(musicEntities, Paths.get(FEATURE_OUT), playList?.name)
//    if (FEATURE_DOWNLOAD_LYRIC) {
//        AutoGenerator.downloadLyric(musicEntities, Paths.get(FEATURE_OUT))
//    }

//    val lyricResponse = spider.fetchLyric(1354489)
//    lyricResponse?.lyric?.lyric.let {lyric ->
//        lyric?.let {
//            FileOutputStream(Paths.get(FEATURE_OUT).resolve("temp.lrc").toFile())
//                .use {out ->
//                    out.write(it.encodeToByteArray())
//                }
//        }
//    }
}

