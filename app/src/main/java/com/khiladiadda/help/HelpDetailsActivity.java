package com.khiladiadda.help;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.fcm.NotificationActivity;
import com.khiladiadda.help.adapter.HelpAdapter;
import com.khiladiadda.help.interfaces.IHelpPresenter;
import com.khiladiadda.help.interfaces.IHelpView;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.FaqCategoryDetails;
import com.khiladiadda.network.model.response.FaqCategoryResponse;
import com.khiladiadda.network.model.response.HelpDetails;
import com.khiladiadda.network.model.response.HelpResponse;
import com.khiladiadda.utility.AppConstant;
import com.khiladiadda.utility.AppUtilityMethods;
import com.khiladiadda.utility.NetworkStatus;

import java.util.ArrayList;

import butterknife.BindView;

public class HelpDetailsActivity extends BaseActivity implements IHelpView, HelpAdapter.IOnItemChildClickListener {

    @BindView(R.id.iv_back) ImageView mBackIV;
    @BindView(R.id.tv_activity_name) TextView mActivityNameTV;
    @BindView(R.id.iv_notification) ImageView mNotificationIV;
    @BindView(R.id.rv_help) RecyclerView mHelpRV;
    @BindView(R.id.btn_email) Button mEmailBTN;
    @BindView(R.id.ll_whatsapp) LinearLayout mWhatsAppLL;
    @BindView(R.id.btn_view_video) Button mVideoBTN;
    private HelpAdapter mAdapter;
    private ArrayList<HelpDetails> mList;
    private FaqCategoryDetails mDetails;
    private IHelpPresenter mPresenter;

    @Override protected int getContentView() {
        return R.layout.activity_help_details;
    }

    @Override protected void initViews() {
        mActivityNameTV.setText(R.string.text_help);
        mBackIV.setOnClickListener(this);
        mNotificationIV.setOnClickListener(this);
        mEmailBTN.setOnClickListener(this);
        mWhatsAppLL.setOnClickListener(this);
        mVideoBTN.setOnClickListener(this);
        mDetails = getIntent().getParcelableExtra(AppConstant.DATA);
    }

    @Override protected void initVariables() {
        mPresenter = new HelpPresenter(this);

        mList = new ArrayList<>();
        mAdapter = new HelpAdapter(mList);
        mHelpRV.setLayoutManager(new LinearLayoutManager(this));
        mHelpRV.setAdapter(mAdapter);
        mAdapter.setOnItemChildClickListener(this);

        if (new NetworkStatus(this).isInternetOn() && !TextUtils.isEmpty(mDetails.getId())) {
            showProgress(getString(R.string.txt_progress_authentication));
            mPresenter.getHelpList(mDetails.getId());
        } else {
            Snackbar.make(mVideoBTN, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_notification:
                startActivity(new Intent(this, NotificationActivity.class));
                break;
            case R.id.ll_whatsapp:
                openWhatsappContact();
                break;
            case R.id.btn_email:
                sendEmail();
                break;
            case R.id.btn_view_video:
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + "www.youtube.com/playlist?list=PLIvWNKDITNJA-lKa_RUj6L1mJvfy8McSG"));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/playlist?list=PLIvWNKDITNJA-lKa_RUj6L1mJvfy8McSG")));
                }
                break;
        }
    }

    @Override public void onGetCategoryComplete(FaqCategoryResponse responseModel) {
    }

    @Override public void onGetCategoryFailure(ApiError error) {
    }

    @Override public void onHelpComplete(HelpResponse responseModel) {
        mList.clear();
        hideProgress();
        if (responseModel.isStatus()) {
            if (responseModel.getResponse().size() > 0) {
                mList.addAll(responseModel.getResponse());
                mAdapter.notifyDataSetChanged();
            } else {
                Snackbar.make(mVideoBTN, getString(R.string.text_no_data), Snackbar.LENGTH_SHORT).show();
            }
        } else {
            AppUtilityMethods.showMsg(this, getString(R.string.error_internet), false);
        }
    }

    @Override public void onHelpFailure(ApiError error) {
        hideProgress();
        Snackbar.make(mVideoBTN, getString(R.string.text_no_data), Snackbar.LENGTH_SHORT).show();
    }

    @Override public void onViewAnswerClicked(int position) {
        HelpDetails details = mList.get(position);
        if (details.isExpand()) {
            details.setExpand(false);
        } else {
            details.setExpand(true);
        }
        mAdapter.notifyItemChanged(position);
    }

    private void sendEmail() {
        final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
        emailIntent.setType("text/plain");
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"support@khiladiadda.com"});
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Khiladi Adda Support");
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Type your query here");
        emailIntent.setType("message/rfc822");
        try {
            startActivity(Intent.createChooser(emailIntent, "Send email using..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "No email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }

    void openWhatsappContact() {
        String url = "https://wa.me/91" + mDetails.getMobile() + "?text=Hello%20I%20have%20a%20problem";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);

    }

    @Override protected void onDestroy() {
        mPresenter.destroy();
        super.onDestroy();
    }

}