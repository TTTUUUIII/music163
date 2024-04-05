### 关于

这是一个简单的爬虫，用于从网易云音乐上下载免费歌曲。

### 快速开始

#### 1. 程序最终会输出一个`shell/bat`脚本，脚本会使用`ffmpeg`下载歌曲并设置元数据，因此还需要安装`ffmpeg`

```shell
sudo apt install ffmpeg
```

#### 2. 在`Main.kt`中可设置登录`Cookie`

#### 3. 在`Features.kt`中可通过`FEATURE_OUT`指定输出目录

#### 4. 在`Main.kt::fetchPlayList`传入需要下载的歌单`ID`后，点击运行即可生成一个下载脚本。

### 注意事项

> 在`Features.kt`中提供了一些可配置的项目，详情见注释。