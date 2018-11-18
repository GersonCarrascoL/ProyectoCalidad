package reclamaciones.libro.com.libroreclamaciones.presentation.main.maps;

import android.Manifest;
import android.app.ProgressDialog;
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
import com.google.android.material.snackbar.Snackbar;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import reclamaciones.libro.com.libroreclamaciones.data.model.BranchOffice;
import reclamaciones.libro.com.libroreclamaciones.data.model.BranchOfficeList;
import reclamaciones.libro.com.libroreclamaciones.utils.CustomInfoWindowGoogleMap;
import reclamaciones.libro.com.libroreclamaciones.presentation.enterprise.EnterpriseActivity;
import reclamaciones.libro.com.libroreclamaciones.R;


public class MapsFragment extends Fragment implements OnMapReadyCallback,GoogleMap.OnMarkerClickListener,MapsContract.View {
    private static final String TAG = "MapActivity";

    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float ZOOM_CAMERA = 12f;

    //vars
    private Boolean mLocationPermissionsGranted = false;
    private GoogleMap mMap;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private ProgressDialog mProgressDialog;
    private MapsPresenter presenter;
    private Context context;
    private Location location_present;
    private Map<String,LatLng> mapMarkers;
    private Map<String,LatLng> markersService;

    private boolean firstChargeView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.context = getContext();

        if (presenter == null){
            presenter = new MapsPresenter(context);
        }

        mapMarkers = new HashMap<>();
        firstChargeView = true;

        getLocationPermission();

        mProgressDialog = new ProgressDialog(context,R.style.MyProgressDialogTheme);
        mProgressDialog.setMessage(getText(R.string.default_loading_text));
        mProgressDialog.setCancelable(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.fragment_maps, container, false);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        getPresenter().onViewAttach(MapsFragment.this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPresenter().onViewDettach();
    }

    public MapsContract.Presenter getPresenter(){
        return presenter;
    }

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
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,10000,0,locationListener);
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
        }

    }

    public MapsFragment() {
        // Required empty public constructor
    }


    private void getDeviceLocation() {
        locationManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);

        if (mLocationPermissionsGranted){
            locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    location_present = location;
                    double latitude = -12.0550758;
                    double longitude = -77.0384442;
                    mMap.clear();
                    LatLng prueba = new LatLng(latitude,longitude);
//                    moveCamera(new LatLng(location.getLatitude(),location.getLongitude()),ZOOM_CAMERA);
                    moveCamera(prueba,ZOOM_CAMERA);
                    getPresenter().getBranchOffice(prueba);
//                    getPresenter().getBranchOffice(new LatLng(location.getLatitude(),location.getLongitude()));
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
    public void setBranchOffice(BranchOfficeList branchOfficeList){
        final List<BranchOffice> branchOffices =  branchOfficeList.getSucursalesCercanas();
        int sizeBranchOffice = branchOfficeList.getSucursalesCercanas().size();

        if (firstChargeView){
            Log.d("Primera carga:","Si");
            for (int i = 0; i< sizeBranchOffice; i++) {

                final int i_value = i;

                CustomInfoWindowGoogleMap customInfoWindowGoogleMap = new CustomInfoWindowGoogleMap(getActivity());
                mMap.setInfoWindowAdapter(customInfoWindowGoogleMap);

                String snippet = "Distrito: " + branchOffices.get(i).getNombreDistrito() + "\n" +
                        "Direccion: " + branchOffices.get(i).getDireccion();

                LatLng positionMarker = new LatLng(branchOffices.get(i).getLatitud(), branchOffices.get(i).getLongitud());

                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions
                        .position(positionMarker)
                        .title(branchOffices.get(i).getNombreEmpresa())
                        .snippet(snippet)
                        .icon((BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

                Marker m = mMap.addMarker(markerOptions);

                mapMarkers.put(m.getId(),positionMarker);

                mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(Marker marker) {
                        Intent intent = new Intent(getActivity(), EnterpriseActivity.class);
                        intent.putExtra("idSucursal",""+branchOffices.get(i_value).getIdSucursal());
                        startActivity(intent);
                    }
                });

                firstChargeView = false;
            }
        }else{

            for (int i = 0; i< sizeBranchOffice; i++) {
                Log.d("Primera carga:","No");
                final int i_value = i;

                CustomInfoWindowGoogleMap customInfoWindowGoogleMap = new CustomInfoWindowGoogleMap(getActivity());
                mMap.setInfoWindowAdapter(customInfoWindowGoogleMap);

                String snippet = "Distrito: " + branchOffices.get(i).getNombreDistrito() + "\n" +
                        "Direccion: " + branchOffices.get(i).getDireccion();

                LatLng positionMarker = new LatLng(branchOffices.get(i).getLatitud(), branchOffices.get(i).getLongitud());

                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions
                        .position(positionMarker)
                        .title(branchOffices.get(i).getNombreEmpresa())
                        .snippet(snippet)
                        .icon((BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

                Marker m = mMap.addMarker(markerOptions);

                mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(Marker marker) {
                        Intent intent = new Intent(getActivity(), EnterpriseActivity.class);
                        intent.putExtra("idSucursal",""+branchOffices.get(i_value).getIdSucursal());
                        startActivity(intent);
                    }
                });
            }
        }
    }

    @Override
    public void showLoadingDialog() {
        mProgressDialog.show();
    }

    @Override
    public void hideLoadingDialog() {
        mProgressDialog.hide();
    }

    @Override
    public void showConnectionError() {
        Snackbar.make(getView(),getResources().getString(R.string.error_connect),Snackbar.LENGTH_LONG).show();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        marker.showInfoWindow();
        return true;
    }


}
