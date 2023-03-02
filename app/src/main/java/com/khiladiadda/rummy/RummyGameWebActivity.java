package com.khiladiadda.rummy;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.gson.Gson;
import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.dialogs.AppDialog;
import com.khiladiadda.gameleague.GamesFinalResultActivity;
import com.khiladiadda.network.model.response.RummyGameModel;
import com.khiladiadda.utility.AppConstant;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;

public class RummyGameWebActivity extends BaseActivity {
    @BindView(R.id.web_view)
    WebView webViewGame;
    private String info;
    private String gameurl;

    @Override
    protected int getContentView() {
        return R.layout.activity_game_web;
    }

    @Override
    protected void initViews() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
        getUrlFromSettings();
        webViewGame.getSettings().setJavaScriptEnabled(true);
        LocalBroadcastManager.getInstance(this).registerReceiver(mGameDroidoNotificationReceiver, new IntentFilter(AppConstant.GAME_DROIDO_CLASS_PACKAGE));
        webViewGame.addJavascriptInterface(new JsObject(), "Android");
    }


    private final BroadcastReceiver mGameDroidoNotificationReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
//            String data = intent.getStringExtra(AppConstant.FROM);
//            if (data.equalsIgnoreCase(AppConstant.DROIDO)) {
//                Intent intLeaderboard = new Intent(RummyGameWebActivity.this, GamesFinalResultActivity.class);
//                intLeaderboard.putExtra("id", iD);
//                intLeaderboard.putExtra("tournamentid", tournamentid);
//                startActivity(intLeaderboard);
//                finish();
//            }
        }
    };

    public void getUrlFromSettings() {
        Intent intLeaderboard = getIntent();
        info = intLeaderboard.getStringExtra("info");
        webViewGame.getSettings().setJavaScriptEnabled(true);
        webViewGame.getSettings().setDomStorageEnabled(true);
        webViewGame.getSettings().setBuiltInZoomControls(false);
//        gameurl = "https://playmagicrummy.com/build/webbuild/khiladiAdda/debug/web-mobile/index.html?info=" + info;
        gameurl = "https://playmagicrummy.com/build/webbuild/khiladiAdda/debug/web-mobile/index.html?info=" + info + ",pb=ka";
        webViewGame.loadUrl(gameurl);
    }

    @Override
    public void onBackPressed() {
//        showDeletePopup();
        sendDataToWebView("back");

    }

    private void sendDataToWebView(String data) {
        webViewGame.evaluateJavascript("javascript: " + "updateFromNative(\"" + data + "\")", null);
        Log.e("jswrapper", "sendDataToWebView: " + data);
    }

    private void showDeletePopup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Alert");
        builder.setMessage("Do you want to exit? If you exit, you loose your 1 attempt.");
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

    class JsObject {
        @JavascriptInterface
        public void receiveMessage(String data) throws JSONException {
            Log.i("JsObject", "new postMessage data= " + data);
            RummyGameModel filteredData = new Gson().fromJson(data, RummyGameModel.class);
            if (filteredData.getRedirectionType().equals("exit") && filteredData.getRedirectionParams().getRedirectionUrl() != null &&
                    filteredData.getRedirectionParams().getRedirectionUrl().equals("addMoney")) {
                AppDialog.showRummyRechargeMsg(RummyGameWebActivity.this, "Recharge Now");
                Log.e("TAG", "receiveMessage: " );
            }else if (filteredData.getRedirectionType().equals("RELOAD")) {
                webViewGame.getSettings().setBuiltInZoomControls(false);
                webViewGame.loadUrl(gameurl);
                finish();
            } else if (filteredData.getRedirectionType().equals("exit") || filteredData.getRedirectionType().equals("EXIT")) {
                finish();
            }
        }
    }

}