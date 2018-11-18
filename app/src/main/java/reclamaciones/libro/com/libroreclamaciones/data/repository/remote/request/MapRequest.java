package reclamaciones.libro.com.libroreclamaciones.data.repository.remote.request;

import reclamaciones.libro.com.libroreclamaciones.data.model.BranchOfficeList;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface MapRequest {

    @FormUrlEncoded
    @POST("empresas-cercanas")
    Call<BranchOfficeList> getOfficeBranch(@Field("latitud") double latitude,
                                           @Field("longitud") double longitude);
}
