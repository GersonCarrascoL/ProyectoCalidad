package reclamaciones.libro.com.libroreclamaciones.presentation.main.maps;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import reclamaciones.libro.com.libroreclamaciones.data.model.BranchOfficeList;

public interface MapsContract {
    interface View{
        void setBranchOffice(BranchOfficeList branchOfficeList);
        void showLoadingDialog();
        void hideLoadingDialog();
        void showConnectionError();
    }

    interface Presenter{
        void onViewDettach();
        void onViewAttach(MapsContract.View view);
        void getBranchOffice(LatLng location_present);
    }
}
