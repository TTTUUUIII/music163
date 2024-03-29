package org.netease.music.conf

/*下载歌曲时是否自动下载歌词*/
const val FEATURE_DOWNLOAD_LYRIC = false

/*用于加密的JS脚本全路径，若为空则使用resource中的默认脚本*/
const val FEATURE_ENCRYPT_PATH = ""

/*如果NodeJs没有被添加进环境变量，那么需要指定其全路径*/
const val FEATURE_NODE_JS_PATH = "node"

/*如果ffmpeg没有被添加进环境变量，那么需要指定其全路径*/
const val FEATURE_FFMPEG_PATH = "ffmpeg"

/*下载保存目录*/
const val FEATURE_OUT = "/home/deliu/Desktop/Music"