package reclamaciones.libro.com.libroreclamaciones.presentation.enterprise;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;
import reclamaciones.libro.com.libroreclamaciones.data.model.Sucursal;
import reclamaciones.libro.com.libroreclamaciones.data.model.User;
import reclamaciones.libro.com.libroreclamaciones.data.repository.remote.ServiceGenerator;
import reclamaciones.libro.com.libroreclamaciones.data.repository.remote.request.EnterpriseRequest;
import reclamaciones.libro.com.libroreclamaciones.data.repository.remote.request.UserRequest;
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
    public void getSucursal(int idSucursal) {
        if (isAttached()){
            getView().showLoadingDialog();
        }

        EnterpriseRequest enterpriseRequest = ServiceGenerator.createServicePython(EnterpriseRequest.class);

        Call<Sucursal> call = enterpriseRequest.getSucursal(idSucursal);

        call.enqueue(new Callback<Sucursal>() {
            @Override
            public void onResponse(Call<Sucursal> call, Response<Sucursal> response) {

                int status = response.code();

                if (response.isSuccessful()){
                    Sucursal sucursal = response.body();
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
            }

            @Override
            public void onFailure(Call<Sucursal> call, Throwable t) {
                if (isAttached()) {
                    getView().showConnectionError();
                }
            }
        });
    }
}
