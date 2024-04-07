### 关于

这是一个简单的爬虫，用于从网易云音乐上下载免费歌曲。

### 快速开始

#### 1. 程序最终会输出一个`shell/bat`脚本，脚本会使用`ffmpeg`下载歌曲，因此需要先安装`ffmpeg`

```shell
sudo apt install ffmpeg
```

```shell
# 根据歌曲ID下载歌曲
java -jar music163-1.0.0-all.jar --music 29386030

# 下载指定歌单ID中的歌曲
java -jar music163-1.0.0-all.jar --playlist 5378020178

#根据歌曲ID下载歌词
java -jar music163-1.0.0-all.jar --lyric 29386030
```

### 注意事项

> 1. 请使用`JDK-11`;