package com.khiladiadda.wallet;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AlertDialog;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.material.snackbar.Snackbar;
import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.utility.NetworkStatus;

import butterknife.BindView;

public class WebPaymentActivity extends BaseActivity {
    @BindView(R.id.paymentWebView)
    WebView mWebView;
    private boolean apex;

    @Override
    protected int getContentView() {
        return R.layout.activity_web_payment;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LocalBroadcastManager.getInstance(this).registerReceiver(mWebPayment, new IntentFilter("com.khiladiadda.WEBPAYMENT_NOTIFY"));
    }

    @Override
    protected void initViews() {
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setJavaScriptEnabled(true);
        String url = getIntent().getStringExtra("URL");
        if (new NetworkStatus(this).isInternetOn()) {
            showProgress("");
            mWebView.loadUrl(url);
        }else {
            Snackbar.make(mWebView, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
        }
        mWebView.setWebViewClient(new HelloWebViewClient());


    }


    @Override
    public void onBackPressed() {
      onBackPressedDialog();

    }

    @Override
    protected void initVariables() {

    }


    @Override
    public void onClick(View v) {

    }

    private final BroadcastReceiver mWebPayment = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            back();

        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        if (apex){
           back();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        apex=true;
    }

    private void back() {
        Intent resultIntent = new Intent();
        setResult(RESULT_OK, resultIntent);
        finish();
    }
    private void onBackPressedDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Do you want to Exit?");
        builder.setPositiveButton("Yes", (dialog, which) -> {
            //if user pressed "yes", then he is allowed to exit from application
            finish();
        });
        builder.setNegativeButton("No", (dialog, which) -> {
            //if user select "No", just cancel this dialog and continue with app
            dialog.cancel();
        });
        AlertDialog alert=builder.create();
        alert.show();
    }
    private  class HelloWebViewClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            hideProgress();
        }
    }

    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mWebPayment);
        super.onDestroy();
    }
}