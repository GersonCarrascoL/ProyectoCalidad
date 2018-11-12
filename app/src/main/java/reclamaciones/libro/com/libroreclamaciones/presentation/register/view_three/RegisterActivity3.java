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
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterActivity3 extends AppCompatActivity implements RegisterContract3.View{

    @BindView(R.id.label_register_dni)
    TextInputLayout label_register_dni;
    @BindView(R.id.register_dni)
    TextInputEditText register_dni;
    @BindView(R.id.label_register_password)
    TextInputLayout label_register_password;
    @BindView(R.id.register_password)
    TextInputEditText register_password;
    @BindView(R.id.label_register_confirmation_password)
    TextInputLayout label_register_confirmation_password;
    @BindView(R.id.register_confirm_password)
    TextInputEditText register_confirm_password;
    @BindView(R.id.btn_register)
    MaterialButton btn_register;


    private String register_name;
    private String register_last_name;
    private String register_email;
    private String register_ocupation;
    private String register_grade_level;
    private String register_district;
    private boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register3);

        ButterKnife.bind(this);

        Intent intent = getIntent();

        register_name = intent.getStringExtra("register_name");
        register_last_name = intent.getStringExtra("register_last_name");
        register_email = intent.getStringExtra("register_email");
        register_ocupation = intent.getStringExtra("spinner_ocupation");
        register_grade_level = intent.getStringExtra("spinner_grade_level");
        register_district = intent.getStringExtra("spinner_district");
    }

    public void registerUser(){
        label_register_dni.setError(null);
        label_register_password.setError(null);
        label_register_confirmation_password.setError(null);
        flag = true;

        validateDNI();
        validatePassword();
        validateConfirmPassword();
        validateEqualsPassword();

        if (flag){
            launchNextLayout();
        }else{
            Snackbar.make(getWindow().getDecorView().getRootView(),getResources().getString(R.string.validate_form),Snackbar.LENGTH_LONG).show();
        }
    }
    public void validateDNI(){
        if (register_dni.length()<=0){
            register_dni.requestFocus();
            label_register_dni.setErrorEnabled(true);
            label_register_dni.setError(getResources().getString(R.string.required));
            flag = false;
        }
    }

    public void validatePassword(){
        if (register_password.length()<=0){
            register_password.requestFocus();
            label_register_password.setErrorEnabled(true);
            label_register_password.setError(getResources().getString(R.string.required));
            flag = false;
        }
    }

    public void validateConfirmPassword(){
        if (register_confirm_password.length()<=0){
            register_confirm_password.requestFocus();
            label_register_confirmation_password.setErrorEnabled(true);
            label_register_confirmation_password.setError(getResources().getString(R.string.required));
            flag = false;
        }
    }

    public void validateEqualsPassword(){
        if (!register_confirm_password.getText().equals(register_password.getText())){
            register_confirm_password.requestFocus();
            register_password.requestFocus();
            label_register_confirmation_password.setErrorEnabled(true);
            label_register_confirmation_password.setError(getResources().getString(R.string.password_different));
            flag = false;
        }
    }

    @Override
    public void launchNextLayout() {
        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);

        startActivity(intent);
    }

    @OnClick(R.id.btn_register)
    public void OnViewClicked(View view){
        registerUser();
    }
}
