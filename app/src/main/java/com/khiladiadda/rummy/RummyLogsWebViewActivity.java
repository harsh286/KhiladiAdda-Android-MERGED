package com.khiladiadda.rummy;

import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;

import butterknife.BindView;

public class RummyLogsWebViewActivity extends BaseActivity {

    @BindView(R.id.wv_logs)
    WebView mLogsMv;
    @BindView(R.id.iv_close)
    ImageView mCloseIv;

    @Override
    protected int getContentView() {
        return R.layout.activity_rummy_logs_web_view;
    }

    @Override
    protected void initViews() {
        Log.e("TAG", "initViews: ==" + "https://mplay.reingames.com/replay?mid=" + getIntent().getStringExtra("mid"));
        mLogsMv.loadUrl("https://mplay.reingames.com/replay?mid=" + getIntent().getStringExtra("mid"));
        // this will enable the javascript.
        mLogsMv.getSettings().setJavaScriptEnabled(true);
        // WebViewClient allows you to handle
        // onPageFinished and override Url loading.
        mLogsMv.setWebViewClient(new WebViewClient());
    }

    @Override
    protected void initVariables() {
        mCloseIv.setOnClickListener(this);
    }

    @Override
    public void onClick(View p0) {
        if (p0.getId() == R.id.iv_close) {
            finish();
        }
    }

}