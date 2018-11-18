package reclamaciones.libro.com.libroreclamaciones.presentation.register.view_three;

public interface RegisterContract3 {
    interface View{
        void launchNextLayout();
        void launchClaimsLayout(String name,String last_name,String email,String gender,String district, String ocupation, String grade_level, String dni, String password);
        void showDialogClaim();
        void showDialogSuccess();
        void showSnackBarErrorCreated();
        void showConnectionError();
        void showLoadingDialog();
        void hideLoadingDialog();
    }

    interface Presenter{
        void onViewDettach();
        void onViewAttach(RegisterContract3.View view);
        void register(String name,String last_name,String email,String gender,int district, String ocupation, String grade_level, String dni, String password);
    }
}
