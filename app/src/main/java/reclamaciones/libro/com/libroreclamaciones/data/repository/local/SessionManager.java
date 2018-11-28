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
    private static final String KEY_ID_USER = "iduser";
    private static final String KEY_USER_NAME = "username";
    private static final String KEY_EMAIL = "useremail";

    public void setKeyToken(String token){
        editor.putString(KEY_TOKEN,"Bearer "+token);
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

    public void setIdUser(int idUser){
        editor.putInt(KEY_ID_USER,idUser);
        editor.commit();
    }

    public int getIdUser(){
        return preferences.getInt(KEY_ID_USER,0);
    }

    public void setUserName(String userName){
        editor.putString(KEY_USER_NAME,userName);
        editor.commit();
    }

    public String getUserName(){
        return preferences.getString(KEY_USER_NAME,"");
    }

    public void setUserEmail(String userEmail){
        editor.putString(KEY_EMAIL,userEmail);
        editor.commit();
    }

    public String getUserEmail(){
        return preferences.getString(KEY_EMAIL,"");
    }

    public boolean isActive(){
        return preferences.getBoolean(KEY_SESSION,false);
    }
    //END SESSION
}
