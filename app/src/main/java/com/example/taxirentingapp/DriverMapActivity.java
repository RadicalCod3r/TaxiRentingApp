package com.example.taxirentingapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.location.LocationManagerCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DriverMapActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {
    private static final int REQUEST_CHECK_SETTINGS = 101;
    //private LocationBroadcastReceiver locationBroadcastReceiver;
    private GoogleMap mMap;
    private static final String TAG = "DriverMapActivity";
    private CoordinatorLayout locationSnackCoordinator, wifiSnackCoordinator;
    private Snackbar locationSnack, networkSnack;
    //private ConnectivityBroadcastReceiver connectivityBroadcastReceiver;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 101;

    private LocationRequest mLocationRequest;
    boolean isOnline = true;
    boolean enabledLocation = true;

    private Marker pickupMarker;
    private Marker destinationMarker;
    private Location pickupLocation;
    private Location destinationLocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(this);


    }

    private void setupLocationManager() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return; //Exit function
        }
        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 4000, 5, this);
            Location lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            onLocationChanged(lastLocation);
        }
    }

    static public void rotateMarker(final Marker marker, final float toRotation, GoogleMap map) {
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        final float startRotation = marker.getRotation();
        final long duration = 1555;

        final Interpolator interpolator = new LinearInterpolator();

        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = interpolator.getInterpolation((float) elapsed / duration);

                float rot = t * toRotation + (1 -t) * startRotation;

                marker.setRotation(-rot > 180 ? rot/2 : rot);
                if (t < 1.0) {
                    // Post again 16ms later.
                    handler.postDelayed(this, 16);
                }
            }
        });
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
        setupLocationManager();
        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    /*@Override
    protected void onStart() {
        super.onStart();
        locationBroadcastReceiver = new LocationBroadcastReceiver();
        connectivityBroadcastReceiver = new ConnectivityBroadcastReceiver();
        wifiSnackCoordinator = (CoordinatorLayout)findViewById(R.id.wifi_snack_coordinator);
        locationSnackCoordinator = (CoordinatorLayout)findViewById(R.id.location_snack_coordinator);
        LocationManager lm = (LocationManager)getSystemService(LOCATION_SERVICE);
        if(!LocationManagerCompat.isLocationEnabled(lm)){
            locationSnack = Snackbar.make(locationSnackCoordinator,"Your Location is unavailable.", BaseTransientBottomBar.LENGTH_INDEFINITE)
                    .setAction("Turn on GPS", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            createLocationRequest();
                        }
                    });
            locationSnack.show();
        }
        registerReceiver(locationBroadcastReceiver,new IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION));
        registerReceiver(connectivityBroadcastReceiver,new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(locationBroadcastReceiver);
        if(locationSnack != null)
            locationSnack.dismiss();
        if(networkSnack != null)
            networkSnack.dismiss();
    }*/


    /*private class LocationBroadcastReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            LocationManager lm = (LocationManager)getSystemService(LOCATION_SERVICE);
            boolean gpsEnabled = false;
            boolean networkEnabled = false;

            try{
                gpsEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);

            }catch (Exception e){

            }
            try{
                networkEnabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            }catch (Exception e){

            }

            if(!gpsEnabled && !networkEnabled){
                    locationSnack = Snackbar.make(locationSnackCoordinator,"Your Location is unavailable.", BaseTransientBottomBar.LENGTH_INDEFINITE)
                            .setAction("Turn on GPS", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    createLocationRequest();
                                }
                            });
                    locationSnack.show();
                    enabledLocation = false;
            }else{
                if(locationSnack!=null){
                    locationSnack.dismiss();
                    enabledLocation = true;
                }
            }
        }
    }*/
    protected void createLocationRequest() {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);
        SettingsClient client = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());
        task.addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                // All location settings are satisfied. The client can initialize
                // location requests here.
                // ...
                locationSnack.dismiss();
                enabledLocation = true;
            }
        });

        task.addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {

                    try {
                        ResolvableApiException resolvable = (ResolvableApiException) e;
                        resolvable.startResolutionForResult(DriverMapActivity.this,
                                REQUEST_CHECK_SETTINGS);
                    } catch (IntentSender.SendIntentException sendEx) {

                    }
                }
                enabledLocation = false;
            }
        });
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        if(location!=null && mMap!=null){
            LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
            mMap.addMarker(new MarkerOptions().position(latLng).title("You"));
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng,20);
            mMap.animateCamera(cameraUpdate);
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }
    /*private class ConnectivityBroadcastReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager cm = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
            NetworkInfo info = cm.getActiveNetworkInfo();
            boolean isConnectd = info!=null && info.isConnectedOrConnecting();
            if(isConnectd){
                if(networkSnack != null){
                    networkSnack.dismiss();
                }
            }else{
                networkSnack = Snackbar.make(wifiSnackCoordinator,"You arent connected to the internet.",BaseTransientBottomBar.LENGTH_INDEFINITE);
                networkSnack.show();
                isOnline = false;
            }
        }
    }*/
}