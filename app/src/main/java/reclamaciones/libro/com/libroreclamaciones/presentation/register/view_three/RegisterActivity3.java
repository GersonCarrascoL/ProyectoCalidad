package reclamaciones.libro.com.libroreclamaciones.presentation.register.view_three;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindFont;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import reclamaciones.libro.com.libroreclamaciones.R;
import reclamaciones.libro.com.libroreclamaciones.presentation.login.LoginActivity;
import reclamaciones.libro.com.libroreclamaciones.presentation.register.view_one.RegisterActivity;
import reclamaciones.libro.com.libroreclamaciones.presentation.register.view_two.RegisterActivity2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class RegisterActivity3 extends AppCompatActivity implements RegisterContract3.View{

    @BindView(R.id.register_dni)
    TextView register_dni;
    @BindView(R.id.register_password)
    TextView register_password;
    @BindView(R.id.register_confirm_password)
    TextView register_confirm_password;
    @BindView(R.id.btn_register)
    MaterialButton btn_register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register3);

        ButterKnife.bind(this);

    }

    @Override
    public void launchNextLayout() {
        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_register)
    public void OnViewClicked(View view){
        launchNextLayout();
    }
}
