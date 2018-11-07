package reclamaciones.libro.com.libroreclamaciones.presentation.main;

import android.content.Context;

public class MainNavigationPresenter {
    private Context context;
    private MainNavigationContract mainContract;


    public MainNavigationPresenter(Context context){
        this.context = context;
    }

    public void OnViewAttached(MainNavigationContract mainContract){
        this.mainContract = mainContract;
    }

    public void onViewDettached(){
        mainContract = null;
    }

    public MainNavigationContract getView(){
        return mainContract;
    }
}
