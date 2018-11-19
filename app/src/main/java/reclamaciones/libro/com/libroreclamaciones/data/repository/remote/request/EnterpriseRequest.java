package reclamaciones.libro.com.libroreclamaciones.data.repository.remote.request;

import reclamaciones.libro.com.libroreclamaciones.data.model.Sucursal;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface EnterpriseRequest {

    @GET("detalles-sucursal/{id}")
    Call<Sucursal> getSucursal(@Path("id") int idSucursal);
}
