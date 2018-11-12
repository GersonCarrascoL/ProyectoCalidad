package reclamaciones.libro.com.libroreclamaciones.presentation.main.profile;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonObject;

import androidx.annotation.Nullable;
import reclamaciones.libro.com.libroreclamaciones.data.model.User;
import reclamaciones.libro.com.libroreclamaciones.data.repository.local.SessionManager;
import reclamaciones.libro.com.libroreclamaciones.data.repository.remote.ServiceGenerator;
import reclamaciones.libro.com.libroreclamaciones.data.repository.remote.request.UserRequest;
import reclamaciones.libro.com.libroreclamaciones.presentation.login.LoginContract;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfilePresenter implements ProfileContract.Presenter{
    private Context context;
    private ProfileContract.View view;
    private SessionManager sessionManager;

    public ProfilePresenter(Context context){
        this.context = context;
        sessionManager = SessionManager.getInstance(context);
    }

    @Override
    public void onViewDettach() {
        this.view = null;
    }

    @Override
    public void onViewAttach(ProfileContract.View view) {
        this.view = view;
    }
    @Nullable
    private ProfileContract.View getView() {
        return view;
    }

    private boolean isAttached() {
        return getView() != null;
    }

    @Override
    public void getProfile() {
        if (isAttached()){
            getView().showLoadingDialog();
        }

        UserRequest userRequest = ServiceGenerator.createService(UserRequest.class);
        Log.d("TOKEN RETURN: ",sessionManager.getKeyToken());
        Call<User> call = userRequest.getProfile(sessionManager.getKeyToken());

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                int status = response.code();

                if (response.isSuccessful()){
                    User user = response.body();
                    switch (status){
                        case 200:

                            if (isAttached()){
                                getView().setInfo(user);
                            }
                            break;
                        case 500:
                            if (isAttached()){
                                getView().showConnectionError();
                            }
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                if (isAttached()) {
                    getView().showConnectionError();
                }
            }
        });
    }
}
