package reclamaciones.libro.com.libroreclamaciones.presentation.login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;


import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import reclamaciones.libro.com.libroreclamaciones.R;
import reclamaciones.libro.com.libroreclamaciones.presentation.main.MainNavigationActivity;
import reclamaciones.libro.com.libroreclamaciones.presentation.register.view_one.RegisterActivity;

public class LoginActivity extends AppCompatActivity implements LoginContract.View{

    @BindView(R.id.label_email)
    TextInputLayout tl_email;
    @BindView(R.id.login_email)
    TextInputEditText et_email;
    @BindView(R.id.label_password)
    TextInputLayout tl_password;
    @BindView(R.id.login_password)
    TextInputEditText et_password;
    @BindView(R.id.btn_login)
    MaterialButton btn_login;
    @BindView(R.id.text_register)
    TextView text_register;

    //VARIABLES GLOBAL
    private ProgressDialog mProgressDialog;
    private LoginPresenter presenter;
    private Context context;
    private boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        context = this;

        if (presenter == null){
            presenter = new LoginPresenter(context);
        }

        mProgressDialog = new ProgressDialog(context,R.style.MyProgressDialogTheme);
        mProgressDialog.setMessage(getText(R.string.default_loading_text));
        mProgressDialog.setCancelable(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPresenter().onViewAttach(LoginActivity.this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        hideLoadingDialog();
        getPresenter().onViewDettach();
    }


    public void login(){
        tl_email.setErrorEnabled(false);
        tl_password.setErrorEnabled(false);
        tl_email.setError(null);
        tl_password.setError(null);
        flag = true;

        validatePassword();

        validateEmailEditText();

        if (flag){
            getPresenter().login(et_email.getText().toString(),et_password.getText().toString());
        }else {
            showCompleteLoginFormToast();
        }
    }

    //Get presenter
    private LoginContract.Presenter getPresenter() {
        return presenter;
    }

    public boolean validateEmail(String email){
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void validateEmailEditText() {
        if (!validateEmail(et_email.getText().toString())) {
            et_email.requestFocus();
            tl_email.setErrorEnabled(true);
            tl_email.setError(getResources().getString(R.string.email_required));
            flag = false;
        }
    }

    private void validatePassword(){
        if (et_password.length()<5){
            et_password.requestFocus();
            tl_password.setErrorEnabled(true);
            tl_password.setError(getResources().getString(R.string.password_invalid));
            flag =false;
        }
    }

    @Override
    public void showLoadingDialog() {
        mProgressDialog.show();
    }

    @Override
    public void hideLoadingDialog() {
        mProgressDialog.hide();
    }

    /*
    UI messages methods
    */

    @Override
    public void showSuccessToast() {
        Snackbar.make(getWindow().getDecorView().getRootView(),context.getResources().getString(R.string.success),Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showWrongCredentialsToast() {
        Snackbar.make(getWindow().getDecorView().getRootView(),context.getResources().getString(R.string.email_password_ws_error),Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showUserDeleteToast() {
        Snackbar.make(getWindow().getDecorView().getRootView(),context.getResources().getString(R.string.user_deleted),Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showUserVerifiedToast() {
        Snackbar.make(getWindow().getDecorView().getRootView(),context.getResources().getString(R.string.user_not_verified),Snackbar.LENGTH_SHORT).show();
    }

    //Connection error
    @Override
    public void showConnectionError() {
        Snackbar.make(getWindow().getDecorView().getRootView(), context.getResources().getString(R.string.error_connect), Snackbar.LENGTH_SHORT).show();
    }


    /*
    Intents
    */
    @Override
    public void launchHome() {
        launchActivityClearStack(this,MainNavigationActivity.class);
    }

    @Override
    public void launchSignUp() {
        launchActivity(this,RegisterActivity.class);
    }

    private void launchActivityClearStack(Context context,Class destinyClass){
        Intent intent = new Intent(context,destinyClass);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void launchActivity(Context context,Class destinyClass){
        Intent intent = new Intent(context,destinyClass);
        startActivity(intent);
    }

    /*
    UI messages methods
    */

    public void showCompleteLoginFormToast(){
        Snackbar.make(getWindow().getDecorView().getRootView(),getResources().getString(R.string.cant_pass),Snackbar.LENGTH_SHORT).show();
    }


    /*
    OnClick
    */
    @OnClick({ R.id.btn_login, R.id.text_register})
    public void onViewClicked(View view){
        Log.d("SUuuuuper","Dupppppppeeeeer");
        switch (view.getId()){
            case R.id.btn_login:
                Log.d("Ingresando","Usando login");
                login();
                break;
            case R.id.text_register:
                Log.d("Ingresando","Usando registro");
                launchSignUp();
                break;
        }
    }
}
