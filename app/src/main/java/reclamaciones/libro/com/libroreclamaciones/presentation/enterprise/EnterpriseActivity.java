package reclamaciones.libro.com.libroreclamaciones.presentation.enterprise;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import reclamaciones.libro.com.libroreclamaciones.R;
import reclamaciones.libro.com.libroreclamaciones.data.model.Sucursal;
import reclamaciones.libro.com.libroreclamaciones.presentation.main.profile.ProfilePresenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class EnterpriseActivity extends AppCompatActivity implements EnterpriseContract.View{

    private Toolbar toolbar;
    private TextView profile_valoracionGeneral;
    private ProgressDialog mProgressDialog;
    private Context context;
    RatingBar ratingBar;

    private String idSucursal;
    private EnterprisePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enterprise);

        toolbar = findViewById(R.id.toolbar_general);
        ratingBar = findViewById(R.id.ratingBar_general);
        profile_valoracionGeneral = findViewById(R.id.profile_valoracionGeneral);

        context = this;

        Intent intent = getIntent();
        idSucursal = intent.getStringExtra("idSucursal");

        Log.d("Empresa idSucursal:",""+idSucursal);

        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("EMPRESA");


        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                profile_valoracionGeneral.setText(""+rating);
            }
        });

        if (presenter == null){
            presenter = new EnterprisePresenter(context);
        }

        mProgressDialog = new ProgressDialog(context, R.style.MyProgressDialogTheme);
        mProgressDialog.setMessage(getText(R.string.default_loading_text));
        mProgressDialog.setCancelable(false);

        getPresenter().getSucursal(Integer.parseInt(idSucursal));
    }

    @Override
    public void onResume() {
        super.onResume();
        getPresenter().onViewAttach(EnterpriseActivity.this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPresenter().onViewDettach();
    }

    public EnterpriseContract.Presenter getPresenter(){
        return presenter;
    }

    @Override
    public void setInfo(Sucursal sucursal) {

    }

    @Override
    public void showLoadingDialog() {
        mProgressDialog.show();
    }

    @Override
    public void showConnectionError() {
        Snackbar.make(getWindow().getDecorView().getRootView(),getResources().getString(R.string.error_connect),Snackbar.LENGTH_LONG);
    }
}
