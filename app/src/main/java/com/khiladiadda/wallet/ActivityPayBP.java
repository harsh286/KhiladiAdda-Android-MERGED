package com.khiladiadda.wallet;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.JsResult;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.utility.AppConstant;

import java.net.URISyntaxException;

import butterknife.BindView;

public class ActivityPayBP extends BaseActivity {
    @BindView(R.id.web_view_bp)
    WebView webView;
    String url;

    @Override
    protected int getContentView() {
        return R.layout.activity_pay_bp;
    }

    @Override
    protected void initViews() {
        url = getIntent().getStringExtra(AppConstant.webUrl);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setBuiltInZoomControls(false);
        webView.loadUrl(url);
        webView.setWebChromeClient(new WebChromeClient() {
            public boolean onConsoleMessage(ConsoleMessage cMsg) {
                /* process JSON */
                cMsg.message();
                return true;
            }
        });
        webView.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(ActivityPayBP.this, getString(R.string.internet_connection_not_available), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                String inputUrl = url;
                if (inputUrl.contains("/paymentResponse")) {
                    Toast.makeText(getApplicationContext(), "Payment Added to BajajPay Wallet Successfully.", Toast.LENGTH_SHORT).show();
                    ActivityPayBP.this.finish();
                }
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (URLUtil.isNetworkUrl(url)) {
                    return false;
                }

//                Intent upiIntent = null;
//                try {
//                    upiIntent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
//                } catch (URISyntaxException e) {
//                    throw new RuntimeException(e);
//                }
//                startActivity(upiIntent);

//                TODO IMPLEMENT NEW INTENT

                if (url.startsWith("intent")) {
                    String extractedPackageName = url.substring((url.lastIndexOf(";package=") + 9), url.lastIndexOf(";S.browser_fallback_url="));
                    String extractedFallBackURL = url.substring((url.lastIndexOf("fallback_url=") + 13), url.lastIndexOf(";end"));
                    Intent upiIntent = null;
                    /**     * DETECT IF EXPECTED APP INSTALLED : START     * YES : GOTO APP     * NO : GOTO WEB BROWSER URL     * */
                    PackageManager pm = getPackageManager();
                    PackageInfo packageInfo = null;
                    try {
                        packageInfo = pm.getPackageInfo(extractedPackageName, PackageManager.GET_META_DATA);
                        if (packageInfo != null) {
                            try {
                                upiIntent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                                startActivity(upiIntent);
                            } catch (URISyntaxException e) {
                                e.printStackTrace();
                            }
                        } else {
                            upiIntent = new Intent(Intent.ACTION_VIEW);
                            upiIntent.setData(Uri.parse(extractedFallBackURL));
                            startActivity(upiIntent);
                        }
                    } catch (PackageManager.NameNotFoundException e) {
                        e.printStackTrace();
                        upiIntent = new Intent(Intent.ACTION_VIEW);
                        upiIntent.setData(Uri.parse(extractedFallBackURL));
                        startActivity(upiIntent);
                    }    /**     * DETECT IF EXPECTED APP INSTALLED : END     * YES : GOTO APP     * NO : GOTO WEB BROWSER URL     * */
                    return true;
                } else {
                    return false;
                }


//                if (appInstalledOrNot(url)) {
//                    Intent upiIntent = null;
//                    try {
//                        upiIntent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
//                    } catch (URISyntaxException e) {
//                        throw new RuntimeException(e);
//                    }
//                    startActivity(upiIntent);
//                } else {
//                    // do something if app is not installed
//                }
//                return true;
            }

        });
        webView.loadUrl(url);
    }

    private class MyWebChromeClient extends WebChromeClient {
        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            Log.d("LogTag", message);
            result.confirm();
            return true;
        }
    }

    @Override
    public void onBackPressed() {
        showDeletePopup();
    }

    private void showDeletePopup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Alert");
        builder.setMessage("Do you want to exit? If you exit");
        builder.setNegativeButton("NO", (dialog, which) -> {
        });
        builder.setPositiveButton("YES", (dialog, which) -> finish());
        builder.show();
    }

    @Override
    protected void initVariables() {

    }

    @Override
    public void onClick(View view) {

    }

    private boolean appInstalledOrNot(String uri) {
        PackageManager pm = getPackageManager();
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
        }
        return false;
    }

}