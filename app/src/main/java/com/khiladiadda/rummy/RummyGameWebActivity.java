package com.khiladiadda.rummy;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import com.google.gson.Gson;
import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.dialogs.AppDialog;
import com.khiladiadda.dialogs.interfaces.IOnNetworkErrorListener;
import com.khiladiadda.network.model.response.RummyGameModel;
import com.khiladiadda.utility.NetworkStatus;
import org.json.JSONException;
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
    protected void initViews(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            WebView.setWebContentsDebuggingEnabled(true);
        }
        webViewGame.addJavascriptInterface(new JsObject(), "Android");
        webViewGame.getSettings().setJavaScriptEnabled(true);
        webViewGame.getSettings().setDomStorageEnabled(true);
        webViewGame.getSettings().setBuiltInZoomControls(false);
        webViewGame.getSettings().setMediaPlaybackRequiresUserGesture(false);
        getUrlFromSettings();
    }

    public void getUrlFromSettings() {
        Intent intLeaderboard = getIntent();
        info = intLeaderboard.getStringExtra("info");
//        gameurl = "https://playmagicrummy.com/build/webbuild/khiladiAdda/debug/web-mobile/index.html?info=" + info;
          gameurl = mAppPreference.getVersion().getResponse().getRummyLink() + "?info=" + info;
//        gameurl = "https://playmagicrummy.com/build/webbuild/khiladiAdda/debug/web-mobile/index.html?info=" + info + ",pb=ka";
        webViewGame.loadUrl(gameurl);
    }

    @Override
    public void onBackPressed() {
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

    class JsObject implements IOnNetworkErrorListener {
        @JavascriptInterface
        public void receiveMessage(String data) throws JSONException {
            Log.i("JsObject", "new postMessage data= " + data);
            RummyGameModel filteredData = new Gson().fromJson(data,RummyGameModel.class);
            if (filteredData.getRedirectionType().equals("exit") && filteredData.getRedirectionParams().getRedirectionUrl() != null &&
                    filteredData.getRedirectionParams().getRedirectionUrl().equals("addMoney")){
             /*AppDialog.showRummyRechargeMsg(RummyGameWebActivity.this, "Recharge Now");*/
                 AppDialog.showInsufficientRummyDialog(RummyGameWebActivity.this);
                Log.e("TAG", "receiveMessage: ");
            } else if (filteredData.getRedirectionType().equals("RELOAD")){
                finish();
                Intent intLeaderboard=new Intent(RummyGameWebActivity.this,RummyGameWebActivity.class);
                intLeaderboard.putExtra("info", info);
                startActivity(intLeaderboard);
            } else if (filteredData.getRedirectionType().equals("exit")||filteredData.getRedirectionType().equals("EXIT")) {
                finish();
            }
        }

        @Override
        public void onRetry() {
            if (new NetworkStatus(RummyGameWebActivity.this).isInternetOn()) {
                finish();
                startActivity(new Intent(RummyGameWebActivity.this, RummyGameWebActivity.class));
            } else {
                AppDialog.showNetworkErrorDialog(RummyGameWebActivity.this, this);
            }
        }
    }

}