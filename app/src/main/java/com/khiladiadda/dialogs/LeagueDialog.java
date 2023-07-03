package com.khiladiadda.dialogs;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
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
import com.khiladiadda.utility.AppConstant;
import com.khiladiadda.utility.AppUtilityMethods;
import com.khiladiadda.utility.ImageActivity;

public class LeagueDialog extends BottomSheetDialog implements View.OnClickListener {

    private Button mCancelBTN, mSendBTN;
    private TextView mRulesTV, mHelpTV, mImageTV,mGameHintTV;
    private EditText mGameLevelET, mUsernameET, mCharacterET, mTeamIdET;
    private RadioGroup mMapRG;
    private LinearLayout mMapLL;
    private IOnAddGameCredentialListener mListener;
    private Context mContext;
    private String mFrom, mUsername, mCharacterId, mMap;
    private boolean mIsMapDownloaded;

    public LeagueDialog(@NonNull Context context, IOnAddGameCredentialListener listener, String from, String username, String characterId, String map) {
        super(context);
        this.mContext = context;
        this.mListener = listener;
        this.mFrom = from;
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
        return R.layout.dialog_game_credential;
    }

    protected void initViews(){
        mCancelBTN = findViewById(R.id.btn_cancel);
        mSendBTN = findViewById(R.id.btn_send);
        mGameLevelET = findViewById(R.id.et_game_level);
        mUsernameET = findViewById(R.id.et_username);
        mCharacterET = findViewById(R.id.et_character);
        mTeamIdET = findViewById(R.id.et_team_id);
        mRulesTV = findViewById(R.id.tv_rules);
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

        TextView tv = findViewById(R.id.tv_map);
        tv.setText("Have you downloaded " + mMap + " map?");

        if (mFrom.equalsIgnoreCase(AppConstant.PUBG_SOLO) || mFrom.equalsIgnoreCase(AppConstant.PUBG_DUO) || mFrom.equalsIgnoreCase(AppConstant.PUBG_SQUAD) || mFrom.equalsIgnoreCase(AppConstant.FF_CLASH_SOLO) || mFrom.equalsIgnoreCase(AppConstant.FF_CLASH_DUO) || mFrom.equalsIgnoreCase(AppConstant.FF_CLASH_SQUAD) || mFrom.equalsIgnoreCase(AppConstant.PREMIUM_ESPORTS_SOLO) || mFrom.equalsIgnoreCase(AppConstant.PREMIUM_ESPORTS_DUO) || mFrom.equalsIgnoreCase(AppConstant.PREMIUM_ESPORTS_SQUAD)) {
            mMapLL.setVisibility(View.GONE);
        }
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
        if (!TextUtils.isEmpty(mUsername)) {
            mUsernameET.setText(mUsername);
        }
        if (!TextUtils.isEmpty(mCharacterId)) {
            mCharacterET.setText(mCharacterId);
        }
        /*Change Game level*/
        if (mFrom.equalsIgnoreCase(AppConstant.FREEFIRE_SOLO)||mFrom.equalsIgnoreCase(AppConstant.FREEFIRE_DUO) || mFrom.equalsIgnoreCase(AppConstant.FREEFIRE_SQUAD) || mFrom.equalsIgnoreCase(AppConstant.FF_MAX_SOLO) || mFrom.equalsIgnoreCase(AppConstant.FF_MAX_DUO) || mFrom.equalsIgnoreCase(AppConstant.FF_MAX_SQUAD)) {
            mGameHintTV.setText(R.string.note_game_level_must_be_25_or_more_than_25);
        } else if (mFrom.equalsIgnoreCase(AppConstant.PREMIUM_ESPORTS_SOLO) || mFrom.equalsIgnoreCase(AppConstant.PREMIUM_ESPORTS_SQUAD) || mFrom.equalsIgnoreCase(AppConstant.PREMIUM_ESPORTS_DUO)) {
            mGameHintTV.setText(R.string.note_game_level_must_be_50_or_more_than_50);
        } else {
            mGameHintTV.setText(R.string.note_game_level_must_be_30_or_more_than_30);
        }
        if (mFrom.equalsIgnoreCase(AppConstant.FREEFIRE_SOLO) || mFrom.equalsIgnoreCase(AppConstant.FREEFIRE_DUO) || mFrom.equalsIgnoreCase(AppConstant.FF_CLASH_SOLO) || mFrom.equalsIgnoreCase(AppConstant.FF_CLASH_DUO) || mFrom.equalsIgnoreCase(AppConstant.FF_MAX_SOLO) || mFrom.equalsIgnoreCase(AppConstant.FF_MAX_DUO)) {
            mUsernameET.setHint(R.string.help_ff_username);
            mCharacterET.setHint(R.string.hint_ff_userid);
            mHelpTV.setText(R.string.help_ff_credential);
            mImageTV.setText(R.string.help_ff_show_image);
        } else if (mFrom.equalsIgnoreCase(AppConstant.PUBG_GLOBAL_SOLO) || mFrom.equalsIgnoreCase(AppConstant.PUBG_GLOBAL_DUO) || mFrom.equalsIgnoreCase(AppConstant.PUBG_GLOBAL_SQUAD)) {
            mUsernameET.setHint(R.string.help_pubglobal_username);
            mCharacterET.setHint(R.string.hint_pubglobal_userid);
            mHelpTV.setText(R.string.help_pubglobal_credential);
            mImageTV.setText(R.string.help_pubglobal_show_image);
        } else if (mFrom.equalsIgnoreCase(AppConstant.PREMIUM_ESPORTS_SOLO) || mFrom.equalsIgnoreCase(AppConstant.PREMIUM_ESPORTS_SQUAD) || mFrom.equalsIgnoreCase(AppConstant.PREMIUM_ESPORTS_DUO)) {
            mUsernameET.setHint(R.string.help_esp_username);
            mCharacterET.setHint(R.string.hint_esp_userid);
            mHelpTV.setText(R.string.help_esp_credential);
            mImageTV.setText(R.string.help_esp_show_image);
        } else if (mFrom.equalsIgnoreCase(AppConstant.PUBG_NEWSTATE_SOLO) || mFrom.equalsIgnoreCase(AppConstant.PUBG_NEWSTATE_DUO) || mFrom.equalsIgnoreCase(AppConstant.PUBG_NEWSTATE_SQUAD)) {
            mUsernameET.setHint(R.string.hint_pubgns_user_name);
            mCharacterET.setHint(R.string.hint_pubgns_character_name);
            mHelpTV.setText(R.string.help_pubg_ns_credential);
            mImageTV.setText(R.string.help_pubgns_show_image);
        }
        if (!mFrom.equalsIgnoreCase(AppConstant.PUBG_SOLO) && !mFrom.equalsIgnoreCase(AppConstant.PUBG_LITE_SOLO) && !mFrom.equalsIgnoreCase(AppConstant.CLASHROYALE) && !mFrom.equalsIgnoreCase(AppConstant.FREEFIRE_SOLO) && !mFrom.equalsIgnoreCase(AppConstant.FF_CLASH_SOLO) && !mFrom.equalsIgnoreCase(AppConstant.FF_MAX_SOLO) && !mFrom.equalsIgnoreCase(AppConstant.PUBG_GLOBAL_SOLO) && !mFrom.equalsIgnoreCase(AppConstant.PREMIUM_ESPORTS_SOLO) && !mFrom.equalsIgnoreCase(AppConstant.PUBG_NEWSTATE_SOLO)) {
            mTeamIdET.setVisibility(View.VISIBLE);
            mRulesTV.setVisibility(View.VISIBLE);
        }
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
                } else if (mFrom.equalsIgnoreCase(AppConstant.CLASHROYALE) && (mCharacterET.getText().toString().trim().isEmpty() || mCharacterET.getText().toString().trim().length() < 3)) {
                    AppUtilityMethods.showMsg(mContext, "Tag-Id cannot be empty", false);
                } else if ((mFrom.equalsIgnoreCase(AppConstant.PUBG_SOLO) || mFrom.equalsIgnoreCase(AppConstant.PUBG_LITE_SOLO)) && (mCharacterET.getText().toString().trim().isEmpty() || mCharacterET.getText().toString().trim().length() < 3)) {
                    AppUtilityMethods.showMsg(mContext, "Character-Id cannot be empty", false);
                } else if ((mFrom.equalsIgnoreCase(AppConstant.FREEFIRE_SOLO) || mFrom.equalsIgnoreCase(AppConstant.FF_CLASH_SOLO) || mFrom.equalsIgnoreCase(AppConstant.PREMIUM_ESPORTS_SOLO) || mFrom.equalsIgnoreCase(AppConstant.PUBG_GLOBAL_SOLO)) && (mCharacterET.getText().toString().trim().isEmpty() || mCharacterET.getText().toString().trim().length() < 3)) {
                    AppUtilityMethods.showMsg(mContext, "User-Id cannot be empty", false);
                } else if ((mFrom.equalsIgnoreCase(AppConstant.PUBG_SQUAD) || mFrom.equalsIgnoreCase(AppConstant.PUBG_DUO) || mFrom.equalsIgnoreCase(AppConstant.PUBG_LITE_DUO) || mFrom.equalsIgnoreCase(AppConstant.PUBG_LITE_SQUAD) || mFrom.equalsIgnoreCase(AppConstant.PREMIUM_ESPORTS_SQUAD) || mFrom.equalsIgnoreCase(AppConstant.PREMIUM_ESPORTS_DUO) || mFrom.equalsIgnoreCase(AppConstant.PUBG_GLOBAL_SQUAD) || mFrom.equalsIgnoreCase(AppConstant.PUBG_GLOBAL_DUO) || mFrom.equalsIgnoreCase(AppConstant.FREEFIRE_SQUAD) || mFrom.equalsIgnoreCase(AppConstant.FREEFIRE_DUO) || mFrom.equalsIgnoreCase(AppConstant.FF_CLASH_SQUAD) || mFrom.equalsIgnoreCase(AppConstant.FF_CLASH_DUO) || mFrom.equalsIgnoreCase(AppConstant.FF_MAX_SQUAD) || mFrom.equalsIgnoreCase(AppConstant.FF_MAX_DUO)) && (mTeamIdET.getText().toString().trim().isEmpty() || mTeamIdET.getText().toString().trim().length() < 3)) {
                    AppUtilityMethods.showMsg(mContext, "Team-Id cannot be empty", false);
                } else if ((mFrom.equalsIgnoreCase(AppConstant.PUBG_NEWSTATE_SOLO) || mFrom.equalsIgnoreCase(AppConstant.PUBG_NEWSTATE_DUO) || mFrom.equalsIgnoreCase(AppConstant.PUBG_NEWSTATE_SQUAD)) && (mCharacterET.getText().toString().trim().isEmpty() || mCharacterET.getText().toString().trim().length() < 3)) {
                    AppUtilityMethods.showMsg(mContext, "Account-Id cannot be empty", false);
                } else if ((mFrom.equalsIgnoreCase(AppConstant.PUBG_NEWSTATE_DUO) || mFrom.equalsIgnoreCase(AppConstant.PUBG_NEWSTATE_SQUAD)) && (mTeamIdET.getText().toString().trim().isEmpty() || mTeamIdET.getText().toString().trim().length() < 3)) {
                    AppUtilityMethods.showMsg(mContext, "Team-Id cannot be empty", false);
                } else if (mGameLevelET.getText().toString().trim().isEmpty()) {
                    AppUtilityMethods.showMsg(mContext, "Game level cannot be empty", false);
                } else if (mListener != null) {
                    mListener.onCredentialAdd(mUsernameET.getText().toString().trim(), mCharacterET.getText().toString().trim(), mTeamIdET.getText().toString().trim(), mGameLevelET.getText().toString().trim(), mIsMapDownloaded);
                }
                dismiss();
                break;
            case R.id.tv_image:
                Intent i = new Intent(mContext, ImageActivity.class);
                if (mFrom.equalsIgnoreCase(AppConstant.PUBG_LITE_SOLO) || mFrom.equalsIgnoreCase(AppConstant.PUBG_LITE_DUO) || mFrom.equalsIgnoreCase(AppConstant.PUBG_LITE_SQUAD)) {
                    i.putExtra(AppConstant.FROM, AppConstant.FROM_VIEW_BGMI);
                } else if (mFrom.equalsIgnoreCase(AppConstant.FREEFIRE_SOLO) || mFrom.equalsIgnoreCase(AppConstant.FREEFIRE_DUO) || mFrom.equalsIgnoreCase(AppConstant.FF_CLASH_SOLO) || mFrom.equalsIgnoreCase(AppConstant.FF_CLASH_DUO) || mFrom.equalsIgnoreCase(AppConstant.FF_MAX_SOLO) || mFrom.equalsIgnoreCase(AppConstant.FF_MAX_DUO)) {
                    i.putExtra(AppConstant.FROM, AppConstant.FROM_VIEW_FREEFIRE);
                } else if (mFrom.equalsIgnoreCase(AppConstant.PUBG_NEWSTATE_SOLO) || mFrom.equalsIgnoreCase(AppConstant.PUBG_NEWSTATE_DUO) || mFrom.equalsIgnoreCase(AppConstant.PUBG_NEWSTATE_SQUAD)) {
                    i.putExtra(AppConstant.FROM, AppConstant.FROM_VIEW_PUBG_NEWSTATE);
                } else {
                    i.putExtra(AppConstant.FROM, AppConstant.FROM_VIEW_TDM);
                }
                mContext.startActivity(i);
                dismiss();
                break;
        }
    }

}