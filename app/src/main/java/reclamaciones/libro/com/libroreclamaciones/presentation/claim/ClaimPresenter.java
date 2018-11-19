package reclamaciones.libro.com.libroreclamaciones.presentation.claim;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonObject;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.Nullable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import reclamaciones.libro.com.libroreclamaciones.data.repository.remote.ServiceGenerator;
import reclamaciones.libro.com.libroreclamaciones.data.repository.remote.request.UserRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Multipart;

public class ClaimPresenter implements ClaimContract.Presenter{

    private Context context;
    private ClaimContract.View view;

    public ClaimPresenter(Context context){
        this.context = context;
    }

    @Override
    public void onViewDettach() {
        this.view = null;
    }

    @Override
    public void onViewAttach(ClaimContract.View view) {
        this.view = view;
    }

    @Nullable
    private ClaimContract.View getView() {
        return view;
    }

    private boolean isAttached() {
        return getView() != null;
    }

    @Override
    public void register(String imageBase64, String message, String cellphone, String name, String last_name, String email, String gender, int district, String ocupation, String grade_level, String dni, String password) {
        if (isAttached()){
            getView().showLoadingDialog();
        }

        UserRequest userRequest = ServiceGenerator.createService(UserRequest.class);

        Call<JsonObject> call = userRequest.registerClaim(name+"",last_name+"",email+"",name+"."+last_name,password+"",dni+"",gender+"",district,ocupation+"",grade_level+"",message+"",cellphone+"",imageBase64+"");

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.d("Response: ", response.toString());
                int status = response.code();

                switch (status){
                    case 201:
                        if (isAttached()){
                            getView().hideLoadingDialog();
                            getView().showDialogSuccess();
                        }
                        break;

                    case 202:
                        if (isAttached()){
                            getView().hideLoadingDialog();
                            getView().showSnackBarErrorCreated();
                        }
                        break;
                    case 500:
                        if (isAttached()){
                            getView().hideLoadingDialog();
                            getView().showConnectionError();
                        }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                getView().hideLoadingDialog();
                getView().showConnectionError();
            }
        });
    }
}
