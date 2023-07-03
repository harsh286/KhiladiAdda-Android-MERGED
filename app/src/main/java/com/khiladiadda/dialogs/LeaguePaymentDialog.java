package com.khiladiadda.dialogs;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.khiladiadda.R;
import com.khiladiadda.dialogs.interfaces.IOnAddGameCredentialListener;
import com.khiladiadda.dialogs.interfaces.IOnCreateTeamPaymentListener;
import com.khiladiadda.preference.AppSharedPreference;
import com.khiladiadda.utility.AppConstant;
import com.khiladiadda.utility.AppUtilityMethods;
import com.khiladiadda.utility.ImageActivity;

public class LeaguePaymentDialog extends BottomSheetDialog implements View.OnClickListener {

    private Button mCancelBTN, mSendBTN;
    private TextView mAmountTV, mHelpTV, mImageTV, mGameHintTV;
    private EditText mGameLevelET, mUsernameET, mCharacterET, mTeamNameET;
    private RadioGroup mMapRG;
    private LinearLayout mMapLL;
    private IOnCreateTeamPaymentListener mListener;
    private Context mContext;
    private String mFrom, mUsername, mCharacterId, mMap, mGameId, mEntryFee;
    private boolean mIsMapDownloaded;

    public LeaguePaymentDialog(@NonNull Context context, IOnCreateTeamPaymentListener listener, String gameId, String entryFee, String username, String characterId, String map) {
        super(context);
        this.mContext = context;
        this.mListener = listener;
        this.mGameId = gameId;
        this.mEntryFee = entryFee;
        this.mUsername = username;
        this.mCharacterId = characterId;
        this.mMap = map;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        initViews();
        initVariables();
    }

    protected int getContentView() {
        return R.layout.dialog_create_game_payment;
    }

    protected void initViews() {
        mCancelBTN = findViewById(R.id.btn_cancel);
        mSendBTN = findViewById(R.id.btn_send);
        mGameLevelET = findViewById(R.id.et_game_level);
        mUsernameET = findViewById(R.id.et_username);
        mCharacterET = findViewById(R.id.et_character);
        mTeamNameET = findViewById(R.id.et_team_name);
        mAmountTV = findViewById(R.id.tv_amount);
        mHelpTV = findViewById(R.id.tv_help);
        mImageTV = findViewById(R.id.tv_image);
        mMapRG = findViewById(R.id.rg_map);
        mMapLL = findViewById(R.id.ll_map);
        mGameHintTV = findViewById(R.id.tv_game_hint);
    }

