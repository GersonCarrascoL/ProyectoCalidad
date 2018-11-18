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
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterActivity extends AppCompatActivity implements RegisterContract.View{

    @BindView(R.id.label_register_name)
    TextInputLayout label_register_name;
    @BindView(R.id.register_name)
    TextInputEditText register_name;
    @BindView(R.id.label_register_last_name)
    TextInputLayout label_register_last_name;
    @BindView(R.id.register_last_name)
    TextInputEditText register_last_name;
    @BindView(R.id.btn_next)
    MaterialButton btn_next;


    private boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ButterKnife.bind(this);

    }


    @Override
    public void launchNextLayout() {
        Intent intent = new Intent(getApplicationContext(),RegisterActivity2.class);
        intent.putExtra("register_name",""+register_name.getText());
        intent.putExtra("register_last_name",""+register_last_name.getText());
        startActivity(intent);
    }

    private void registerNextStep(){
        label_register_name.setErrorEnabled(false);
        label_register_last_name.setErrorEnabled(false);
        label_register_name.setError(null);
        label_register_last_name.setError(null);
        flag = true;

        validateName();
        validateLastName();

        if (flag){
            launchNextLayout();
        }else{
            Snackbar.make(getWindow().getDecorView().getRootView(),"Tiene campos sin completar",Snackbar.LENGTH_LONG).show();
        }
    }

    public void validateName(){
        if (register_name.length()<=0){
            register_name.requestFocus();
            label_register_name.setErrorEnabled(true);
            label_register_name.setError(getResources().getString(R.string.error_name));
            flag = false;
        }
    }

    public void validateLastName(){
        if (register_last_name.length()<=0){
            register_last_name.requestFocus();
            label_register_last_name.setErrorEnabled(true);
            label_register_last_name.setError(getResources().getString(R.string.error_last_name));
            flag = false;
        }
    }

    @OnClick(R.id.btn_next)
    public void OnViewClicked(View view){
        registerNextStep();
    }
}
