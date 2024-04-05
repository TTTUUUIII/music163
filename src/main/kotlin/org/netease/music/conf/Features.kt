package org.netease.music.conf

import org.netease.music.utils.WIN


/*如果ffmpeg没有被添加进环境变量，那么需要指定其全路径*/
const val FEATURE_FFMPEG_PATH = "ffmpeg"

/*下载保存目录*/
val FEATURE_OUT = if (WIN) {
    "C:\\Users\\wn123\\Desktop\\Media"
} else {
    "/home/deliu/Desktop/Media"
}