package reclamaciones.libro.com.libroreclamaciones.presentation.enterprise;

import reclamaciones.libro.com.libroreclamaciones.data.model.ResponseValoracion;
import reclamaciones.libro.com.libroreclamaciones.data.model.Sucursal;

public interface EnterpriseContract {

    interface View{
        void setInfo(Sucursal sucursal);
        void setResponseValoracion(ResponseValoracion responseValoracion);
        void showLoadingDialog();
        void hideLoadingDialog();
        void showValorationDuplicateError();
        void showConnectionError();
        void showErrorSaveComment();
        void showCommentSuccessfull();
        void showCompleteMessageFormSnackbar();
    }

    interface Presenter{
        void onViewDettach();
        void onViewAttach(EnterpriseContract.View view);
        void getSucursal(int idSucursal, int idUsuario);
        void sendComment(int idSucursal,int idUsuario,String message,float rating);
    }
}
