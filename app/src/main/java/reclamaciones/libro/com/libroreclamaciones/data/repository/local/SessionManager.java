package reclamaciones.libro.com.libroreclamaciones.data.repository.local;

import android.content.Context;
import android.content.SharedPreferences;

import java.security.Key;

public class SessionManager {

    private static final String PREFERENCE_NAME = "Reclamos";
    private int PRIVATE_MODE = 0;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Context context;
    private static SessionManager sessionManager;

    public static SessionManager getInstance(Context context){
        if(sessionManager == null){
            sessionManager = new SessionManager(context);
        }
        return sessionManager;
    }

    private SessionManager(Context context){
        this.context = context;
        preferences = context.getSharedPreferences(PREFERENCE_NAME,PRIVATE_MODE);
        editor = preferences.edit();
    }

    // SESSION
    private static final String KEY_SESSION = "session";
    private static final String KEY_TOKEN = "token";

    public void setKeyToken(String token){
        editor.putString(KEY_TOKEN,"Bearer: "+token);
        editor.commit();
    }

    public String getKeyToken(){
        return preferences.getString(KEY_TOKEN,"");
    }

    public void login(){
        editor.putBoolean(KEY_SESSION,true);
        editor.commit();
    }

    public void logOut(){
        editor.clear();
        editor.putBoolean(KEY_SESSION,false);
        editor.commit();
    }

    public boolean isActive(){
        return preferences.getBoolean(KEY_SESSION,false);
    }
    //END SESSION
}
