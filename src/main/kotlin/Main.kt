import org.netease.music.MusicEntity
import org.netease.music.Spider
import org.netease.music.net.HttpClient
import org.netease.music.utils.Downloader
import java.nio.file.Paths
import java.util.*

val spider = Spider(client = HttpClient(cookies = mapOf("MUSIC_U" to System.getenv("MUSIC_U"))))
const val OUT_DIR = "C:\\Users\\wn123\\Desktop\\media"

fun main() {
    val playList = spider.fetchPlayList(2724514503)
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

    Downloader.generateFfmpegScript(musicEntities, Paths.get(OUT_DIR), playList?.name)
}

