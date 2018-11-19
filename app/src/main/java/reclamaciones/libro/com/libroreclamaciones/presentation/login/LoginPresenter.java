package reclamaciones.libro.com.libroreclamaciones.presentation.login;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonObject;

import androidx.annotation.Nullable;
import reclamaciones.libro.com.libroreclamaciones.data.repository.local.SessionManager;
import reclamaciones.libro.com.libroreclamaciones.data.repository.remote.ServiceGenerator;
import reclamaciones.libro.com.libroreclamaciones.data.repository.remote.request.UserRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPresenter implements LoginContract.Presenter{

    private Context context;
    private LoginContract.View view;
    private SessionManager session;

    public LoginPresenter(Context context){
        this.context = context;
        session = SessionManager.getInstance(context);
    }

    @Override
    public void onViewDettach() {
        this.view = null;
    }

    @Override
    public void onViewAttach(LoginContract.View view) {
        this.view = view;
    }

    @Nullable
    private LoginContract.View getView() {
        return view;
    }

    private boolean isAttached() {
        return getView() != null;
    }

    @Override
    public void login(String email, String password) {
        if (isAttached()){
            getView().showLoadingDialog();
        }

        UserRequest userRequest = ServiceGenerator.createService(UserRequest.class);

        Call<JsonObject> call = userRequest.login(email,password);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                JsonObject jsonObject = response.body();
                int status = response.code();
                Log.d("ATTACHHED",""+response.code());
                switch (status){
                    case 200:
                        String token = jsonObject.get("token").getAsString();
                        int idUser = jsonObject.get("idUsuario").getAsInt();
                        Log.d("TOKEN: ",token);
                        Log.d("IDUSUARIO: ",idUser+"");

                        session.setIdUser(idUser);
                        session.setKeyToken(token);

                        session.login();
                        if (isAttached()){

                            getView().hideLoadingDialog();
                            getView().showSuccessToast();
                            getView().launchHome();
                        }
                        break;
                    case 202:
                        if (isAttached()) {
                            getView().hideLoadingDialog();
                            getView().showWrongCredentialsToast();
                        }
                        break;

                    case 403:
                        if (isAttached()) {
                            getView().hideLoadingDialog();
                            getView().showUserDeleteToast();
                        }
                        break;
                    case 401:
                        if (isAttached()) {
                            getView().hideLoadingDialog();
                            getView().showUserVerifiedToast();
                        }
                        break;
                    case 500:
                        if (isAttached()) {
                            getView().hideLoadingDialog();
                            getView().showConnectionError();
                        }
                        break;
                }


            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                if (isAttached()) {
                    getView().hideLoadingDialog();
                    getView().showConnectionError();
                }
            }
        });
    }
}
