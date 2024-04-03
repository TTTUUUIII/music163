package org.netease.music;

import org.jetbrains.annotations.Nullable;
import org.openjdk.nashorn.api.scripting.NashornScriptEngineFactory;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptException;

public class JSEngine {
    private final ScriptEngine engine;

    public JSEngine() {
        engine = new NashornScriptEngineFactory().getScriptEngine("--language=es6");
    }

    public void compile(String js) {
        try {
            engine.eval(js);
        } catch (ScriptException e) {
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
