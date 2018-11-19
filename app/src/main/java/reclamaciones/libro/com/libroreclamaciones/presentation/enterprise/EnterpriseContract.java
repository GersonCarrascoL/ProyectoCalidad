package reclamaciones.libro.com.libroreclamaciones.presentation.enterprise;

import reclamaciones.libro.com.libroreclamaciones.data.model.Sucursal;

public interface EnterpriseContract {

    interface View{
        void setInfo(Sucursal sucursal);
        void showLoadingDialog();
        void hideLoadingDialog();
        void showConnectionError();
    }

    interface Presenter{
        void onViewDettach();
        void onViewAttach(EnterpriseContract.View view);
        void getSucursal(int idSucursal, int idUsuario);
    }
}
