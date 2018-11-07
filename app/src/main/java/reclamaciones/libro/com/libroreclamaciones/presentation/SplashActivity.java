package reclamaciones.libro.com.libroreclamaciones.presentation;

import androidx.appcompat.app.AppCompatActivity;
import reclamaciones.libro.com.libroreclamaciones.R;
import reclamaciones.libro.com.libroreclamaciones.data.repository.local.SessionManager;
import reclamaciones.libro.com.libroreclamaciones.presentation.login.LoginActivity;
import reclamaciones.libro.com.libroreclamaciones.presentation.main.MainNavigationActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    private static final long SPLASH_SCREEN_DELAY = 3000;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                sessionManager = SessionManager.getInstance(getApplicationContext());

                if (sessionManager.isActive()) {
                    launchActivity(getApplicationContext(), MainNavigationActivity.class);
                } else {
                    launchActivity(getApplicationContext(), LoginActivity.class);
                }
            }
        };

        Timer timer = new Timer();
        timer.schedule(timerTask,SPLASH_SCREEN_DELAY);
    }

    private void launchActivity(Context origin, Class destiny) {
        Intent intent = new Intent().setClass(
                origin, destiny);
        startActivity(intent);
        finish();
    }

}
