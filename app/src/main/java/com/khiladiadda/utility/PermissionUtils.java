package com.khiladiadda.utility;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;

import com.khiladiadda.R;

public class PermissionUtils {

    public static boolean hasStoragePermission(final Activity activity) {
        int hasStoragePermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (hasStoragePermission != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                showMessageOKCancel(activity, "You need to allow access to external storage", AppConstant.REQUEST_GALLERY);
            }
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, AppConstant.RC_ASK_PERMISSIONS_STORAGE);
            return false;
        }
        return true;
    }

    public static boolean hasCameraPermission(final Activity activity) {
        int hasStoragePermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.CAMERA);
        if (hasStoragePermission != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.CAMERA)) {
                showMessageOKCancel(activity, "You need to allow access to camera.", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA}, AppConstant.RC_ASK_PERMISSIONS_CAMERA);
                    }
                });
                return false;
            }
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA}, AppConstant.RC_ASK_PERMISSIONS_CAMERA);
            return false;
        }
        return true;
    }

    public static boolean hasGpsPermission(final Activity activity) {
        int hasStoragePermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION);
        if (hasStoragePermission != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                showMessageOKCancel(activity, "You need to allow access to external storage", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, AppConstant.RC_ASK_PERMISSIONS_GPS);
                    }
                });
                return false;
            }
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, AppConstant.RC_ASK_PERMISSIONS_GPS);
            return false;
        }
        return true;
    }

    private static void showMessageOKCancel(Activity activity, String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(activity).setMessage(message).setPositiveButton("OK", okListener).setNegativeButton("Cancel", null).create().show();
    }

    public static void showMessageOKCancel(final Activity activity, String message, int from) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.dialog_delete);
        TextView tv_msg = (TextView) dialog.findViewById(R.id.tv_msg);
        tv_msg.setText(message);
        Button mOkBTN = (Button) dialog.findViewById(R.id.btn_ok);
        mOkBTN.setText(R.string.text_yes);
        mOkBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                dialog.dismiss();
                if (from == AppConstant.REQUEST_GALLERY) {
                    ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, AppConstant.RC_ASK_PERMISSIONS_STORAGE);
                } else {
                    ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.SEND_SMS}, AppConstant.RC_ASK_PERMISSIONS_MSG);
                }
            }
        });
        Button mNoBTN = (Button) dialog.findViewById(R.id.btn_no);
        mNoBTN.setText(R.string.cancel);
        mNoBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    // For ReadSMS permission
    public static boolean hasSMSReadPermission(final Activity activity) {
        int hasSMSReadPermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_SMS);
        if (hasSMSReadPermission != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_SMS)) {
                showMessageOKCancel(activity, "You need to allow access to read sms for OTP.",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_SMS},
                                        AppConstant.RC_ASK_PERMISSIONS_MSG);
                            }
                        });
                return false;
            }
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_SMS}, AppConstant.RC_ASK_PERMISSIONS_MSG);
            return false;
        }
        return true;
    }

}