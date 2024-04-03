package org.netease.music.conf

/*下载歌曲时是否自动下载歌词，暂时不建议用*/
const val FEATURE_DOWNLOAD_LYRIC = false


/*如果ffmpeg没有被添加进环境变量，那么需要指定其全路径*/
const val FEATURE_FFMPEG_PATH = "ffmpeg"

/*下载保存目录*/
const val FEATURE_OUT = "/home/deliu/Desktop/Media"