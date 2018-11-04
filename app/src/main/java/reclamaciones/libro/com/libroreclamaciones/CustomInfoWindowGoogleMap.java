package reclamaciones.libro.com.libroreclamaciones;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;


public class CustomInfoWindowGoogleMap implements GoogleMap.InfoWindowAdapter {

    private Context context;

    public CustomInfoWindowGoogleMap(Context context){
        this.context = context;
    }
    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View view = ((Activity)context).getLayoutInflater().inflate(R.layout.info_marker,null);

        TextView tv_name = view.findViewById(R.id.enterprise_name);
        TextView tv_direction = view.findViewById(R.id.enterprise_direction);

        tv_name.setText(marker.getTitle());
        tv_direction.setText(marker.getSnippet());

        return view;
    }
}
