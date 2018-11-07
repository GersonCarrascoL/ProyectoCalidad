package reclamaciones.libro.com.libroreclamaciones.presentation.main.maps;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import reclamaciones.libro.com.libroreclamaciones.data.model.InfoWindow;
import reclamaciones.libro.com.libroreclamaciones.utils.CustomInfoWindowGoogleMap;
import reclamaciones.libro.com.libroreclamaciones.presentation.enterprise.EnterpriseActivity;
import reclamaciones.libro.com.libroreclamaciones.R;


public class MapsFragment extends Fragment implements OnMapReadyCallback,GoogleMap.OnMarkerClickListener{

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (mLocationPermissionsGranted) {
            getDeviceLocation();

            if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
        }

        // CREAR MARKER OPTIONS
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions
                .position(new LatLng(-11.9757177,-76.9941974))
                .title("Cerca de mi casa")
                .snippet("Muy cerca")
                .icon((BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

        // SE CREA LA CLASE QUE RECIBE TODA LA INFO PARA EL INFO WINDOW
        InfoWindow infoWindow = new InfoWindow();
        infoWindow.setEnterprise_name("Empresa 1");
        infoWindow.setEnterprise_direction("Av San Martin cdra 65 Jr Santuario");

        //SE SETEA EL CREADOR DEL INFO WINDOW
        CustomInfoWindowGoogleMap customInfoWindowGoogleMap = new CustomInfoWindowGoogleMap(getActivity());
        mMap.setInfoWindowAdapter(customInfoWindowGoogleMap);

        // SE AGREGA EL MARCADOR Y SE SETEA EL INFO WINDOW AL MARKER
        Marker m = mMap.addMarker(markerOptions);
        m.setTag(infoWindow);
        m.showInfoWindow();

        //CUANDO CLICKEAS EN EL INFO WINDOW ABRE OTRO LISTENER
        googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Intent intent = new Intent(getActivity(),EnterpriseActivity.class);
                startActivity(intent);
            }
        });
    }

    public MapsFragment() {
        // Required empty public constructor
    }

    private static final String TAG = "MapActivity";

    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float ZOOM_CAMERA = 15f;

    //vars
    private Boolean mLocationPermissionsGranted = false;
    private GoogleMap mMap;
    private LocationManager locationManager;
    private LocationListener locationListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.fragment_maps, container, false);
        getLocationPermission();
        return v;
    }

    private void getDeviceLocation() {
        locationManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);

        if (mLocationPermissionsGranted){
            locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    moveCamera(new LatLng(location.getLatitude(),location.getLongitude()),ZOOM_CAMERA);
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            };
        }else{
            Toast.makeText(getActivity().getApplicationContext(), "unable to get current location", Toast.LENGTH_SHORT).show();
        }

    }

    private void getLocationPermission() {
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if(ContextCompat.checkSelfPermission(getActivity().getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(getActivity().getApplicationContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                mLocationPermissionsGranted = true;
                initMap();
            }else{
                ActivityCompat.requestPermissions(getActivity(),
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        }else{
            ActivityCompat.requestPermissions(getActivity(),
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }


    public void initMap(){
        SupportMapFragment mapFragment = (SupportMapFragment)getChildFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment == null){
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            mapFragment = SupportMapFragment.newInstance();
            fragmentTransaction.replace(R.id.map,mapFragment).commit();
        }
        mapFragment.getMapAsync(this);
    }

    private void moveCamera(LatLng latLng, float zoom){
//        Log.d(TAG, "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude );
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mLocationPermissionsGranted = false;

        switch(requestCode){
            case LOCATION_PERMISSION_REQUEST_CODE:{
                if(grantResults.length > 0){
                    for(int i = 0; i < grantResults.length; i++){
                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                            mLocationPermissionsGranted = false;
                            Log.d(TAG, "onRequestPermissionsResult: permission failed");
                            return;
                        }
                    }
                    Log.d(TAG, "onRequestPermissionsResult: permission granted");
                    mLocationPermissionsGranted = true;
                    //initialize our map
                    initMap();
                }
            }
        }
    }


    @Override
    public boolean onMarkerClick(Marker marker) {
        Intent intent = new Intent(getActivity(),EnterpriseActivity.class);
        startActivity(intent);
        return true;
    }


}
