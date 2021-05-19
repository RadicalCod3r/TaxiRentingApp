package com.example.taxirentingapp;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class UserMapActivity extends FragmentActivity implements OnMapReadyCallback{
    private final int ID_ACCOUNT = 1;
    private final int ID_ORDER = 2;
    private final int ID_HISTORY = 3;
    private MeowBottomNavigation navigation;
    private GoogleMap mMap;
    private static final String TAG = "UserMapActivity";
    private Marker userMarker;
    private Marker destinationMarker;
    private Location userLocation;
    private Location destinationLocation;
    private Button selectDepDesBtn;
    private TextView locationErrorTxt;
    private TextView distanceTxt;
    private int clickCounter;
    private final int REQUEST_CODE = 101;
    private FusedLocationProviderClient fusedLocationProviderClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        selectDepDesBtn = (Button)findViewById(R.id.select_pickup_destination_btn);
        distanceTxt = (TextView)findViewById(R.id.distance_txt);
        locationErrorTxt = (TextView)findViewById(R.id.location_disabled_error);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        navigation = (MeowBottomNavigation) findViewById(R.id.bottom_navigation);
        navigation.add(new MeowBottomNavigation.Model(ID_ACCOUNT, R.drawable.ic_baseline_account_circle_24));
        navigation.add(new MeowBottomNavigation.Model(ID_ORDER, R.drawable.ic_baseline_directions_car_24));
        navigation.add(new MeowBottomNavigation.Model(ID_HISTORY, R.drawable.ic_baseline_history_24));
        navigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
                switch (item.getId()) {
                    case ID_ACCOUNT:
                        Intent accountIntent = new Intent(UserMapActivity.this, ProfileActivity.class);
                        startActivity(accountIntent);
                        //the line below is the fucking magic
                        overridePendingTransition(0, 0);
                        break;
                }
            }
        });
        navigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                String name = "";
                switch (item.getId()) {
                    case ID_ACCOUNT:
                        name = "Account";
                        break;

                    case ID_ORDER:
                        name = "Order";
                        break;

                    case ID_HISTORY:
                        name = "History";
                        break;
                }
            }
        });
        navigation.setCount(ID_ACCOUNT, "2");
        navigation.show(ID_ORDER, true);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    private void zoomInLocation(Location location){
        userMarker = mMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(),location.getLongitude())).icon(BitmapDescriptorFactory.fromResource(R.drawable.pickup_icon)).title("You"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(),location.getLongitude()),18));
    }
    private void setupLocationManager() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
            return;
        }
        try{
            Task<Location> locationResult = fusedLocationProviderClient.getLastLocation();
            locationResult.addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    if(task.isSuccessful()){
                        Location lastKnownLocation = task.getResult();
                        if(lastKnownLocation != null){
                            userLocation = lastKnownLocation;
                            zoomInLocation(userLocation);
                        }
                    }else{
                        Log.d(TAG, "Current location is null. Using defaults.");
                        Log.e(TAG, "Exception: %s", task.getException());
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(35.6892,51.3890),10));
                    }
                }
            });
        }catch (SecurityException e){
            Log.i(TAG, "setupLocationManager: " + e.getMessage());
        }
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.mapstyle));

            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }
        clickCounter = 0;
        selectDepDesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCounter++;
                if(clickCounter == 1){
                    selectDepDesBtn.setText("SELECT DESTINATION");
                    mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                        @Override
                        public void onMapLongClick(LatLng latLng) {
                            if(destinationMarker!=null){
                                destinationMarker.remove();
                            }
                            destinationMarker = mMap.addMarker(new MarkerOptions().position(latLng).title("Your Destination").icon(BitmapDescriptorFactory.fromResource(R.drawable.destination)));
                            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng,18);
                            mMap.animateCamera(cameraUpdate);
                            destinationLocation = new Location(LocationManager.GPS_PROVIDER);
                            destinationLocation.setLatitude(latLng.latitude);
                            destinationLocation.setLongitude(latLng.longitude);
                            OnBackPressedCallback callback1 = new OnBackPressedCallback(true) {
                                @Override
                                public void handleOnBackPressed() {
                                    clickCounter = 0;
                                    selectDepDesBtn.setText("SELECT DEPARTURE");
                                }
                            };
                            getOnBackPressedDispatcher().addCallback(callback1);
                        }
                    });
                }else if(clickCounter == 2){
                    CameraUpdate cameraUpdate = CameraUpdateFactory.zoomTo(15);
                    mMap.animateCamera(cameraUpdate);
                    final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(UserMapActivity.this,R.style.BottomSheetDialogTheme);
                    View bottomSheetLayout = LayoutInflater.from(UserMapActivity.this).inflate(R.layout.layout_bottom_sheet,(LinearLayout)findViewById(R.id.bottom_sheet_container));
                    Button rentBtn = bottomSheetLayout.findViewById(R.id.rent_btn);
                    rentBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
                    bottomSheetDialog.setContentView(bottomSheetLayout);
                    bottomSheetDialog.show();
                    OnBackPressedCallback callback2 = new OnBackPressedCallback(true) {
                        @Override
                        public void handleOnBackPressed() {
                            clickCounter = 1;
                            if(bottomSheetDialog!=null)
                                bottomSheetDialog.dismiss();
                        }
                    };
                    getOnBackPressedDispatcher().addCallback(callback2);
                }else{
                    final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(UserMapActivity.this,R.style.BottomSheetDialogTheme);
                    View bottomSheetLayout = LayoutInflater.from(UserMapActivity.this).inflate(R.layout.layout_bottom_sheet,(LinearLayout)findViewById(R.id.bottom_sheet_container));
                    Button rentBtn = bottomSheetLayout.findViewById(R.id.rent_btn);
                    rentBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
                    bottomSheetDialog.setContentView(bottomSheetLayout);
                    bottomSheetDialog.show();
                    OnBackPressedCallback callback3 = new OnBackPressedCallback(true) {
                        @Override
                        public void handleOnBackPressed() {
                            clickCounter = 1;
                            if(bottomSheetDialog!=null)
                                bottomSheetDialog.dismiss();
                        }
                    };
                    getOnBackPressedDispatcher().addCallback(callback3);
                }
                Log.i(TAG, "onMapReady: " + clickCounter);
            }
        });
        setupLocationManager();
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                if(userMarker!=null){
                    userMarker.remove();
                }
                userLocation.setLatitude(latLng.latitude);
                userLocation.setLongitude(latLng.longitude);
                zoomInLocation(userLocation);
            }
        });

        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    /*@Override
    public void onLocationChanged(@NonNull Location location) {
        if(location!=null && mMap!=null){
            userLocation = location;
            LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
            if(userMarker!=null){
                userMarker.remove();
            }
            userMarker = mMap.addMarker(new MarkerOptions().position(latLng).title("You").icon(BitmapDescriptorFactory.fromResource(R.drawable.pickup_icon)));
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng,18);
            mMap.animateCamera(cameraUpdate);
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
        locationErrorTxt.setVisibility(View.GONE);
        setupLocationManager();
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        if(provider.equals(LocationManager.GPS_PROVIDER)){
            locationErrorTxt.setVisibility(View.VISIBLE);
            locationErrorTxt.setText("CHECK IF YOUR LOCATION IS ENABLED");
        }
    }
*/
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==REQUEST_CODE){
            if(grantResults[0]==0){
                setupLocationManager();
            }
        }
    }
}