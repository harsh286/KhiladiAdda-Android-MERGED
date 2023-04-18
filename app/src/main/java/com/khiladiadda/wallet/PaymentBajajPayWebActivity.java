package com.khiladiadda.wallet;

import android.annotation.SuppressLint;
import android.content.Context;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class PaymentBajajPayWebActivity extends WebView {
    public PaymentBajajPayWebActivity(Context context) {
        super(context);
        setup();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void setup() {
        setWebViewClient(new AdWebViewClient());
        getSettings().setJavaScriptEnabled(true);
    }

    private class AdWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.startsWith("customchema://")) {
                Toast.makeText(getContext(), "event recieved", Toast.LENGTH_SHORT).show();
                return true;
            }
            return false;
        }
    }
}
