package org.netease.music;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.openjdk.nashorn.api.scripting.NashornScriptEngineFactory;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

class JSEngine {
    private final ScriptEngine engine;

    public JSEngine() {
        this(new String[0]);
    }

    public JSEngine(@NotNull String ...libs) {
        engine = new NashornScriptEngineFactory().getScriptEngine("--language=es6");
        for (String url : libs) {
            include(url);
        }
    }

    public void include(@NotNull URL httpUrl) {
        try {
            engine.eval(new InputStreamReader(httpUrl.openStream()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void include(@NotNull String httpUrl) {
        try {
            include(new URL(httpUrl));
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public void compile(@NotNull String js) {
        try {
            engine.eval(js);
        } catch (ScriptException e) {
            throw new RuntimeException(e);
        }
    }

    public @Nullable Object call(@NotNull String func, Object ...args) {
        Invocable ctx = (Invocable) engine;
        try {
            return ctx.invokeFunction(func, args);
        } catch (Exception e) {
            return null;
        }
    }
}
