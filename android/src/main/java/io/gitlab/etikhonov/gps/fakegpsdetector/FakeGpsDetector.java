package io.gitlab.etikhonov.gps.fakegpsdetector;

import android.Manifest;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.location.LocationManagerCompat;
import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;

public class FakeGpsDetector {

    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;

    public void checkMockLocation(Context context, PluginCall pluginCall, boolean enableHighAccuracy, int timeout) {
        int statusCode = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context);
        if (statusCode == ConnectionResult.SUCCESS) {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);

            LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

            if (LocationManagerCompat.isLocationEnabled(locationManager)) {
                boolean networkEnabled = false;

                try {
                    networkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
                } catch (Exception ignored) {}

                int lowPriority = networkEnabled ? Priority.PRIORITY_BALANCED_POWER_ACCURACY : Priority.PRIORITY_LOW_POWER;
                int priority = enableHighAccuracy ? Priority.PRIORITY_HIGH_ACCURACY : lowPriority;

                LocationRequest locationRequest = new LocationRequest.Builder(10000)
                    .setMaxUpdateDelayMillis(timeout)
                    .setMinUpdateIntervalMillis(5000)
                    .setPriority(priority)
                    .build();

                locationCallback =
                    new LocationCallback() {
                        @Override
                        public void onLocationResult(@NonNull LocationResult locationResult) {
                            Location lastLocation = locationResult.getLastLocation();
                            if (lastLocation == null) {
                                pluginCall.reject("location unavailable");
                            } else {
                                JSObject result = new JSObject();
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                                    result.put("isEnabled", lastLocation.isMock());
                                } else {
                                    result.put("isEnabled", lastLocation.isFromMockProvider());
                                }
                                pluginCall.resolve(result);
                                if (locationCallback != null) {
                                    fusedLocationClient.removeLocationUpdates(locationCallback);
                                    locationCallback = null;
                                }
                            }
                        }
                    };

                if (
                    ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED
                ) {
                    pluginCall.reject("LOCATION Permissions NOT grunted!");
                }
                fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
            } else {
                pluginCall.reject("location disabled");
            }
        } else {
            pluginCall.reject("Google Play Services not available");
        }
    }
}
