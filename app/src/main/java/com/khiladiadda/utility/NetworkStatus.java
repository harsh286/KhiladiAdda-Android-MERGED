package com.khiladiadda.utility;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * This class is a Network Status to check whether your Device is connected to Internet or not.
 * Author Harsh
 */
public class NetworkStatus {
    private Context context;

    public NetworkStatus(Context context) {
        this.context = context;
    }

    public boolean isInternetOn() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            return true;
        }
        return false;
    }

    public void showInternetAlertDialog() {
        new AlertDialog.Builder(context).setMessage("No Internet Connection").setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }

    public Boolean isOnline() {
        try {
            Process p1 = Runtime.getRuntime().exec("ping -c 1 www.google.com");
            int returnVal = p1.waitFor();
            boolean reachable = (returnVal == 0);
            if (reachable) {
                System.out.println("Internet accessed");
                return reachable;
            } else {
                System.out.println("No Internet access");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}