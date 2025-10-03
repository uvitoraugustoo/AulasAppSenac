package com.vitoraugusto.aulas.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private static final String PREF = "aulas_prefs";
    private final SharedPreferences prefs;

    public SessionManager(Context ctx) {
        prefs = ctx.getSharedPreferences(PREF, Context.MODE_PRIVATE);
    }

    public void salvarUsuarioLogado(int id, String tipo) {
        prefs.edit().putInt("user_id", id).putString("user_tipo", tipo).apply();
    }

    public int getUserId() { return prefs.getInt("user_id", -1); }
    public String getUserTipo() { return prefs.getString("user_tipo", null); }

    public void logout() { prefs.edit().clear().apply(); }
}
