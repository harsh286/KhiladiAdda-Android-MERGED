package com.khiladiadda.fcm;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;

import java.util.List;

import butterknife.BindView;

public class NotificationHelpActivity extends BaseActivity {

    @BindView(R.id.iv_back) ImageView mBackIV;
    @BindView(R.id.tv_activity_name) TextView mActivityNameTV;
    @BindView(R.id.btn_notify) Button mNotifyBTN;

    @Override protected int getContentView() {
        return R.layout.activity_notification_help;
    }

    @Override protected void initViews() {
    }

    @Override protected void initVariables() {
        mNotifyBTN.setOnClickListener(this);
        mActivityNameTV.setText(R.string.menu_notification);
        mBackIV.setOnClickListener(this);
    }

    @Override public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_notify:
                String manufacturer = android.os.Build.MANUFACTURER;
                try {
                    Intent intent = new Intent();
                    if ("xiaomi".equalsIgnoreCase(manufacturer)) {
                        intent.setComponent(new ComponentName("com.miui.securitycenter", "com.miui.permcenter.autostart.AutoStartManagementActivity"));
                    } else if ("oppo".equalsIgnoreCase(manufacturer)) {
                        intent.setComponent(new ComponentName("com.coloros.safecenter", "com.coloros.safecenter.permission.startup.StartupAppListActivity"));
                    } else if ("vivo".equalsIgnoreCase(manufacturer)) {
                        intent.setComponent(new ComponentName("com.vivo.permissionmanager", "com.vivo.permissionmanager.activity.BgStartUpManagerActivity"));
                    } else if ("Letv".equalsIgnoreCase(manufacturer)) {
                        intent.setComponent(new ComponentName("com.letv.android.letvsafe", "com.letv.android.letvsafe.AutobootManageActivity"));
                    } else if ("Honor".equalsIgnoreCase(manufacturer)) {
                        intent.setComponent(new ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.optimize.process.ProtectActivity"));
                    }
                    List<ResolveInfo> list = getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
                    if (list.size() > 0) {
                        startActivity(intent);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

}