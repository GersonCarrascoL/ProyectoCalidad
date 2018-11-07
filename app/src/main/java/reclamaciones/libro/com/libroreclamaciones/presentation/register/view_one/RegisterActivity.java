package reclamaciones.libro.com.libroreclamaciones.presentation.register.view_one;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import reclamaciones.libro.com.libroreclamaciones.R;
import reclamaciones.libro.com.libroreclamaciones.presentation.register.view_two.RegisterActivity2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class RegisterActivity extends AppCompatActivity implements RegisterContract.View{

    private Toolbar toolbar;

    @BindView(R.id.register_name)
    TextView register_name;
    @BindView(R.id.register_last_name)
    TextView register_last_name;
    @BindView(R.id.btn_next)
    MaterialButton btn_next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ButterKnife.bind(this);

    }


    @Override
    public void launchNextLayout() {
        Intent intent = new Intent(getApplicationContext(),RegisterActivity2.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_next)
    public void OnViewClicked(View view){
        launchNextLayout();
    }
}
