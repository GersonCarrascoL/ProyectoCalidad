package reclamaciones.libro.com.libroreclamaciones.presentation.enterprise;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonObject;

import androidx.annotation.Nullable;
import reclamaciones.libro.com.libroreclamaciones.data.model.ResponseValoracion;
import reclamaciones.libro.com.libroreclamaciones.data.model.Sucursal;
import reclamaciones.libro.com.libroreclamaciones.data.repository.remote.ServiceGenerator;
import reclamaciones.libro.com.libroreclamaciones.data.repository.remote.request.EnterpriseRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EnterprisePresenter implements EnterpriseContract.Presenter{
    private Context context;
    private EnterpriseContract.View view;

    public EnterprisePresenter(Context context){
        this.context = context;

    }

    @Override
    public void onViewDettach() {
        this.view = null;
    }

    @Override
    public void onViewAttach(EnterpriseContract.View view) {
        this.view = view;
    }

    @Nullable
    private EnterpriseContract.View getView() {
        return view;
    }

    private boolean isAttached() {
        return getView() != null;
    }

    @Override
    public void getSucursal(int idSucursal,int idUser) {
        if (isAttached()){
            getView().showLoadingDialog();
        }

        Log.d("idSucursal",idSucursal+"");
        EnterpriseRequest enterpriseRequest = ServiceGenerator.createServicePython(EnterpriseRequest.class);

        Call<Sucursal> call = enterpriseRequest.getSucursal(idSucursal,idUser);

        call.enqueue(new Callback<Sucursal>() {
            @Override
            public void onResponse(Call<Sucursal> call, Response<Sucursal> response) {

                int status = response.code();
                Log.d("Response status",status+"");

                Sucursal sucursal = response.body();
                Log.d("Sucursal",sucursal.toString()+"");
                switch (status){
                    case 200:
                        if (isAttached()){
                            getView().setInfo(sucursal);
                        }
                        break;
                    case 500:
                        if (isAttached()){
                            getView().showConnectionError();
                        }
                        break;
                }
            }


            @Override
            public void onFailure(Call<Sucursal> call, Throwable t) {
                if (isAttached()) {
                    getView().showConnectionError();
                }
            }
        });
    }

    @Override
    public void sendComment(int idSucursal, int idUsuario, String message, float rating) {
        if (isAttached()){
            getView().showLoadingDialog();
        }

        Log.d("Empresa Data","Envio de mensajes");
        Log.d("Empresa idUser",idUsuario+"");
        Log.d("Empresa idSucursal",idSucursal+"");
        Log.d("Empresa rating",rating+"");
        Log.d("Empresa message",message+"");

        EnterpriseRequest enterpriseRequest = ServiceGenerator.createServicePython(EnterpriseRequest.class);

        Call<ResponseValoracion> call = enterpriseRequest.sendComment(idSucursal,idUsuario,message,rating);

        call.enqueue(new Callback<ResponseValoracion>() {
            @Override
            public void onResponse(Call<ResponseValoracion> call, Response<ResponseValoracion> response) {
                Log.d("Response",""+response);
                int status = response.code();
                Log.d("Status",""+status);

                ResponseValoracion res = response.body();
                switch (status){
                    case 201:
                        if (isAttached()){
                            getView().hideLoadingDialog();
                            getView().setResponseValoracion(res);
                        }
                        break;
                    case 200:
                        if (isAttached()){
                            getView().hideLoadingDialog();
                            getView().showValorationDuplicateError();
                        }
                        break;
                    case 500:
                        if (isAttached()){
                            getView().hideLoadingDialog();
                            getView().showConnectionError();
                        }
                        break;
                }

            }

            @Override
            public void onFailure(Call<ResponseValoracion> call, Throwable t) {

            }
        });
    }
}
