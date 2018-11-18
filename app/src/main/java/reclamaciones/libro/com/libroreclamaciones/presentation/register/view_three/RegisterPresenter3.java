package reclamaciones.libro.com.libroreclamaciones.presentation.register.view_three;

import android.app.Service;
import android.content.Context;
import android.util.Log;

import com.google.gson.JsonObject;

import androidx.annotation.Nullable;
import reclamaciones.libro.com.libroreclamaciones.data.repository.remote.ServiceGenerator;
import reclamaciones.libro.com.libroreclamaciones.data.repository.remote.request.UserRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterPresenter3 implements RegisterContract3.Presenter{

    private Context context;
    private RegisterContract3.View view;

    public RegisterPresenter3(Context context){
        this.context = context;
    }

    @Override
    public void onViewDettach() {
        this.view = null;
    }

    @Override
    public void onViewAttach(RegisterContract3.View view) {
        this.view = view;
    }

    @Nullable
    private RegisterContract3.View getView() {
        return view;
    }

    private boolean isAttached() {
        return getView() != null;
    }

    @Override
    public void register(String name, String last_name, String email, String gender, int district, String ocupation, String grade_level, String dni, String password) {
        if (isAttached()){
            getView().showLoadingDialog();
        }

        UserRequest userRequest = ServiceGenerator.createService(UserRequest.class);

        Call<JsonObject> call = userRequest.register(""+name,""+last_name,""+email,name+"."+last_name,""+password,""+dni,""+gender,district,""+ocupation,""+grade_level);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.d("Respuesta: ",""+response.code());
                int status = response.code();
                switch (status){
                    case 201:
                        if (isAttached()){
                            getView().hideLoadingDialog();
                            getView().showDialogSuccess();
                        }
                        break;
                    case 202:
                        if (isAttached()) {
                            getView().hideLoadingDialog();
                            getView().showDialogClaim();
                        }
                        break;

                    case 400:
                        if (isAttached()) {
                            getView().hideLoadingDialog();
                            getView().showSnackBarErrorCreated();
                        }
                        break;
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                getView().showConnectionError();
            }
        });
    }
}
