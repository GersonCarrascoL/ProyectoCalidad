package reclamaciones.libro.com.libroreclamaciones.data.repository.remote.request;


import com.google.gson.JsonObject;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import reclamaciones.libro.com.libroreclamaciones.data.model.User;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface UserRequest {

    //Login
    @FormUrlEncoded
    @POST("users/signin")
    Call<JsonObject> login(@Field("userEmail") String email,
                           @Field("userPassword") String password);

    //Profile
    @GET("users")
    Call<User> getProfile(@Header("Authorization") String token);

    //Register
    @FormUrlEncoded
    @POST("users/signup")
    Call<JsonObject> register(@Field("userName") String name,
                              @Field("userLastName") String last_name,
                              @Field("userEmail") String email,
                              @Field("userID") String userName,
                              @Field("userPassword") String password,
                              @Field("userDNI") String dni,
                              @Field("userGender") String gender,
                              @Field("userDistrict") int district,
                              @Field("userOcupation") String ocupation,
                              @Field("userScholarGrade") String grade_level);

    //Claim
    @FormUrlEncoded
    @POST("users/claims")
    Call<JsonObject> registerClaim(@Field("userName") String name,
                                   @Field("userLastName") String last_name,
                                   @Field("userEmail") String email,
                                   @Field("userID") String userName,
                                   @Field("userPassword") String password,
                                   @Field("userDNI") String dni,
                                   @Field("userGender") String gender,
                                   @Field("userDistrict") int district,
                                   @Field("userOcupation") String ocupation,
                                   @Field("userScholarGrade") String gradeLevel,
                                   @Field("claimMessage") String claimMessage,
                                   @Field("claimCellPhone") String claimCellPhone,
                                   @Field("claimPhoto") String claimPhoto );

}
