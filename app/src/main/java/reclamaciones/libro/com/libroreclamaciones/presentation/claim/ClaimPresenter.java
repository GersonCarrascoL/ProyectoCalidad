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
    public void register(File fileImage, String message, String cellphone, String name, String last_name, String email, String gender, int district, String ocupation, String grade_level, String dni, String password) {
        if (isAttached()){
            getView().showLoadingDialog();
        }

        File file = new File(fileImage.toURI());

        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"),file);

//        Map<String,RequestBody> map = new HashMap<>();
//
//        map.put("claimPhoto","file\"; filename=\"pp.png\"",requestFile);
//        map.put("claimMessage",toRequestBody(message+""));
//        map.put("claimCellPhone",toRequestBody(cellphone+""));
//        map.put("userName",toRequestBody(name+""));
//        map.put("userLastName",toRequestBody(last_name+""));
//        map.put("userEmail",toRequestBody(email+""));
//        map.put("userID",toRequestBody(name+"."+last_name));
//        map.put("userPassword",toRequestBody(password+""));
//        map.put("userDNI",toRequestBody(dni+""));
//        map.put("userGender",toRequestBody(gender+""));
//        map.put("userDistrict",toRequestBody(district+""));
//        map.put("userOcupation",toRequestBody(ocupation+""));
//        map.put("userScholarGrade",toRequestBody(grade_level+""));


        MultipartBody.Part bodyImage = MultipartBody.Part.createFormData("claimPhoto",file.getName(),requestFile);
        RequestBody v_message = RequestBody.create(MediaType.parse("text/plain"), message+"");
        RequestBody v_cellphone = RequestBody.create(MediaType.parse("text/plain"), cellphone+"");
        RequestBody v_name = RequestBody.create(MediaType.parse("text/plain"), name+"");
        RequestBody v_lastname = RequestBody.create(MediaType.parse("text/plain"), last_name+"");
        RequestBody v_username = RequestBody.create(MediaType.parse("text/plain"), v_name+"."+v_lastname+"");
        RequestBody v_email = RequestBody.create(MediaType.parse("text/plain"), email+"");
        RequestBody v_gender = RequestBody.create(MediaType.parse("text/plain"), gender+"");
        RequestBody v_district = RequestBody.create(MediaType.parse("text/plain"), district+"");
        RequestBody v_ocupation = RequestBody.create(MediaType.parse("text/plain"), ocupation+"");
        RequestBody v_grade_level = RequestBody.create(MediaType.parse("text/plain"), grade_level+"");
        RequestBody v_dni = RequestBody.create(MediaType.parse("text/plain"), dni+"");
        RequestBody v_password = RequestBody.create(MediaType.parse("text/plain"), password+"");


        UserRequest userRequest = ServiceGenerator.createService(UserRequest.class);

        Call<JsonObject> call = userRequest.registerClaim(bodyImage,v_message,v_cellphone,v_name,v_lastname,v_email,v_username,v_password,v_dni,v_gender,v_district,v_ocupation,v_grade_level);

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
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                getView().hideLoadingDialog();
                getView().showConnectionError();
            }
        });
    }

    public static RequestBody toRequestBody (String value) {
        RequestBody body = RequestBody.create(MediaType.parse("text/plain"), value);
        return body ;
    }
}