    private void initVariables() {
        mSendBTN.setOnClickListener(this);
        mCancelBTN.setOnClickListener(this);
        mImageTV.setOnClickListener(this);
        if (TextUtils.isEmpty(mEntryFee)) {
            mAmountTV.setText("Payable Coins: 0");
        } else {
            mAmountTV.setText("Payable Coins: " + mEntryFee);
        }
        if (!TextUtils.isEmpty(mUsername)) {
            mUsernameET.setText(mUsername);
        }
        if (!TextUtils.isEmpty(mCharacterId)) {
            mCharacterET.setText(mCharacterId);
        }
        /*Change Game level*/
        if (mGameId.equalsIgnoreCase(AppSharedPreference.getInstance().getString(AppConstant.FREEFIRE_ID,"")) ||mGameId.equalsIgnoreCase(AppSharedPreference.getInstance().getString(AppConstant.FF_MAX_ID,""))) {
            mGameHintTV.setText(R.string.note_game_level_must_be_25_or_more_than_25);
        } else if (mGameId.equalsIgnoreCase(AppSharedPreference.getInstance().getString(AppConstant.PREMIUM_ESPORTS_ID, ""))) {
            mGameHintTV.setText(R.string.note_game_level_must_be_50_or_more_than_50);
        } else {
            mGameHintTV.setText(R.string.note_game_level_must_be_30_or_more_than_30);
        }
        if (mGameId.equalsIgnoreCase(AppSharedPreference.getInstance().getString(AppConstant.PUBG_ID, "")) || mGameId.equalsIgnoreCase(AppSharedPreference.getInstance().getString(AppConstant.PUBG_LITE_ID, ""))) {
            mImageTV.setText(R.string.help_pubg_show_image);
            mUsernameET.setHint(R.string.hint_pubg_user_name);
            mCharacterET.setHint(R.string.hint_pubg_character_name);
            mHelpTV.setText(R.string.help_pubg_credential);
            mMapLL.setVisibility(View.GONE);
        } else if (mGameId.equalsIgnoreCase(AppSharedPreference.getInstance().getString(AppConstant.FREEFIRE_ID, "")) || mGameId.equalsIgnoreCase(AppSharedPreference.getInstance().getString(AppConstant.FF_CLASH_ID, "")) || mGameId.equalsIgnoreCase(AppSharedPreference.getInstance().getString(AppConstant.FF_MAX_ID, ""))) {
            mImageTV.setText(R.string.help_ff_show_image);
            mUsernameET.setHint(R.string.help_ff_username);
            mCharacterET.setHint(R.string.hint_ff_userid);
            mHelpTV.setText(R.string.help_ff_credential);
            if (mGameId.equalsIgnoreCase(AppSharedPreference.getInstance().getString(AppConstant.FF_CLASH_ID, ""))) {
                mMapLL.setVisibility(View.GONE);
            }
        } else if (mGameId.equalsIgnoreCase(AppSharedPreference.getInstance().getString(AppConstant.PUBG_GLOBAL_ID, ""))) {
            mImageTV.setText(R.string.help_pubg_show_image);
            mUsernameET.setHint(R.string.help_pubglobal_username);
            mCharacterET.setHint(R.string.hint_pubglobal_userid);
            mHelpTV.setText(R.string.help_pubglobal_credential);
        } else if (mGameId.equalsIgnoreCase(AppSharedPreference.getInstance().getString(AppConstant.PREMIUM_ESPORTS_ID, ""))) {
            mImageTV.setText(R.string.help_esp_show_image);
            mUsernameET.setHint(R.string.help_esp_username);
            mCharacterET.setHint(R.string.hint_esp_userid);
            mHelpTV.setText(R.string.help_esp_credential);
            mMapLL.setVisibility(View.GONE);
        } else if (mGameId.equalsIgnoreCase(AppSharedPreference.getInstance().getString(AppConstant.PUBG_NEWSTATE_ID, ""))) {
            mUsernameET.setHint(R.string.hint_pubgns_user_name);
            mCharacterET.setHint(R.string.hint_pubgns_character_name);
            mHelpTV.setText(R.string.help_pubg_ns_credential);
            mImageTV.setText(R.string.help_pubgns_show_image);
        }
        TextView tv = findViewById(R.id.tv_map);
        tv.setText("Have you downloaded " + mMap + " map?");

        mMapRG.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton rb = group.findViewById(checkedId);
            if (null != rb) {
                int selectedId = mMapRG.getCheckedRadioButtonId();
                RadioButton radioVehicleButton = mMapRG.findViewById(selectedId);
                String mapDownloaded = String.valueOf(radioVehicleButton.getText());
                if (mapDownloaded.equalsIgnoreCase("yes")) {
                    mIsMapDownloaded = true;
                } else {
                    mIsMapDownloaded = false;
                }
            }
        });


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel:
                dismiss();
                break;
            case R.id.btn_send:
                if (mUsernameET.getText().toString().trim().isEmpty() || mUsernameET.getText().toString().trim().length() < 3) {
                    AppUtilityMethods.showMsg(mContext, "Username cannot be empty", false);
                } else if (mGameId.equalsIgnoreCase(AppSharedPreference.getInstance().getString(AppConstant.PUBG_ID, "")) && (mCharacterET.getText().toString().trim().isEmpty() || mCharacterET.getText().toString().trim().length() < 3)) {
                    AppUtilityMethods.showMsg(mContext, "Character-Id cannot be empty", false);
                } else if (mTeamNameET.getText().toString().trim().isEmpty() || mTeamNameET.getText().toString().trim().length() < 3) {
                    AppUtilityMethods.showMsg(mContext, "Team name cannot be empty", false);
                } else if (mListener != null) {
                    mListener.onPayment(mUsernameET.getText().toString().trim(), mCharacterET.getText().toString().trim(), mTeamNameET.getText().toString().trim(), mGameLevelET.getText().toString().trim(), mIsMapDownloaded);
                }
                dismiss();
                break;
            case R.id.tv_image:
                Intent i = new Intent(mContext, ImageActivity.class);
                if (mGameId.equalsIgnoreCase(AppSharedPreference.getInstance().getString(AppConstant.PUBG_ID, "")) || mGameId.equalsIgnoreCase(AppSharedPreference.getInstance().getString(AppConstant.PUBG_LITE_ID, ""))) {
                    i.putExtra(AppConstant.FROM, AppConstant.FROM_VIEW_TDM);
                } else if (mGameId.equalsIgnoreCase(AppSharedPreference.getInstance().getString(AppConstant.FREEFIRE_ID, "")) || mGameId.equalsIgnoreCase(AppSharedPreference.getInstance().getString(AppConstant.FF_CLASH_ID, ""))) {
                    i.putExtra(AppConstant.FROM, AppConstant.FROM_VIEW_FREEFIRE);
                } else if (mGameId.equalsIgnoreCase(AppSharedPreference.getInstance().getString(AppConstant.FF_MAX_ID, ""))) {
                    i.putExtra(AppConstant.FROM, AppConstant.FROM_VIEW_FF_MAX);
                } else if (mGameId.equalsIgnoreCase(AppSharedPreference.getInstance().getString(AppConstant.PUBG_GLOBAL_ID, ""))) {
                    i.putExtra(AppConstant.FROM, AppConstant.FROM_VIEW_PUBG_GLOBAL);
                } else if (mGameId.equalsIgnoreCase(AppSharedPreference.getInstance().getString(AppConstant.PREMIUM_ESPORTS_ID, ""))) {
                    i.putExtra(AppConstant.FROM, AppConstant.FROM_VIEW_PREMIUM_ESPORTS);
                } else if (mGameId.equalsIgnoreCase(AppSharedPreference.getInstance().getString(AppConstant.PUBG_NEWSTATE_ID, ""))) {
                    i.putExtra(AppConstant.FROM, AppConstant.FROM_VIEW_PUBG_NEWSTATE);
                }
                mContext.startActivity(i);
                dismiss();
                break;
        }
    }


}