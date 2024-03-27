package org.netease.music.utils

import org.netease.music.MusicEntity
import java.nio.file.Files
import java.nio.file.Path

class Downloader {
    companion object {
        fun generateFfmpegCommand(musicEntities: List<MusicEntity>): String {
            val commands = StringBuilder()
            for (music in musicEntities) {
                if (music.url == null) continue
                val artists = music.artists.joinToString(",") {
                    it.artistName
                }
                val metadata = arrayOf(
                    "-metadata title=\'${music.name}\'",
                    "-metadata artist=\'$artists\'",
                    "-metadata album=\'${music.album.albumName}\'",
                    "-metadata album_artist=\'${music.album.albumName}\'"
                ).joinToString(" ")
                commands.append("ffmpeg -i \'${music.url}\' -i \'${music.album.picUrl}\' $metadata -map 0 -c:a copy -map 1 -c:v mjpeg -id3v2_version 3 \'${music.name}-${artists}.${music.type}\'\n")
            }
            return commands.toString()
        }

        fun generateFfmpegScript(musicEntities: List<MusicEntity>, path: Path) {
            val scriptPath: Path = if (path.toFile().isDirectory) {
                path.resolve("download.sh")
            } else {
                path
            }
            val script = StringBuilder()
            script.append("#!/bin/sh\n\n")
            script.append("#This file is automatically generated and relies on ffmpeg\n")
            script.append("#see: https://ffmpeg.org/\n")
            script.append("#about: https://github.com/TTTUUUIII\n\n")
            script.append("mkdir -p musics && cd musics\n")
            script.append(generateFfmpegCommand(musicEntities))
            script.append("\n\nexit 0\n")
            Files.writeString(scriptPath, script)
            scriptPath.toFile().setExecutable(true, false)
        }
    }
}