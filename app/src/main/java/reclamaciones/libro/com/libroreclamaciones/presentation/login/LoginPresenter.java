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
    private String TAG = LoginPresenter.class.getSimpleName();

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
                if (response.isSuccessful()){
                    if (response.body() != null){
                        JsonObject jsonObject = response.body();
                        Log.d("uooasdas", jsonObject.toString());
                        int status = jsonObject.get("status").getAsInt();
                        String token = jsonObject.get("token").toString();
                        switch (status){
                            case 1:
                                session.setKeyToken(token);
                                if (isAttached()){
                                    getView().hideLoadingDialog();
                                    getView().showSuccessToast();
                                    getView().launchHome();
                                }
                                break;
                            case 2:
                                if (isAttached()) {
                                    getView().hideLoadingDialog();
                                    getView().showWrongCredentialsToast();
                                }
                                break;
                            case 3:
                                break;
                            case 4:
                                break;
                        }
                        session.login();


                    }
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
