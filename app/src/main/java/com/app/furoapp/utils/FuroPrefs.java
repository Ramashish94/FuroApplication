package com.app.furoapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class FuroPrefs {
    private static final String Furo = "FuroSecurity";
    private static SharedPreferences sharedPreferences;

    public static SharedPreferences getInstance(Context context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(Furo, 0);
        }
        return sharedPreferences;
    }

    public static String getString(Context context, String str) {
        getInstance(context);
        return sharedPreferences.getString(str, "");
    }

    public static void putString(Context context, String str, String str2) {
        getInstance(context);
        sharedPreferences.edit().putString(str, String.valueOf(str2)).commit();
    }

    public static int getInt(Context context, String str, int i) {
        getInstance(context);
        return sharedPreferences.getInt(str, i);
    }

    public static void putInt(Context context, String str, int i) {
        getInstance(context);
        sharedPreferences.edit().putInt(str, i).commit();
    }

    public static boolean getBoolean(Context context, String str) {
        getInstance(context);
        return sharedPreferences.getBoolean(str, false);
    }


    public static float getFloat(Context context, String str) {
        getInstance(context);
        return sharedPreferences.getFloat(str, 0f);
    }

    public static void putBoolean(Context context, String str, boolean z) {
        getInstance(context);
        sharedPreferences.edit().putBoolean(str, z).apply();
    }

    public static void clear(Context context) {
        getInstance(context);
        sharedPreferences.edit().clear().apply();
    }

    public static void putLong(Context context, String str, long j) {
        getInstance(context);
        sharedPreferences.edit().putLong(str, j).apply();
    }

    public static long getLong(Context context, String str) {
        getInstance(context);
        return sharedPreferences.getLong(str, 0);
    }

    public static void putFloat(Context context, String str, float f) {
        getInstance(context);
        sharedPreferences.edit().putFloat(str, f).apply();
    }



}