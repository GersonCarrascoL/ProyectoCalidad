package reclamaciones.libro.com.libroreclamaciones.presentation.enterprise;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import reclamaciones.libro.com.libroreclamaciones.R;
import reclamaciones.libro.com.libroreclamaciones.data.model.Sucursal;
import reclamaciones.libro.com.libroreclamaciones.data.repository.local.SessionManager;
import reclamaciones.libro.com.libroreclamaciones.presentation.enterprise.adapter.CommentsAdapter;
import reclamaciones.libro.com.libroreclamaciones.presentation.main.profile.ProfilePresenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.w3c.dom.Text;

public class EnterpriseActivity extends AppCompatActivity implements EnterpriseContract.View{

    @BindView(R.id.enterprise_name)
    TextView enterprise_name;
    @BindView(R.id.enterprise_address)
    TextView enterprise_address;
    @BindView(R.id.enterprise_rating)
    TextView enterprise_rating;
    @BindView(R.id.label_personal_opinion)
    TextInputLayout label_personal_opinion;
    @BindView(R.id.enterprise_personal_opinion)
    TextInputEditText enterprise_personal_opinion;
    @BindView(R.id.enterprise_send_opinion)
    ImageView enterprise_send_opinion;

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private CommentsAdapter commentsAdapter;
    private Toolbar toolbar;
    private TextView profile_valoracionGeneral;
    private ProgressDialog mProgressDialog;
    private Context context;
    boolean flag;
    RatingBar ratingBar;
    RatingBar person_rating;

    private String idSucursal;
    private float person_rating_comment;
    private int idUser;
    private EnterprisePresenter presenter;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_enterprise);

        flag = false;

        context = this;

        toolbar = findViewById(R.id.toolbar_general);
        ratingBar = findViewById(R.id.ratingBar_general);
        person_rating = findViewById(R.id.ratingBar_personal);
        profile_valoracionGeneral = findViewById(R.id.enterprise_rating);
        recyclerView = findViewById(R.id.comments_list_rv);
        recyclerView.setHasFixedSize(true);

        linearLayoutManager = new LinearLayoutManager(context);

        recyclerView.setLayoutManager(linearLayoutManager);

        ButterKnife.bind(this);

        session = SessionManager.getInstance(context);
        idUser = session.getIdUser();

        Intent intent = getIntent();
        idSucursal = intent.getStringExtra("idSucursal");

        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("EMPRESA");

        person_rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                person_rating_comment = rating;
            }
        });

        enterprise_send_opinion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });


        if (presenter == null){
            presenter = new EnterprisePresenter(context);
        }

        mProgressDialog = new ProgressDialog(context, R.style.MyProgressDialogTheme);
        mProgressDialog.setMessage(getText(R.string.default_loading_text));
        mProgressDialog.setCancelable(false);

        getPresenter().getSucursal(Integer.parseInt(idSucursal),idUser);
    }


    public void sendMessage(){
        label_personal_opinion.setErrorEnabled(false);
        label_personal_opinion.setError(null);

        flag = true;

        validateMessage();
        if (flag){
            getPresenter().sendComment(Integer.parseInt(idSucursal),idUser,enterprise_personal_opinion.getText().toString(),5);
        }else {
            showCompleteMessageFormSnackbar();
        }

    }

    private void validateMessage(){
        if (enterprise_personal_opinion.length()<=0){
            enterprise_personal_opinion.requestFocus();
            label_personal_opinion.setErrorEnabled(true);
            label_personal_opinion.setError(getResources().getString(R.string.password_invalid));
            flag =false;
        }
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
        Log.d("Sucursal Nombre", sucursal.getPuntaje_promedio().toString());
        enterprise_name.setText(sucursal.getNombreEmpresa());
        enterprise_address.setText(sucursal.getDireccion());
        enterprise_rating.setText(sucursal.getPuntaje_promedio().toString());
        ratingBar.setRating(sucursal.getPuntaje_promedio());

        commentsAdapter = new CommentsAdapter(sucursal.getComentarios());
        recyclerView.setAdapter(commentsAdapter);
    }

    @Override
    public void showLoadingDialog() {
        mProgressDialog.show();
    }

    @Override
    public void hideLoadingDialog() {
        mProgressDialog.hide();
    }

    @Override
    public void showConnectionError() {
        Snackbar.make(getWindow().getDecorView().getRootView(),getResources().getString(R.string.error_connect),Snackbar.LENGTH_LONG);
    }

    @Override
    public void showCompleteMessageFormSnackbar() {
        Snackbar.make(getWindow().getDecorView().getRootView(),getResources().getString(R.string.enterprise_send_message_error),Snackbar.LENGTH_LONG);
    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.enterprise_send_opinion:
                try {
                    Snackbar.make(getWindow().getDecorView().getRootView(),getResources().getString(R.string.enterprise_send_message_error),Snackbar.LENGTH_LONG);
                } catch (Exception e) {

                    e.printStackTrace();
                }
                break;
        }
    }
}
