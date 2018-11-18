package reclamaciones.libro.com.libroreclamaciones.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;

import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import reclamaciones.libro.com.libroreclamaciones.R;


public class CustomInfoWindowGoogleMap implements GoogleMap.InfoWindowAdapter {

//    @BindView(R.id.enterprise_name)
    TextView enterprise_name;
//    @BindView(R.id.enterprise_direction)
    TextView enterprise_direction;


    private Context context;
    private final View mView;

    public CustomInfoWindowGoogleMap(Context context){
        this.context = context;
        mView = LayoutInflater.from(context).inflate(R.layout.info_marker,null);
    }

    private void renderWindowText(Marker marker , View view){
        String title = marker.getTitle();
        enterprise_name = view.findViewById(R.id.enterprise_name);

        enterprise_name.setText(title);

        String snippet = marker.getSnippet();
        enterprise_direction = view.findViewById(R.id.enterprise_direction);

        enterprise_direction.setText(snippet);
    }
    @Override
    public View getInfoWindow(Marker marker) {
        renderWindowText(marker,mView);
        return mView;
    }

    @Override
    public View getInfoContents(Marker marker) {
        renderWindowText(marker,mView);
        return mView;
    }
}
