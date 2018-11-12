package reclamaciones.libro.com.libroreclamaciones.presentation.main.profile;

import reclamaciones.libro.com.libroreclamaciones.data.model.User;

public interface ProfileContract {
    interface Presenter{
        void onViewDettach();
        void onViewAttach(ProfileContract.View view);
        void getProfile();
    }

    interface View{
        void setInfo(User user);
        void showLoadingDialog();
        void showConnectionError();
    }
}
