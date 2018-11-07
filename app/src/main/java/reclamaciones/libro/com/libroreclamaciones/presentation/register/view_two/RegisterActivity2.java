package reclamaciones.libro.com.libroreclamaciones.presentation.register.view_two;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindFont;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import reclamaciones.libro.com.libroreclamaciones.R;
import reclamaciones.libro.com.libroreclamaciones.presentation.register.view_three.RegisterActivity3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.jaredrummler.materialspinner.MaterialSpinner;

import org.w3c.dom.Text;

public class RegisterActivity2 extends AppCompatActivity implements RegisterContract2.View{

    @BindView(R.id.register_email)
    TextView register_email;
    @BindView(R.id.spinner_district)
    MaterialSpinner spinner_district;
    @BindView(R.id.spinner_ocupation)
    MaterialSpinner  spinner_ocupation;
    @BindView(R.id.spinner_grade_level)
    MaterialSpinner  spinner_grade_level;
    @BindView(R.id.btn_next)
    MaterialButton btn_next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        ButterKnife.bind(this);
    }

    @Override
    public void launchNextLayout() {
        Intent intent = new Intent(getApplicationContext(),RegisterActivity3.class);
        startActivity(intent);
    }


    @OnClick(R.id.btn_next)
    public void OnViewClicked(View view){
        launchNextLayout();
    }
}
