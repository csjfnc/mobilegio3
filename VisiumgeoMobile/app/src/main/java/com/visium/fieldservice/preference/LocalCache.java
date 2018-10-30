package com.visium.fieldservice.preference;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Andrew Willard (andrewillard@gmail.com)
 */
public class LocalCache {

    private final ConcurrentMap<String, Object> lock = new ConcurrentHashMap<String, Object>();
    private final ConcurrentMap<String, Object> cache = new ConcurrentHashMap<String, Object>();
    private final SharedPreferences settings;

    private static LocalCache instance;

    public static LocalCache get() {
        return instance;
    }

    public static LocalCache init(Application application) {
        LocalCache.instance = new LocalCache(application);
        return LocalCache.instance;
    }

    private LocalCache(Application application) {
        this.settings = application.getSharedPreferences(PreferenceKey.APP_PREFERENCE_NAME,
                Context.MODE_PRIVATE);
    }

    @SuppressWarnings("unchecked")
    public <T> T peekField(String key, Type aClass, T defaultValue) {
        T object = (T) cache.get(key);
        lock.putIfAbsent(key, new Object());

        synchronized (lock.get(key)) {
            if (object == null) {
                String json = settings.getString(key, null);

                if (json != null) {
                    object = new Gson().fromJson(json, aClass);
                } else {
                    object = defaultValue;
                    updateField(key, object);
                }
            }
        }

        return object;
    }

    public boolean contains(String key) {
        return settings.contains(key) && settings.getAll().get(key) != null;
    }

    public void remove(String key) {
        cache.remove(key);
        settings.edit().remove(key).apply();
    }

    public <T> T pullField(String key, Type aClass, T defaultValue) {
        T t = peekField(key, aClass, defaultValue);
        cache.remove(key);
        settings.edit().remove(key).apply();
        return t;
    }

    public <T> void updateField(String key, T object) {
        if (object == null) {
            cache.remove(key);
        } else {
            cache.put(key, object);
        }

        settings.edit().putString(key, (object == null) ? null : new Gson().toJson(object)).apply();
    }

    public <T> void updateField(String key, T object, Type serializationType) {
        if (object == null) {
            cache.remove(key);
        } else {
            cache.put(key, object);
        }

        settings.edit().putString(key, (object == null) ? null : new Gson().toJson(object, serializationType)).apply();
    }

    public void clearAll() {
        cache.clear();
        settings.edit().clear().apply();
    }

}
