package reclamaciones.libro.com.libroreclamaciones.data.repository.remote.request;


import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UserRequest {

    //Login
    @FormUrlEncoded
    @POST("person/login")
    Call<JsonObject> login(@Field("personEmail") String email,
                           @Field("personPassword") String password);

}
