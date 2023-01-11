package com.khiladiadda.fcm;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.fcm.adapter.NotificationRVAdapter;
import com.khiladiadda.fcm.interfaces.IFCMPresenter;
import com.khiladiadda.fcm.interfaces.IFCMView;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.BaseResponse;
import com.khiladiadda.network.model.request.NotificationModel;
import com.khiladiadda.utility.AppConstant;
import com.khiladiadda.utility.AppUtilityMethods;

import java.util.ArrayList;
import java.util.Set;

import butterknife.BindView;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class NotificationActivity extends BaseActivity implements IFCMView, NotificationRVAdapter.IOnCheckListener {

    @BindView(R.id.iv_back)
    ImageView mBackIV;
    @BindView(R.id.tv_activity_name)
    TextView mActivityNameTV;
    @BindView(R.id.iv_delete)
    ImageView mDeleteIV;
    @BindView(R.id.iv_checkbox)
    ImageView mCheckAllCB;
    @BindView(R.id.rv_notification)
    RecyclerView mNotificationRV;
    @BindView(R.id.tv_issue)
    TextView mNotifyTV;
    @BindView(R.id.tv_league_notification)
    TextView mLeagueNotifcationTV;

    private NotificationRVAdapter mAdapter;
    private ArrayList<NotificationModel> mList;
    private int mFrom;
    private boolean mIsSelectAllChecked;
    private IFCMPresenter mPreseneter;
    private boolean mIsStatus;

    @Override
    protected int getContentView() {
        return R.layout.activity_notification;
    }

    @Override
    protected void initViews() {
        mActivityNameTV.setText(R.string.menu_notification);
        mFrom = getIntent().getIntExtra(AppConstant.FROM, -1);
        mAppPreference.setNotificationCount(0);
        mBackIV.setOnClickListener(this);
        mDeleteIV.setOnClickListener(this);
        mCheckAllCB.setOnClickListener(this);
        mNotifyTV.setOnClickListener(this);
        mLeagueNotifcationTV.setOnClickListener(this);
    }

    @Override
    protected void initVariables() {
        mPreseneter = new FCMPresenter(this);
        mList = new ArrayList<>();
        mAdapter = new NotificationRVAdapter(this, mList);
        mNotificationRV.setLayoutManager(new LinearLayoutManager(this));
        mNotificationRV.setAdapter(mAdapter);
        if(mAppPreference.getDashboardData().isNotificationDisabled()){
           mLeagueNotifcationTV.setText(R.string.text_start_notification);
        }else{
            mLeagueNotifcationTV.setText(R.string.text_stop_notifcation);
        }
        getNotifications();
    }

    private void getNotifications() {
        mList.clear();
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> {
            RealmResults<NotificationModel> notificationModels = realm1.where(NotificationModel.class).sort("mId", Sort.DESCENDING).findAll();
            for (NotificationModel notificationModel : notificationModels) {
                mList.add(new NotificationModel(notificationModel));
            }
            mAdapter.notifyDataSetChanged();
        });
        if (mList.size() > 0) {
            mCheckAllCB.setVisibility(View.VISIBLE);
            mDeleteIV.setVisibility(View.VISIBLE);
        } else {
            mCheckAllCB.setVisibility(View.GONE);
            mDeleteIV.setVisibility(View.GONE);
        }
        AppUtilityMethods.deleteCache(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                if (mFrom == AppConstant.FROM_NOTIFICATION) {
                    Intent intent = new Intent();
                    setResult(AppConstant.REQUEST_NOTIFICATION, intent);
                }
                finish();
                break;
            case R.id.iv_checkbox:
                if (mIsSelectAllChecked) {
                    mAdapter.uncheckAll();
                    mIsSelectAllChecked = false;
                    mCheckAllCB.setImageResource(R.drawable.checkbox_blank);
                } else {
                    mAdapter.checkAll();
                    mIsSelectAllChecked = true;
                    mCheckAllCB.setImageResource(R.drawable.checkbox_checked);
                }
                break;
            case R.id.iv_delete:
                Realm realm = Realm.getDefaultInstance();
                realm.executeTransaction(realm1 -> {
                    Set<Integer> keySet = mAdapter.getSelectedMap().keySet();
                    for (int key : keySet) {
                        RealmResults<NotificationModel> results = realm1.where(NotificationModel.class).equalTo("mId", key).findAll();
                        if (!results.isEmpty()) {
                            results.deleteAllFromRealm();
                        }
                    }
                });
                mCheckAllCB.setImageResource(R.drawable.checkbox_blank);
                mAdapter.getSelectedMap().clear();
                getNotifications();
                break;
            case R.id.tv_issue:
                startActivity(new Intent(this, NotificationHelpActivity.class));
                break;
            case R.id.tv_league_notification:
                if(mAppPreference.getDashboardData().isNotificationDisabled()){
                    mIsStatus = false;
                    showMsg("You will receive all notifications and notifications related to Leagues(Freefire, Freefire Max, Freefire ClashSquad, Esports Premium, etc.)");
                } else{
                    mIsStatus = true;
                    showMsg("You will not receive any notifications related to Leagues(Freefire, Freefire Max, Freefire ClashSquad, Esports Premium, etc.).\n You will get notifications other than league.");
                }
                break;
        }
    }

    @Override
    public void setChecked(boolean isChecked) {
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mFrom == AppConstant.FROM_NOTIFICATION) {
            Intent intent = new Intent();
            setResult(AppConstant.REQUEST_NOTIFICATION, intent);
            finish();
        } else {
            finish();
        }
    }

    @Override
    public void onFcmUpdateComplete(BaseResponse responseModel) {
        hideProgress();
        AppUtilityMethods.showMsg(this, "Notification changes has been successful", false);
    }

    @Override
    public void onFcmUpdateFailure(ApiError error) {
        hideProgress();
    }

    public void showMsg(String msg) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.dialog_delete);
        TextView tv_msg = dialog.findViewById(R.id.tv_msg);
        tv_msg.setText(msg);
        Button okBTN = dialog.findViewById(R.id.btn_ok);
        Button noBTN = dialog.findViewById(R.id.btn_no);
        okBTN.setOnClickListener(arg0 -> {
            dialog.dismiss();
            mPreseneter.updateLeague(mIsStatus);
        });
        noBTN.setOnClickListener(arg0 -> {
            dialog.dismiss();
        });
        dialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}