package org.netease.music;

import jdk.nashorn.api.scripting.NashornScriptEngineFactory;
import org.jetbrains.annotations.Nullable;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class JSEngine {
    private final ScriptEngine engine = new NashornScriptEngineFactory().getScriptEngine("--language=es6");

    public JSEngine(String ...scriptsInResource) {
        try {
            for (String script : scriptsInResource) {
                try (InputStream in = getClass().getClassLoader().getResource(script).openStream()){
                    engine.eval(new InputStreamReader(in));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public @Nullable Object call(String func, Object ...args) {
        Invocable ctx = (Invocable) engine;
        try {
            return ctx.invokeFunction(func, args);
        } catch (Exception e) {
            return null;
        }
    }
}
