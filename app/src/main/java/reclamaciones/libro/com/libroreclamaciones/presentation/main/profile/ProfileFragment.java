package reclamaciones.libro.com.libroreclamaciones.presentation.main.profile;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import reclamaciones.libro.com.libroreclamaciones.R;
import reclamaciones.libro.com.libroreclamaciones.data.model.User;


public class ProfileFragment extends Fragment implements ProfileContract.View{

    @BindView(R.id.profile_name)
    TextView profile_name;
    @BindView(R.id.profile_ocupation)
    TextView profile_ocupation;
    @BindView(R.id.profile_district)
    TextView profile_district;
    @BindView(R.id.profile_sex)
    TextView profile_sex;
    @BindView(R.id.profile_email)
    TextView profile_email;
    @BindView(R.id.profile_dni)
    TextView profile_dni;
    @BindView(R.id.profile_scholar_grade)
    TextView profile_scholar_grade;

    private Context context;
    private ProgressDialog mProgressDialog;
    private ProfilePresenter presenter;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = getContext();

        if (presenter == null){
            presenter = new ProfilePresenter(context);
        }

        mProgressDialog = new ProgressDialog(context,R.style.MyProgressDialogTheme);
        mProgressDialog.setMessage(getText(R.string.default_loading_text));
        mProgressDialog.setCancelable(false);

        getPresenter().getProfile();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this,v);
        getPresenter().getProfile();

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        getPresenter().onViewAttach(ProfileFragment.this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPresenter().onViewDettach();
    }

    public ProfileContract.Presenter getPresenter(){
        return presenter;
    }

    @Override
    public void setInfo(User user) {
        if (user.getDistrito().equals("")){
            user.setDistrito("-");
        }
        if (user.getOcupacion().equals("")){
            user.setOcupacion("-");
        }
        if (user.getGradoEducativo().equals("")){
            user.setGradoEducativo("-");
        }
        if (user.getSexo().equals("")){
            user.setSexo("-");
        }
        String nombre = user.getNombre()+" "+user.getApellido();
        profile_name.setText(nombre);
        profile_ocupation.setText(user.getOcupacion());
        profile_email.setText(user.getCorreo());
        profile_district.setText(user.getDistrito());
        profile_dni.setText(user.getDni());
        profile_scholar_grade.setText(user.getGradoEducativo());
        profile_sex.setText(user.getSexo());

    }


    @Override
    public void showLoadingDialog() {
        mProgressDialog.show();
    }

    @Override
    public void showConnectionError() {
        Snackbar.make(getView(),getResources().getString(R.string.error_connect),Snackbar.LENGTH_LONG);
    }

}
