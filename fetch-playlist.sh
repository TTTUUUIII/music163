#!/bin/sh

#This file is automatically generated and relies on ffmpeg
#see: https://ffmpeg.org/
#about: https://github.com/TTTUUUIII

mkdir -p '-呀哈呦喜欢的音乐' && cd '-呀哈呦喜欢的音乐'
ffmpeg -i 'http://m10.music.126.net/20241014141522/fa301e56d808e8b167357972835bdb7e/ymusic/e94d/35c7/10fb/2c2f1c72c8d2fdadbc7b1ef5cf56b54f.mp3' -i 'http://p1.music.126.net/0I_RK7PsmyjbdPVmQ96SjQ==/109951169869394319.jpg' -metadata title='鳥の詩' -metadata artist='Lia' -metadata album='AIR ORIGINAL SOUNDTRACK' -metadata album_artist='AIR ORIGINAL SOUNDTRACK' -map 0 -c:a copy -map 1 -c:v mjpeg -id3v2_version 3 '鳥の詩-Lia.mp3'
ffmpeg -i 'http://m10.music.126.net/20241014141522/163455a3c0a20aef4ed2fbb8248608a0/ymusic/obj/w5zDlMODwrDDiGjCn8Ky/3700899890/d0f4/c5a6/032a/89838fd6169323c7bc0dd5e8da1e864e.mp3' -i 'http://p1.music.126.net/B0hvMNWSoxW5jMc9kgS5xA==/109951165076735700.jpg' -metadata title='Are You Lost' -metadata artist='Park Bird' -metadata album='Are You Lost' -metadata album_artist='Are You Lost' -map 0 -c:a copy -map 1 -c:v mjpeg -id3v2_version 3 'Are You Lost-Park Bird.mp3'
ffmpeg -i 'http://m701.music.126.net/20241014141522/6f6621f3bf3ab64209a2c5d27e2d62f6/jdymusic/obj/wo3DlMOGwrbDjj7DisKw/34435237552/73b3/84f0/89bb/decaa435291b9624f218661b3364bd60.mp3' -i 'http://p1.music.126.net/lbaNBDgE_fgFjlPIcvHGxA==/109951169410329444.jpg' -metadata title='Forest Mixtape' -metadata artist='Christina Kuong' -metadata album='Tsuki's Odyssey (Original Game Soundtrack) Vol. 2' -metadata album_artist='Tsuki's Odyssey (Original Game Soundtrack) Vol. 2' -map 0 -c:a copy -map 1 -c:v mjpeg -id3v2_version 3 'Forest Mixtape-Christina Kuong.mp3'


exit 0
