package reclamaciones.libro.com.libroreclamaciones.data.repository.remote.request;


import com.google.gson.JsonObject;

import reclamaciones.libro.com.libroreclamaciones.data.model.User;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UserRequest {

    //Login
    @FormUrlEncoded
    @POST("users/signin")
    Call<JsonObject> login(@Field("userEmail") String email,
                           @Field("userPassword") String password);

    //Profile
    @GET("users")
    Call<User> getProfile(@Header("Authorization") String token);
}
