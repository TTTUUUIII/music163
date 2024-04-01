### 关于

这是一个简单的爬虫，用于从网易云音乐上下载免费歌曲。

### 快速开始

#### 1. 首先需要安装`node.js`

```shell
sudo apt install nodejs
```

#### 2. 程序最终会输出一个`shell/bat`脚本，脚本会使用`ffmpeg`下载歌曲并设置元数据，因此还需要安装`ffmpeg`

```shell
sudo apt install ffmpeg
```

#### 3. 在`Main.kt`中可设置登录`Cookie`

#### 4. 在`Main.kt::fetchPlayList`传入需要下载的歌单`ID`后，点击运行即可生成一个下载脚本。

### 注意事项

> 在`Features.kt`中提供了一些可配置的项目，详情见注释。

> 在`Windows`中你可能需要把`resources/core.js`复制到其他地方，然后在`Features.kt`中将`FEATURE_ENCRYPT_PATH`配置为`core.js`的全路径，也许才能工作。