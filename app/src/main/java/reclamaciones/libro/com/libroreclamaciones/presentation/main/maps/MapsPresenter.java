package reclamaciones.libro.com.libroreclamaciones.presentation.main.maps;

import android.content.Context;
import android.location.Location;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import androidx.annotation.Nullable;
import reclamaciones.libro.com.libroreclamaciones.data.model.BranchOfficeList;
import reclamaciones.libro.com.libroreclamaciones.data.repository.remote.ServiceGenerator;
import reclamaciones.libro.com.libroreclamaciones.data.repository.remote.request.MapRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsPresenter implements MapsContract.Presenter{

    private Context context;
    private MapsContract.View view;

    public MapsPresenter(Context context){
        this.context = context;
    }


    @Override
    public void onViewDettach() {
        this.view = null;
    }

    @Override
    public void onViewAttach(MapsContract.View view) {
        this.view = view;
    }

    @Nullable
    private MapsContract.View getView() {
        return view;
    }

    private boolean isAttached() {
        return getView() != null;
    }

    @Override
    public void getBranchOffice(LatLng location_present) {

        MapRequest mapRequest = ServiceGenerator.createServicePython(MapRequest.class);

        Call<BranchOfficeList> call = mapRequest.getOfficeBranch(location_present.latitude,location_present.longitude);

        call.enqueue(new Callback<BranchOfficeList>() {
            @Override
            public void onResponse(Call<BranchOfficeList> call, Response<BranchOfficeList> response) {
                BranchOfficeList jsonObject = response.body();
                int status = response.code();
                switch (status){
                    case 200:

                        if (isAttached()){
                            getView().hideLoadingDialog();
                            getView().setBranchOffice(jsonObject);
                        }
                        break;
                    case 500:
                        if (isAttached()) {
                            getView().hideLoadingDialog();
                            getView().showConnectionError();
                        }
                        break;
                }

            }

            @Override
            public void onFailure(Call<BranchOfficeList> call, Throwable t) {
                if (isAttached()) {
                    getView().hideLoadingDialog();
                    getView().showConnectionError();
                }
            }
        });
    }
}
