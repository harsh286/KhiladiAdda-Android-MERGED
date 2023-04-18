package com.khiladiadda.gameleague;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.utility.AppConstant;

import butterknife.BindView;

public class GameWebActivity extends BaseActivity {
    @BindView(R.id.web_view)
    WebView webViewGame;
    private String iD;
    private String tournamentid;
    public String refNo;
    private String gameurl;

    @Override
    protected int getContentView() {
        return R.layout.activity_game_web;
    }

    @Override
    protected void initViews() {
        getUrlFromSettings();
        LocalBroadcastManager.getInstance(this).registerReceiver(mGameDroidoNotificationReceiver, new IntentFilter(AppConstant.GAME_DROIDO_CLASS_PACKAGE));
    }

    private final BroadcastReceiver mGameDroidoNotificationReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String data = intent.getStringExtra(AppConstant.FROM);
            if (data.equalsIgnoreCase(AppConstant.DROIDO)) {
                Intent intLeaderboard = new Intent(GameWebActivity.this, GamesFinalResultActivity.class);
                intLeaderboard.putExtra("id", iD);
                intLeaderboard.putExtra("tournamentid", tournamentid);
                startActivity(intLeaderboard);
                finish();
            }
        }
    };

    public void getUrlFromSettings() {
        Intent intLeaderboard = getIntent();
        gameurl = intLeaderboard.getStringExtra("gameurl");
        iD = intLeaderboard.getStringExtra("id");
        tournamentid = intLeaderboard.getStringExtra("tournamentid");
        refNo = intLeaderboard.getStringExtra("refNo");
        webViewGame.getSettings().setJavaScriptEnabled(true);
        webViewGame.getSettings().setDomStorageEnabled(true);
        webViewGame.getSettings().setBuiltInZoomControls(false);
        String gameurl_new = "https://prod.freakx.in/khiladi-adda/droid-o/v4/index.html";
        webViewGame.loadUrl(gameurl_new + "?" + "userid" + "=" + iD + "&" + "tournamentid" + "=" + tournamentid + "&" + "refno" + "=" + refNo + "&stage=prod&env=prod&partnerName=khiladiAdda&gameName=droid-o-online&adChannelId=khiladiaddauat");
//        webViewGame.loadUrl(gameurl + "?" + "userid" + "=" + iD + "&" + "tournamentid" + "=" + tournamentid + "&" + "refno" + "=" + refNo);
    }

    @Override
    public void onBackPressed() {
        showDeletePopup();
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

}