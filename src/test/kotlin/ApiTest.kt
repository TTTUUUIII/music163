import org.junit.jupiter.api.Test
import org.netease.music.Spider
import kotlin.test.assertTrue

class ApiTest {
    private val spider = Spider()
    @Test
    fun testFetchMusicUrl() {
        val musicList = spider.fetchMusicUrl(longArrayOf(484278264))
        assertTrue(musicList.isNotEmpty())
    }

    @Test
    fun testFetchPlayList() {
        val playList = spider.fetchPlayList(6635302339)
        assertTrue(playList != null)
    }

    @Test
    fun testFetchLyric() {
        val lyric = spider.fetchLyric(1335835527)
        assertTrue(lyric != null)
    }
}