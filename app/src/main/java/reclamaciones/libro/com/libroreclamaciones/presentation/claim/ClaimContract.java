package reclamaciones.libro.com.libroreclamaciones.presentation.claim;

import java.io.File;

public interface ClaimContract {
    interface View{
        void showLoadingDialog();
        void hideLoadingDialog();
        void showDialogSuccess();
        void showSnackBarErrorCreated();
        void launchLoginActivity();
        void showConnectionError();
    }

    interface Presenter{
        void onViewDettach();
        void onViewAttach(ClaimContract.View view);
        void register(String imageBase64, String message, String cellphone, String name, String last_name, String email, String gender, int district, String ocupation, String grade_level, String dni, String password);
    }
}
