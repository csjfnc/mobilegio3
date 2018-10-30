package com.visium.fieldservice.preference;

import com.google.gson.reflect.TypeToken;
import com.visium.fieldservice.entity.UserProfile;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Andrew Willard (andrewillard@gmail.com)
 */
public class PreferenceHelper {

    private static final LocalCache LOCAL_CACHE = LocalCache.get();
    private static final Type OFFILINE_IDS_TYPE = new TypeToken<Set<String>>() {}.getType();

    public static boolean contains(String key) {
        return LOCAL_CACHE.contains(key);
    }

    public static UserProfile getUserProfile() {
        return LOCAL_CACHE.peekField(PreferenceKey.USER_PROFILE, UserProfile.class, null);
    }

    public static void setUserProfile(UserProfile userProfile) {
        LOCAL_CACHE.updateField(PreferenceKey.USER_PROFILE, userProfile);
    }

    public static String getImei() {
        return LOCAL_CACHE.peekField(PreferenceKey.IMEI, String.class, null);
    }

    public static void setImei(String imei) {
        LOCAL_CACHE.updateField(PreferenceKey.IMEI, imei);
    }

    public static synchronized void setOfflineIds(Set<String> ids) {
        LOCAL_CACHE.updateField(PreferenceKey.OFFLINE_IDS, ids);
    }

    public static synchronized Set<String> getOfflineIds() {
        return LOCAL_CACHE.peekField(PreferenceKey.OFFLINE_IDS, OFFILINE_IDS_TYPE, Collections.synchronizedSet(new HashSet<String>()));
    }

    public static Boolean getGcmTokenStatus() {
        return LOCAL_CACHE.peekField(PreferenceKey.GCM_SENT_TOKEN_TO_SERVER, Boolean.class, Boolean.FALSE);
    }

    public static void setGcmTokenStatus(boolean sent) {
        LOCAL_CACHE.updateField(PreferenceKey.GCM_SENT_TOKEN_TO_SERVER, sent);
    }

    public static String getGcmToken() {
        return LOCAL_CACHE.peekField(PreferenceKey.GCM_TOKEN, String.class, null);
    }

    public static void setGcmToken(String token) {
        LOCAL_CACHE.updateField(PreferenceKey.GCM_TOKEN, token);
    }

}