package reclamaciones.libro.com.libroreclamaciones.presentation.enterprise;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import reclamaciones.libro.com.libroreclamaciones.R;

import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;

public class EnterpriseActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView profile_valoracionGeneral;
    RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enterprise);

        toolbar = findViewById(R.id.toolbar_general);
        ratingBar = findViewById(R.id.ratingBar_general);
        profile_valoracionGeneral = findViewById(R.id.profile_valoracionGeneral);

        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("EMPRESA");


        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                profile_valoracionGeneral.setText(""+rating);
            }
        });
    }



}
