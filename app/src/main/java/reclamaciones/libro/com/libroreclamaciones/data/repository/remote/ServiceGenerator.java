package reclamaciones.libro.com.libroreclamaciones.data.repository.remote;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {
    public static final String API_BASE_URL = WS.root;
    public static final String API_BASE_URL_P = WS.root_p;

    private static final OkHttpClient httpClient  = new OkHttpClient();

    public static Retrofit.Builder builderNode =
            new Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());

    public static Retrofit.Builder builderPython =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL_P)
                    .addConverterFactory(GsonConverterFactory.create());

    public static <S> S createService(Class<S> serviceClass){
        Retrofit retrofit = builderNode.client(httpClient).build();

        return retrofit.create(serviceClass);
    }

    public static <S> S createServicePython(Class<S> serviceClass){
        Retrofit retrofit = builderPython.client(httpClient).build();

        return retrofit.create(serviceClass);
    }
}
