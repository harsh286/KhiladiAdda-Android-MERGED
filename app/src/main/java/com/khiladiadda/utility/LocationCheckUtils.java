package com.khiladiadda.utility;

import static com.khiladiadda.utility.AppConstant.RC_ASK_PERMISSIONS_GPS;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Looper;
import android.provider.Settings;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.khiladiadda.R;
import com.khiladiadda.preference.AppSharedPreference;

import java.util.List;
import java.util.Locale;

public class LocationCheckUtils {
    private Context context;
    private Activity activity;
    private IOnAdressPassed mIOnAdressPassed;
    private FusedLocationProviderClient mFusedLocationClient;
    private static LocationCheckUtils INSTANCE;
    private String mLatitute, mLongitude;

    public LocationCheckUtils(Context context, Activity activity, IOnAdressPassed mIOnAdressPassed) {
        this.context = context;
        this.activity = activity;
        this.mIOnAdressPassed = mIOnAdressPassed;
        AppSharedPreference.initialize(context);
    }

    public static void initialize(Context context, Activity activity, IOnAdressPassed mIOnAdressPassed) {
        if (INSTANCE == null) {
            synchronized (LocationCheckUtils.class) {
                INSTANCE = new LocationCheckUtils(context, activity, mIOnAdressPassed);
            }
        }
    }

    public static LocationCheckUtils getInstance() {
        if (INSTANCE == null) {
            throw new IllegalStateException("You have to call initialize() first then call getInstance().");
        }
        return INSTANCE;
    }

    /**
     * Start from here
     */
    public void getLastLocation() {
        if (checkPermissions()) {
            // check if location is enabled
            if (isLocationEnabled()) {
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    DialogWithCallBack(activity, "KhiladiAdda need to access your location.");
                    return;
                }
                mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        Location location = task.getResult();
                        if (location == null) {
                            requestNewLocationData();
                        } else {
                            checkAddress(location.getLatitude(), location.getLongitude());
                        }
                    }
                });
            } else {
                Toast.makeText(context, "Please turn on" + " your location...", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                context.startActivity(intent);
            }
        } else {
            // if permissions aren't available,
            // request for permissions
            requestPermissions();
        }
    }

    @SuppressLint("MissingPermission")
    public void requestNewLocationData() {
        // Initializing LocationRequest
        // object with appropriate methods
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(5);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);
        // setting LocationRequest
        // on FusedLocationClient
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
    }

    private LocationCallback mLocationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            checkAddress(mLastLocation.getLatitude(), mLastLocation.getLongitude());
        }
    };

    // method to check for permissions
    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    // method to request for permissions
    private void requestPermissions() {
        ActivityCompat.requestPermissions(activity, new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION}, RC_ASK_PERMISSIONS_GPS);
    }

    // method to check
// if location is enabled
    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    public boolean hasLocationPermission() {
        int hasStoragePermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION);
        return hasStoragePermission == PackageManager.PERMISSION_GRANTED;
    }


    public void statusCheck() {
        try {
            final LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
//            if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                buildAlertMessageNoGps();
            }
//            else {
//                getLastLocation();
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        try {
            builder.setMessage("Location Required\nWe need to insure this app is allowed in your state of residence.\nPlease allow to access your location for the first time.")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(final DialogInterface dialog, final int id) {
                            context.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }
                    });
            final AlertDialog alert = builder.create();
            alert.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkAddress(double latitude, double longitude) {
        Geocoder gcd = new Geocoder(context, Locale.getDefault());
        String state = "";
        String country = "";
        mLatitute = "" + latitude;
        mLongitude = "" + longitude;
        try {
            List<Address> addresses = gcd.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0) {
                state = addresses.get(0).getAdminArea();
                country = addresses.get(0).getCountryName();
            }
        } catch (Exception ignored) {
        }
        List<String> countries = AppSharedPreference.getInstance().getVersion().getAllowCountries();
        List<String> states = AppSharedPreference.getInstance().getVersion().getBannerStates();

        if (countries != null) {
            for (int i = 0; i < countries.size(); i++) {
                if (country.toLowerCase(Locale.ROOT).replaceAll("[^a-z]", "")
                        .equalsIgnoreCase(countries.get(i).toLowerCase(Locale.ROOT).replaceAll("[^a-z]", ""))) {
                    if (states != null) {
                        for (int j = 0; j < states.size(); j++) {
                            if (state.toLowerCase(Locale.ROOT).replaceAll("[^a-z]", "")
                                    .equalsIgnoreCase(states.get(j).toLowerCase(Locale.ROOT).replaceAll("[^a-z]", ""))) {
//                        DialogNotAllowed(activity, "You are not allowed to play skill-based real money gaming in your state.");
                                mIOnAdressPassed.iOnAddressFailure();
                            } else {
                                mIOnAdressPassed.iOnAddressSuccess();
                            }
                        }
                    }
                } else {
                    DialogNotAllowed(activity, "You are not allowed to play skill-based real money gaming in your country.");
                }
            }
        }
    }

    /**
     * END LOCATION
     */

    public interface IOnAdressPassed {
        public void iOnAddressSuccess();

        public void iOnAddressFailure();
    }

    public Dialog DialogWithCallBack(Activity activity, String message) {
        final Dialog dialog = new Dialog(activity);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.show_error_popup);
        TextView mMessageTV = dialog.findViewById(R.id.tv_error_message);
        mMessageTV.setText(message);
        Button mOkayTV = dialog.findViewById(R.id.btn_done);
        mOkayTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                activity.startActivity(new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.fromParts("package", activity.getPackageName(), null)));
            }
        });
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        return dialog;
    }

    public Dialog DialogNotAllowed(Activity activity, String message) {
        final Dialog dialog = new Dialog(activity);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.show_error_popup);
        TextView mMessageTV = dialog.findViewById(R.id.tv_error_message);
        mMessageTV.setText(message);
        Button mOkayTV = dialog.findViewById(R.id.btn_done);
        mOkayTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        return dialog;
    }

    public String getmLatitute() {
        return mLatitute;
    }

    public String getmLongitude() {
        return mLongitude;
    }

}
