package reclamaciones.libro.com.libroreclamaciones.presentation.enterprise;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import reclamaciones.libro.com.libroreclamaciones.R;
import reclamaciones.libro.com.libroreclamaciones.data.model.ResponseValoracion;
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
import android.widget.LinearLayout;
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
    @BindView(R.id.linear_comment)
    LinearLayout linear_comment;
    @BindView(R.id.layout_send_comment)
    LinearLayout layout_send_comment;
    @BindView(R.id.layout_comment_personal_card)
    LinearLayout layout_comment_personal_card;
    @BindView(R.id.layout_comment_delete)
    LinearLayout layout_comment_delete;

    /*---INFO PERSONAL CARD---*/
    @BindView(R.id.card_person_name)
    TextView card_person_name;
    @BindView(R.id.card_person_rating_comment)
    RatingBar card_person_rating_comment;
    @BindView(R.id.card_comment_date)
    TextView card_comment_date;
    @BindView(R.id.card_person_comment)
    TextView card_person_comment;

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
//        person_rating_card = findViewById(R.id.person_rating_comment);
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

        Log.d("Empresa idUser",idUser+"");
        Log.d("Empresa idSucursal",idSucursal+"");

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
            getPresenter().sendComment(Integer.parseInt(idSucursal),idUser,enterprise_personal_opinion.getText().toString(),person_rating.getRating());
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

        Log.d("Sucursal Nombre", sucursal.getPuntajePromedio().toString());
        enterprise_name.setText(sucursal.getNombreEmpresa());
        enterprise_address.setText(sucursal.getDireccion());
        enterprise_rating.setText(sucursal.getPuntajePromedio().toString());
        ratingBar.setRating(sucursal.getPuntajePromedio());

        Log.d("TAMAÑO COMENTARIO",sucursal.getComentarioUsuario().size()+"");

        switch (sucursal.getEstadoComentario()){
            case -1:
                layout_comment_delete.setVisibility(LinearLayout.VISIBLE);
                layout_send_comment.setVisibility(LinearLayout.GONE);
                break;

            case 1:
                layout_comment_personal_card.setVisibility(LinearLayout.VISIBLE);
                card_person_name.setText(sucursal.getComentarioUsuario().get(0).getNombreUsuario());
                card_comment_date.setText(sucursal.getComentarioUsuario().get(0).getFecha());
                card_person_comment.setText(sucursal.getComentarioUsuario().get(0).getComentario());
                card_person_rating_comment.setRating(sucursal.getComentarioUsuario().get(0).getPuntaje());
                layout_send_comment.setVisibility(LinearLayout.GONE);
                break;

            default:
                break;
        }


        commentsAdapter = new CommentsAdapter(sucursal.getComentarios());
        recyclerView.setAdapter(commentsAdapter);
    }

    @Override
    public void setResponseValoracion(ResponseValoracion responseValoracion) {
        layout_comment_personal_card.setVisibility(LinearLayout.VISIBLE);
        card_person_name.setText(responseValoracion.getValoracion().getNombreUsuario());
        card_comment_date.setText(responseValoracion.getValoracion().getFecha());
        card_person_comment.setText(responseValoracion.getValoracion().getComentario());
        card_person_rating_comment.setRating(responseValoracion.getValoracion().getPuntaje());
        layout_send_comment.setVisibility(LinearLayout.GONE);
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
    public void showValorationDuplicateError() {
        Snackbar.make(getWindow().getDecorView().getRootView(),getResources().getString(R.string.enterprise_valoration_duplicate),Snackbar.LENGTH_LONG);
    }

    @Override
    public void showConnectionError() {
        Snackbar.make(getWindow().getDecorView().getRootView(),getResources().getString(R.string.error_connect),Snackbar.LENGTH_LONG);
    }

    @Override
    public void showErrorSaveComment() {
        Snackbar.make(getWindow().getDecorView().getRootView(),getResources().getString(R.string.enterprise_error_comment),Snackbar.LENGTH_LONG);
    }

    @Override
    public void showCommentSuccessfull() {
        Snackbar.make(getWindow().getDecorView().getRootView(),getResources().getString(R.string.enterprise_comment_success),Snackbar.LENGTH_LONG);
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
