package reclamaciones.libro.com.libroreclamaciones.presentation.register.view_three;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindFont;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import reclamaciones.libro.com.libroreclamaciones.R;
import reclamaciones.libro.com.libroreclamaciones.presentation.claim.ClaimActivity;
import reclamaciones.libro.com.libroreclamaciones.presentation.login.LoginActivity;
import reclamaciones.libro.com.libroreclamaciones.presentation.register.view_one.RegisterActivity;
import reclamaciones.libro.com.libroreclamaciones.presentation.register.view_two.RegisterActivity2;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
    private String register_gender;
    private boolean flag;
    private Context context;
    private ProgressDialog mProgressDialog;
    private AlertDialog.Builder builder;
    private AlertDialog.Builder builderSuccess;
    private RegisterPresenter3 presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register3);
        context = this;
        ButterKnife.bind(this);

        if (presenter == null){
            presenter = new RegisterPresenter3(context);
        }

        Intent intent = getIntent();

        register_name = intent.getStringExtra("register_name");
        register_last_name = intent.getStringExtra("register_last_name");
        register_email = intent.getStringExtra("register_email");
        register_ocupation = intent.getStringExtra("spinner_ocupation");
        register_grade_level = intent.getStringExtra("spinner_grade_level");
        register_district = intent.getStringExtra("spinner_district");
        register_gender = intent.getStringExtra("register_gender");

        mProgressDialog = new ProgressDialog(context,R.style.MyProgressDialogTheme);
        mProgressDialog.setMessage(getText(R.string.default_loading_text));
        mProgressDialog.setCancelable(false);

        builder = new AlertDialog.Builder(this);
        builder.setTitle("Aviso");
        builder.setMessage("Al parecer este dni ya existe.Desea abrir un reclamo?");
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        launchClaimsLayout(register_name,register_last_name,register_email,register_gender,register_district,register_ocupation,register_grade_level,register_dni.getText().toString(),register_password.getText().toString());
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };
        builder.setPositiveButton("Si",dialogClickListener);
        builder.setNegativeButton("No",dialogClickListener);

        builderSuccess = new AlertDialog.Builder(this);
        builderSuccess.setTitle("Usuario creado");
        builderSuccess.setMessage("En estos momentos se está evaluando la confirmación de su cuenta");
        DialogInterface.OnClickListener dialogClickListenerSuccess = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        launchNextLayout();
                        break;
                }
            }
        };
        builderSuccess.setPositiveButton("OK",dialogClickListenerSuccess);

    }


    @Override
    protected void onResume() {
        super.onResume();
        getPresenter().onViewAttach(RegisterActivity3.this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        hideLoadingDialog();
        getPresenter().onViewDettach();
    }

    public RegisterPresenter3 getPresenter(){
        return this.presenter;
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
            getPresenter().register(register_name,register_last_name,register_email,register_gender,Integer.parseInt(register_district),register_ocupation,register_grade_level,register_dni.getText().toString(),register_password.getText().toString());
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
        if (register_password.length()<5){
            register_password.requestFocus();
            label_register_password.setErrorEnabled(true);
            label_register_password.setError(getResources().getString(R.string.required));
            flag = false;
        }
    }

    public void validateConfirmPassword(){
        if (register_confirm_password.length()<5){
            register_confirm_password.requestFocus();
            label_register_confirmation_password.setErrorEnabled(true);
            label_register_confirmation_password.setError(getResources().getString(R.string.required));
            flag = false;
        }
    }

    public void validateEqualsPassword(){
        if (!register_confirm_password.getText().toString().equals(register_password.getText().toString())){
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

    @Override
    public void launchClaimsLayout(String name,String last_name,String email,String gender,String district, String ocupation, String grade_level, String dni, String password) {
        Intent intent = new Intent(getApplicationContext(),ClaimActivity.class);
        intent.putExtra("register_name",""+name);
        intent.putExtra("register_last_name",""+last_name);
        intent.putExtra("register_email",""+email);
        intent.putExtra("register_gender",""+gender);
        intent.putExtra("Register_district",""+district);
        intent.putExtra("register_ocupation",""+ocupation);
        intent.putExtra("register_grade_level",""+grade_level);
        intent.putExtra("register_dni",""+dni);
        intent.putExtra("register_password",""+password);
        startActivity(intent);
    }

    @Override
    public void showDialogClaim() {
        builder.show();
    }

    @Override
    public void showDialogSuccess() {
        builderSuccess.show();
    }

    @Override
    public void showSnackBarErrorCreated() {
        Snackbar.make(getWindow().getDecorView().getRootView(),getResources().getString(R.string.created_error),Snackbar.LENGTH_LONG);
    }

    @Override
    public void showConnectionError() {
        Snackbar.make(getWindow().getDecorView().getRootView(),getResources().getString(R.string.error_connect),Snackbar.LENGTH_LONG);
    }

    @Override
    public void showLoadingDialog() {
        mProgressDialog.show();
    }

    @Override
    public void hideLoadingDialog() {
        mProgressDialog.hide();
    }

    @OnClick(R.id.btn_register)
    public void OnViewClicked(View view){
        registerUser();
    }
}
