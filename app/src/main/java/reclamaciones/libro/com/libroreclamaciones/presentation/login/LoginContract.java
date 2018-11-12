package reclamaciones.libro.com.libroreclamaciones.presentation.login;

public class LoginContract {

    interface Presenter{
        void onViewDettach();
        void onViewAttach(LoginContract.View view);
        void login(String email, String password);
    }

    interface View{
        void showSuccessToast();
        void showLoadingDialog();
        void hideLoadingDialog();
        void showWrongCredentialsToast();
        void showConnectionError();
        void showUserDeleteToast();
        void showUserVerifiedToast();
        void launchHome();
        void launchSignUp();
    }

}
