package org.netease.music.net

import okhttp3.*
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable
import org.riversun.okhttp3.OkHttp3CookieHelper

class HttpClient(val cookies: Map<String, String> = mapOf()){

    private val okHttpClient: OkHttpClient

    fun doPost(@NotNull url: String, @Nullable params: Map<String, String> = mapOf()): Response {
        val bodyBuilder = FormBody.Builder()
        params.forEach { (k, v) ->  bodyBuilder.add(k, v)}
        val request = Request.Builder()
            .url(url)
            .post(bodyBuilder.build())
            .build()
        return okHttpClient.newCall(request).execute()
    }

    fun doGet(@NotNull url: String): Response {
        val request = Request.Builder()
            .url(url)
            .get()
            .build()
        return okHttpClient.newCall(request).execute()
    }

    init {
        val cookieJar = OkHttp3CookieHelper()
            .run {
                cookies.forEach {(k, v) ->
                    setCookie(BASE_URL, k, v)
                }
                cookieJar()
            }
        okHttpClient = OkHttpClient.Builder()
            .run {
                cookieJar(cookieJar)
                addInterceptor {
                    val origin = it.request()
                    val newRequest = origin.newBuilder()
                        .addHeader("Origin", BASE_URL)
                        .addHeader("User-Agent", USER_AGENT)
                        .addHeader("Referer", BASE_URL)
                        .build();
                    it.proceed(newRequest);
                }
            }
            .build()
    }

    companion object {
        const val USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36 Edg/122.0.0.0"
    }
}