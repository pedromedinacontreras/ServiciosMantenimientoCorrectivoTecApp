package com.example.usuario.serviciosmantenimientotecapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by usuario on 10/12/16.
 */

public class SessionManager{

        public static final String KEY_ACCESS_TOKEN = "KEY_ACCESS_TOKEN";
        public static final String KEY_USUARIO = "KEY_USUARIO";
        public static final String KEY_CONTRASEÑA = "KEY_CONTRASEÑA";
        private static SharedPreferences pref;
        private Context mContext;


        public SessionManager(Context context) {
            this.mContext = context;
            pref = PreferenceManager.getDefaultSharedPreferences(context);
        }

        public void saveAccessToken(String accessToken) {
            pref.edit().putString(KEY_ACCESS_TOKEN, accessToken).apply();
        }

        public String getAccessToken() {
            String token = pref.getString(KEY_ACCESS_TOKEN, "");
            return token;
        }

    public void saveProfileData(String usuario, String contraseña) {
            pref.edit().putString(KEY_USUARIO, usuario).apply();
            pref.edit().putString(KEY_CONTRASEÑA, contraseña).apply();
        }

    public String getUsuario(){
        return pref.getString(KEY_USUARIO,"");
    }

    public void putUsuario(String usuario){
        pref.edit().putString(KEY_USUARIO, usuario).apply();
    }

    public String getContraseña(){
        return pref.getString(KEY_CONTRASEÑA,"");
    }

    public void putContraseña(String contraseña){
        pref.edit().putString(KEY_CONTRASEÑA, contraseña).apply();
    }
}
