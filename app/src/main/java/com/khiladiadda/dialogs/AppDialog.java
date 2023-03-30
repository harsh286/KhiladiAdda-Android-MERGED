package com.khiladiadda.dialogs;

import static android.os.Build.VERSION.SDK_INT;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.khiladiadda.R;
import com.khiladiadda.clashx2.main.activity.MyFanLeagueActivityHTH;
import com.khiladiadda.clashx2.main.activity.SelectedPlayers;
import com.khiladiadda.dialogs.interfaces.IOnAddCallDutyCredentialListener;
import com.khiladiadda.dialogs.interfaces.IOnAddChallengeListener;
import com.khiladiadda.dialogs.interfaces.IOnAddGameCredentialListener;
import com.khiladiadda.dialogs.interfaces.IOnAddLudoUniverseListener;
import com.khiladiadda.dialogs.interfaces.IOnCreateTeamPaymentListener;
import com.khiladiadda.dialogs.interfaces.IOnLudoUpdateListener;
import com.khiladiadda.dialogs.interfaces.IOnNetworkErrorListener;
import com.khiladiadda.dialogs.interfaces.IOnPaymentListener;
import com.khiladiadda.dialogs.interfaces.IOnRedeemVoucherListener;
import com.khiladiadda.dialogs.interfaces.IOnUpdateGameUsernameListener;
import com.khiladiadda.dialogs.interfaces.IOnVesrionDownloadListener;
import com.khiladiadda.gameleague.interfaces.IDialogFilter;
import com.khiladiadda.network.model.response.Coins;
import com.khiladiadda.preference.AppSharedPreference;
import com.khiladiadda.terms.TermsActivity;
import com.khiladiadda.utility.AppConstant;
import com.khiladiadda.utility.AppUtilityMethods;
import com.khiladiadda.utility.ImageActivity;
import com.khiladiadda.utility.PermissionUtils;
import com.khiladiadda.wallet.AddWalletActivity;
import com.moengage.core.analytics.MoEAnalyticsHelper;
import com.moengage.core.model.AppStatus;

public class AppDialog {

    public static Dialog getAppProgressDialog(Context context, String message) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_app_progresss);
        dialog.getWindow().setBackgroundDrawableResource(R.color.colorTransparent);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    public static Dialog getAppProgressDialogWithMessage(Context context, String message) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_wait);
        dialog.getWindow().setBackgroundDrawableResource(R.color.colorTransparent);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        final TextView mMessageTv = dialog.findViewById(R.id.tv_msg);
        mMessageTv.setText(message);
        return dialog;
    }

    public static Dialog showPaymentConfirmation(Activity activity, final IOnPaymentListener listener, String heading, String msg, boolean result) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_payment_confirmation);
        final TextView mHeadingTV = dialog.findViewById(R.id.tv_heading);
        mHeadingTV.setText(heading);
        final TextView mMsgTV = dialog.findViewById(R.id.tv_msg);
        mMsgTV.setText(msg);
        final ImageView mIV = dialog.findViewById(R.id.iv_payment);
        if (result) {
            mHeadingTV.setTextColor(ContextCompat.getColor(activity, R.color.color_green));
            mIV.setBackground(AppCompatResources.getDrawable(activity, R.drawable.payment_success));
        } else {
            mHeadingTV.setTextColor(ContextCompat.getColor(activity, R.color.colorPrimary));
            mIV.setBackground(AppCompatResources.getDrawable(activity, R.drawable.payment_failure));
        }
        Button mSendBTN = dialog.findViewById(R.id.btn_ok);
        mSendBTN.setOnClickListener(v -> {
            dialog.dismiss();
            if (listener != null) {
                listener.onPayment(result);
            }
        });
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        return dialog;
    }

    public static Dialog addGameCredentialDialog(Activity activity, final IOnAddGameCredentialListener listener, String from, String username, String characterId) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_game_credential);
        final EditText mUsernameET = dialog.findViewById(R.id.et_username);
        final EditText mCharacterET = dialog.findViewById(R.id.et_character);
        final EditText mTeamIdET = dialog.findViewById(R.id.et_team_id);
        final TextView mRulesTV = dialog.findViewById(R.id.tv_rules);
        final TextView mHelpTV = dialog.findViewById(R.id.tv_help);
        final TextView mImageTV = dialog.findViewById(R.id.tv_image);
        if (!TextUtils.isEmpty(username)) {
            mUsernameET.setText(username);
        }
        if (!TextUtils.isEmpty(characterId)) {
            mCharacterET.setText(characterId);
        }
        if (from.equalsIgnoreCase(AppConstant.FREEFIRE_SOLO) || from.equalsIgnoreCase(AppConstant.FREEFIRE_DUO) || from.equalsIgnoreCase(AppConstant.FF_CLASH_SOLO) || from.equalsIgnoreCase(AppConstant.FF_CLASH_DUO) || from.equalsIgnoreCase(AppConstant.FF_MAX_SOLO) || from.equalsIgnoreCase(AppConstant.FF_MAX_DUO)) {
            mUsernameET.setHint(R.string.help_ff_username);
            mCharacterET.setHint(R.string.hint_ff_userid);
            mHelpTV.setText(R.string.help_ff_credential);
            mImageTV.setText(R.string.help_ff_show_image);
        } else if (from.equalsIgnoreCase(AppConstant.PUBG_GLOBAL_SOLO) || from.equalsIgnoreCase(AppConstant.PUBG_GLOBAL_DUO) || from.equalsIgnoreCase(AppConstant.PUBG_GLOBAL_SQUAD)) {
            mUsernameET.setHint(R.string.help_pubglobal_username);
            mCharacterET.setHint(R.string.hint_pubglobal_userid);
            mHelpTV.setText(R.string.help_pubglobal_credential);
            mImageTV.setText(R.string.help_pubglobal_show_image);
        } else if (from.equalsIgnoreCase(AppConstant.PREMIUM_ESPORTS_SOLO) || from.equalsIgnoreCase(AppConstant.PREMIUM_ESPORTS_SQUAD) || from.equalsIgnoreCase(AppConstant.PREMIUM_ESPORTS_DUO)) {
            mUsernameET.setHint(R.string.help_esp_username);
            mCharacterET.setHint(R.string.hint_esp_userid);
            mHelpTV.setText(R.string.help_esp_credential);
            mImageTV.setText(R.string.help_esp_show_image);
        } else if (from.equalsIgnoreCase(AppConstant.PUBG_NEWSTATE_SOLO) || from.equalsIgnoreCase(AppConstant.PUBG_NEWSTATE_DUO) || from.equalsIgnoreCase(AppConstant.PUBG_NEWSTATE_SQUAD)) {
            mUsernameET.setHint(R.string.hint_pubgns_user_name);
            mCharacterET.setHint(R.string.hint_pubgns_character_name);
            mHelpTV.setText(R.string.help_pubg_ns_credential);
            mImageTV.setText(R.string.help_pubgns_show_image);
        }
        if (!from.equalsIgnoreCase(AppConstant.PUBG_SOLO) && !from.equalsIgnoreCase(AppConstant.PUBG_LITE_SOLO) && !from.equalsIgnoreCase(AppConstant.CLASHROYALE) && !from.equalsIgnoreCase(AppConstant.FREEFIRE_SOLO) && !from.equalsIgnoreCase(AppConstant.FF_CLASH_SOLO) && !from.equalsIgnoreCase(AppConstant.FF_MAX_SOLO) && !from.equalsIgnoreCase(AppConstant.PUBG_GLOBAL_SOLO) && !from.equalsIgnoreCase(AppConstant.PREMIUM_ESPORTS_SOLO) && !from.equalsIgnoreCase(AppConstant.PUBG_NEWSTATE_SOLO)) {
            mTeamIdET.setVisibility(View.VISIBLE);
            mRulesTV.setVisibility(View.VISIBLE);
        }
        mImageTV.setOnClickListener(v -> {
            Intent i = new Intent(activity, ImageActivity.class);
            if (from.equalsIgnoreCase(AppConstant.PUBG_LITE_SOLO) || from.equalsIgnoreCase(AppConstant.PUBG_LITE_DUO) || from.equalsIgnoreCase(AppConstant.PUBG_LITE_SQUAD)) {
                i.putExtra(AppConstant.FROM, AppConstant.FROM_VIEW_BGMI);
            } else if (from.equalsIgnoreCase(AppConstant.FREEFIRE_SOLO) || from.equalsIgnoreCase(AppConstant.FREEFIRE_DUO) || from.equalsIgnoreCase(AppConstant.FF_CLASH_SOLO) || from.equalsIgnoreCase(AppConstant.FF_CLASH_DUO) || from.equalsIgnoreCase(AppConstant.FF_MAX_SOLO) || from.equalsIgnoreCase(AppConstant.FF_MAX_DUO)) {
                i.putExtra(AppConstant.FROM, AppConstant.FROM_VIEW_FREEFIRE);
            } else if (from.equalsIgnoreCase(AppConstant.PUBG_NEWSTATE_SOLO) || from.equalsIgnoreCase(AppConstant.PUBG_NEWSTATE_DUO) || from.equalsIgnoreCase(AppConstant.PUBG_NEWSTATE_SQUAD)) {
                i.putExtra(AppConstant.FROM, AppConstant.FROM_VIEW_PUBG_NEWSTATE);
            } else {
                i.putExtra(AppConstant.FROM, AppConstant.FROM_VIEW_TDM);
            }
            activity.startActivity(i);
        });
        Button mCancelBTN = dialog.findViewById(R.id.btn_cancel);
        mCancelBTN.setOnClickListener(v -> dialog.dismiss());
        Button mSendBTN = dialog.findViewById(R.id.btn_send);
        mSendBTN.setOnClickListener(v -> {
            if (mUsernameET.getText().toString().trim().isEmpty() || mUsernameET.getText().toString().trim().length() < 3) {
                AppUtilityMethods.showMsg(activity, "Username cannot be empty", false);
            } else if (from.equalsIgnoreCase(AppConstant.CLASHROYALE) && (mCharacterET.getText().toString().trim().isEmpty() || mCharacterET.getText().toString().trim().length() < 3)) {
                AppUtilityMethods.showMsg(activity, "Tag-Id cannot be empty", false);
            } else if ((from.equalsIgnoreCase(AppConstant.PUBG_SOLO) || from.equalsIgnoreCase(AppConstant.PUBG_LITE_SOLO)) && (mCharacterET.getText().toString().trim().isEmpty() || mCharacterET.getText().toString().trim().length() < 3)) {
                AppUtilityMethods.showMsg(activity, "Character-Id cannot be empty", false);
            } else if ((from.equalsIgnoreCase(AppConstant.FREEFIRE_SOLO) || from.equalsIgnoreCase(AppConstant.FF_CLASH_SOLO) || from.equalsIgnoreCase(AppConstant.PREMIUM_ESPORTS_SOLO) || from.equalsIgnoreCase(AppConstant.PUBG_GLOBAL_SOLO)) && (mCharacterET.getText().toString().trim().isEmpty() || mCharacterET.getText().toString().trim().length() < 3)) {
                AppUtilityMethods.showMsg(activity, "User-Id cannot be empty", false);
            } else if ((from.equalsIgnoreCase(AppConstant.PUBG_SQUAD) || from.equalsIgnoreCase(AppConstant.PUBG_DUO) || from.equalsIgnoreCase(AppConstant.PUBG_LITE_DUO) || from.equalsIgnoreCase(AppConstant.PUBG_LITE_SQUAD) || from.equalsIgnoreCase(AppConstant.PREMIUM_ESPORTS_SQUAD) || from.equalsIgnoreCase(AppConstant.PREMIUM_ESPORTS_DUO) || from.equalsIgnoreCase(AppConstant.PUBG_GLOBAL_SQUAD) || from.equalsIgnoreCase(AppConstant.PUBG_GLOBAL_DUO) || from.equalsIgnoreCase(AppConstant.FREEFIRE_SQUAD) || from.equalsIgnoreCase(AppConstant.FREEFIRE_DUO) || from.equalsIgnoreCase(AppConstant.FF_CLASH_SQUAD) || from.equalsIgnoreCase(AppConstant.FF_CLASH_DUO) || from.equalsIgnoreCase(AppConstant.FF_MAX_SQUAD) || from.equalsIgnoreCase(AppConstant.FF_MAX_DUO)) && (mTeamIdET.getText().toString().trim().isEmpty() || mTeamIdET.getText().toString().trim().length() < 3)) {
                AppUtilityMethods.showMsg(activity, "Team-Id cannot be empty", false);
            } else if ((from.equalsIgnoreCase(AppConstant.PUBG_NEWSTATE_SOLO) || from.equalsIgnoreCase(AppConstant.PUBG_NEWSTATE_DUO) || from.equalsIgnoreCase(AppConstant.PUBG_NEWSTATE_SQUAD)) && (mCharacterET.getText().toString().trim().isEmpty() || mCharacterET.getText().toString().trim().length() < 3)) {
                AppUtilityMethods.showMsg(activity, "Account-Id cannot be empty", false);
            } else if ((from.equalsIgnoreCase(AppConstant.PUBG_NEWSTATE_DUO) || from.equalsIgnoreCase(AppConstant.PUBG_NEWSTATE_SQUAD)) && (mTeamIdET.getText().toString().trim().isEmpty() || mTeamIdET.getText().toString().trim().length() < 3)) {
                AppUtilityMethods.showMsg(activity, "Team-Id cannot be empty", false);
            } else if (listener != null) {
                listener.onCredentialAdd(mUsernameET.getText().toString().trim(), mCharacterET.getText().toString().trim(), mTeamIdET.getText().toString().trim());
            }
            dialog.dismiss();
        });
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        return dialog;
    }

    public static Dialog addCallDutyCredentialDialog(Activity activity, final IOnAddCallDutyCredentialListener listener, String from, String username) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_game_credential);
        final EditText mUsernameET = dialog.findViewById(R.id.et_username);
        final EditText mCharacterET = dialog.findViewById(R.id.et_character);
        mCharacterET.setVisibility(View.GONE);
        final EditText mTeamIdET = dialog.findViewById(R.id.et_team_id);
        final TextView mHelpTV = dialog.findViewById(R.id.tv_help);
        mHelpTV.setText(R.string.help_cod_credential);
        final TextView mRulesTV = dialog.findViewById(R.id.tv_rules);
        final TextView mImageTV = dialog.findViewById(R.id.tv_image);
        if (!TextUtils.isEmpty(username)) {
            mUsernameET.setText(username);
        }
        mUsernameET.setHint(R.string.help_cod_username);
        mHelpTV.setText(R.string.help_cod_credential);
        mImageTV.setText(R.string.help_cod_show_image);
        if (!from.equalsIgnoreCase(AppConstant.CALL_DUTY_SOLO)) {
            mTeamIdET.setVisibility(View.VISIBLE);
            mRulesTV.setVisibility(View.VISIBLE);
        }
        mImageTV.setOnClickListener(v -> {
            Intent i = new Intent(activity, ImageActivity.class);
            i.putExtra(AppConstant.FROM, AppConstant.FROM_VIEW_CALLOFDUTY);
            activity.startActivity(i);
        });
        Button mCancelBTN = dialog.findViewById(R.id.btn_cancel);
        mCancelBTN.setOnClickListener(v -> dialog.dismiss());
        Button mSendBTN = dialog.findViewById(R.id.btn_send);
        mSendBTN.setOnClickListener(v -> {
            if (mUsernameET.getText().toString().trim().isEmpty() || mUsernameET.getText().toString().trim().length() < 2) {
                AppUtilityMethods.showMsg(activity, "Game Username cannot be empty", false);
            } else if (from.equalsIgnoreCase(AppConstant.CALL_DUTY_SQUAD) || from.equalsIgnoreCase(AppConstant.CALL_DUTY_DUO) && (mTeamIdET.getText().toString().trim().isEmpty() || mTeamIdET.getText().toString().trim().length() < 3)) {
                AppUtilityMethods.showMsg(activity, "Team-Id cannot be empty", false);
            } else if (listener != null) {
                listener.onCallDutyCredentialAdd(mUsernameET.getText().toString().trim(), mTeamIdET.getText().toString().trim());
            }
            dialog.dismiss();
        });
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        return dialog;
    }

    public static Dialog showLiveCredentialDialog(Activity activity, String username, String password, int from) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_live_credential);
        final TextView mUsernameET = dialog.findViewById(R.id.tv_username);
        final TextView mHeadTV = dialog.findViewById(R.id.tv_dialog_name);
        if (from == 1) {
            mHeadTV.setText(R.string.text_pubns_help);
            mUsernameET.setText("Match Name: " + username);
        } else {
            mUsernameET.setText("RoomId: " + username);
        }
        mUsernameET.setEnabled(false);
        final TextView mCharacterET = dialog.findViewById(R.id.tv_character);
        if (!TextUtils.isEmpty(password)) {
            mCharacterET.setText("Password: " + password);
            mCharacterET.setEnabled(false);
        } else {
            mCharacterET.setVisibility(View.GONE);
        }
        Button mSendBTN = dialog.findViewById(R.id.btn_send);
        mSendBTN.setOnClickListener(v -> dialog.dismiss());
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        return dialog;
    }

    public static Dialog showCreateTeamPaymentDialog(Activity activity, final IOnCreateTeamPaymentListener listener, String gameId, String entryFee, String username, String characterId) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_create_game_payment);
        final EditText mUsernameET = dialog.findViewById(R.id.et_username);
        final EditText mCharacterET = dialog.findViewById(R.id.et_character);
        final EditText mTeamNameET = dialog.findViewById(R.id.et_team_name);
        final TextView mAmountTV = dialog.findViewById(R.id.tv_amount);
        final TextView mHelpTV = dialog.findViewById(R.id.tv_help);
        final TextView mImageTV = dialog.findViewById(R.id.tv_image);
        Button mSendBTN = dialog.findViewById(R.id.btn_send);
        Button mCancelBTN = dialog.findViewById(R.id.btn_cancel);
        if (TextUtils.isEmpty(entryFee)) {
            mAmountTV.setText("Payable Coins: 0");
        } else {
            mAmountTV.setText("Payable Coins: " + entryFee);
        }
        if (!TextUtils.isEmpty(username)) {
            mUsernameET.setText(username);
        }
        if (!TextUtils.isEmpty(characterId)) {
            mCharacterET.setText(characterId);
        }
        if (gameId.equalsIgnoreCase(AppSharedPreference.getInstance().getString(AppConstant.PUBG_ID, "")) || gameId.equalsIgnoreCase(AppSharedPreference.getInstance().getString(AppConstant.PUBG_LITE_ID, ""))) {
            mImageTV.setText(R.string.help_pubg_show_image);
            mUsernameET.setHint(R.string.hint_pubg_user_name);
            mCharacterET.setHint(R.string.hint_pubg_character_name);
            mHelpTV.setText(R.string.help_pubg_credential);
        } else if (gameId.equalsIgnoreCase(AppSharedPreference.getInstance().getString(AppConstant.FREEFIRE_ID, "")) || gameId.equalsIgnoreCase(AppSharedPreference.getInstance().getString(AppConstant.FF_CLASH_ID, "")) || gameId.equalsIgnoreCase(AppSharedPreference.getInstance().getString(AppConstant.FF_MAX_ID, ""))) {
            mImageTV.setText(R.string.help_ff_show_image);
            mUsernameET.setHint(R.string.help_ff_username);
            mCharacterET.setHint(R.string.hint_ff_userid);
            mHelpTV.setText(R.string.help_ff_credential);
        } else if (gameId.equalsIgnoreCase(AppSharedPreference.getInstance().getString(AppConstant.PUBG_GLOBAL_ID, ""))) {
            mImageTV.setText(R.string.help_pubg_show_image);
            mUsernameET.setHint(R.string.help_pubglobal_username);
            mCharacterET.setHint(R.string.hint_pubglobal_userid);
            mHelpTV.setText(R.string.help_pubglobal_credential);
        } else if (gameId.equalsIgnoreCase(AppSharedPreference.getInstance().getString(AppConstant.PREMIUM_ESPORTS_ID, ""))) {
            mImageTV.setText(R.string.help_esp_show_image);
            mUsernameET.setHint(R.string.help_esp_username);
            mCharacterET.setHint(R.string.hint_esp_userid);
            mHelpTV.setText(R.string.help_esp_credential);
        } else if (gameId.equalsIgnoreCase(AppSharedPreference.getInstance().getString(AppConstant.PUBG_NEWSTATE_ID, ""))) {
            mUsernameET.setHint(R.string.hint_pubgns_user_name);
            mCharacterET.setHint(R.string.hint_pubgns_character_name);
            mHelpTV.setText(R.string.help_pubg_ns_credential);
            mImageTV.setText(R.string.help_pubgns_show_image);
        }
        mImageTV.setOnClickListener(v -> {
            Intent i = new Intent(activity, ImageActivity.class);
            if (gameId.equalsIgnoreCase(AppSharedPreference.getInstance().getString(AppConstant.PUBG_ID, "")) || gameId.equalsIgnoreCase(AppSharedPreference.getInstance().getString(AppConstant.PUBG_LITE_ID, ""))) {
                i.putExtra(AppConstant.FROM, AppConstant.FROM_VIEW_TDM);
            } else if (gameId.equalsIgnoreCase(AppSharedPreference.getInstance().getString(AppConstant.FREEFIRE_ID, "")) || gameId.equalsIgnoreCase(AppSharedPreference.getInstance().getString(AppConstant.FF_CLASH_ID, ""))) {
                i.putExtra(AppConstant.FROM, AppConstant.FROM_VIEW_FREEFIRE);
            } else if (gameId.equalsIgnoreCase(AppSharedPreference.getInstance().getString(AppConstant.FF_MAX_ID, ""))) {
                i.putExtra(AppConstant.FROM, AppConstant.FROM_VIEW_FF_MAX);
            } else if (gameId.equalsIgnoreCase(AppSharedPreference.getInstance().getString(AppConstant.PUBG_GLOBAL_ID, ""))) {
                i.putExtra(AppConstant.FROM, AppConstant.FROM_VIEW_PUBG_GLOBAL);
            } else if (gameId.equalsIgnoreCase(AppSharedPreference.getInstance().getString(AppConstant.PREMIUM_ESPORTS_ID, ""))) {
                i.putExtra(AppConstant.FROM, AppConstant.FROM_VIEW_PREMIUM_ESPORTS);
            } else if (gameId.equalsIgnoreCase(AppSharedPreference.getInstance().getString(AppConstant.PUBG_NEWSTATE_ID, ""))) {
                i.putExtra(AppConstant.FROM, AppConstant.FROM_VIEW_PUBG_NEWSTATE);
            }
            activity.startActivity(i);
        });
        mCancelBTN.setOnClickListener(v -> dialog.dismiss());
        mSendBTN.setOnClickListener(v -> {
            if (mUsernameET.getText().toString().trim().isEmpty() || mUsernameET.getText().toString().trim().length() < 3) {
                AppUtilityMethods.showMsg(activity, "Username cannot be empty", false);
            } else if (gameId.equalsIgnoreCase(AppSharedPreference.getInstance().getString(AppConstant.PUBG_ID, "")) && (mCharacterET.getText().toString().trim().isEmpty() || mCharacterET.getText().toString().trim().length() < 3)) {
                AppUtilityMethods.showMsg(activity, "Character-Id cannot be empty", false);
            } else if (mTeamNameET.getText().toString().trim().isEmpty() || mTeamNameET.getText().toString().trim().length() < 3) {
                AppUtilityMethods.showMsg(activity, "Team name cannot be empty", false);
            } else if (listener != null) {
                listener.onPayment(mUsernameET.getText().toString().trim(), mCharacterET.getText().toString().trim(), mTeamNameET.getText().toString().trim());
            }
            dialog.dismiss();
        });
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        return dialog;
    }

    public static Dialog addChallengeDialog(Activity activity, final IOnAddChallengeListener listener, String gameUsername, int contestType, int mode, double walletBalance) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_challange);
        final TextView nameTV = dialog.findViewById(R.id.tv_dialog_name);
        final EditText ludoIdET = dialog.findViewById(R.id.et_ludo_id);
        final EditText amountET = dialog.findViewById(R.id.et_amount);
        Button mSetBTN = dialog.findViewById(R.id.btn_set);
        if (!TextUtils.isEmpty(gameUsername)) {
            ludoIdET.setText(gameUsername);
        }
        final TextView tenTV = dialog.findViewById(R.id.tv_ten);
        final TextView fiftyTV = dialog.findViewById(R.id.tv_fifty);
        final TextView hundredTV = dialog.findViewById(R.id.tv_hundred);
        final TextView twoHundredTV = dialog.findViewById(R.id.tv_two_hundred);
        final TextView fiveHundredTV = dialog.findViewById(R.id.tv_five_hundred);
        final TextView thousandTV = dialog.findViewById(R.id.tv_thousand);
        final TextView twoThousandTV = dialog.findViewById(R.id.tv_two_thousand);
        final TextView fiveThousandTV = dialog.findViewById(R.id.tv_five_thousand);
        if (mode == 1) {
            nameTV.setText("Add Challenge (Classic)");
        } else {
            nameTV.setText("Add Challenge (Popular)");
        }
        tenTV.setOnClickListener(v -> {
            tenTV.setSelected(true);
            fiftyTV.setSelected(false);
            hundredTV.setSelected(false);
            twoHundredTV.setSelected(false);
            fiveHundredTV.setSelected(false);
            thousandTV.setSelected(false);
            twoThousandTV.setSelected(false);
            fiveThousandTV.setSelected(false);
            amountET.setText(R.string.text_ten);
        });
        fiftyTV.setOnClickListener(v -> {
            tenTV.setSelected(false);
            fiftyTV.setSelected(true);
            hundredTV.setSelected(false);
            twoHundredTV.setSelected(false);
            fiveHundredTV.setSelected(false);
            thousandTV.setSelected(false);
            twoThousandTV.setSelected(false);
            fiveThousandTV.setSelected(false);
            amountET.setText(R.string.text_fifty);
        });
        hundredTV.setOnClickListener(v -> {
            tenTV.setSelected(false);
            fiftyTV.setSelected(false);
            hundredTV.setSelected(true);
            twoHundredTV.setSelected(false);
            fiveHundredTV.setSelected(false);
            thousandTV.setSelected(false);
            twoThousandTV.setSelected(false);
            fiveThousandTV.setSelected(false);
            amountET.setText(R.string.text_hundred);
        });
        twoHundredTV.setOnClickListener(v -> {
            tenTV.setSelected(false);
            fiftyTV.setSelected(false);
            hundredTV.setSelected(false);
            twoHundredTV.setSelected(true);
            fiveHundredTV.setSelected(false);
            thousandTV.setSelected(false);
            twoThousandTV.setSelected(false);
            fiveThousandTV.setSelected(false);
            amountET.setText(R.string.text_two_hundred);
        });
        fiveHundredTV.setOnClickListener(v -> {
            tenTV.setSelected(false);
            fiftyTV.setSelected(false);
            hundredTV.setSelected(false);
            twoHundredTV.setSelected(false);
            fiveHundredTV.setSelected(true);
            thousandTV.setSelected(false);
            twoThousandTV.setSelected(false);
            fiveThousandTV.setSelected(false);
            amountET.setText(R.string.text_five_hundred);
        });
        thousandTV.setOnClickListener(v -> {
            tenTV.setSelected(false);
            fiftyTV.setSelected(false);
            hundredTV.setSelected(false);
            twoHundredTV.setSelected(false);
            fiveHundredTV.setSelected(false);
            thousandTV.setSelected(true);
            twoThousandTV.setSelected(false);
            fiveThousandTV.setSelected(false);
            amountET.setText(R.string.text_thousand);
        });
        twoThousandTV.setOnClickListener(v -> {
            tenTV.setSelected(false);
            fiftyTV.setSelected(false);
            hundredTV.setSelected(false);
            twoHundredTV.setSelected(false);
            fiveHundredTV.setSelected(false);
            thousandTV.setSelected(false);
            twoThousandTV.setSelected(true);
            fiveThousandTV.setSelected(false);
            amountET.setText(R.string.text_two_thousand);
        });
        fiveThousandTV.setOnClickListener(v -> {
            tenTV.setSelected(false);
            fiftyTV.setSelected(false);
            hundredTV.setSelected(false);
            twoHundredTV.setSelected(false);
            fiveHundredTV.setSelected(false);
            thousandTV.setSelected(false);
            twoThousandTV.setSelected(false);
            fiveThousandTV.setSelected(true);
            amountET.setText(R.string.text_five_thousand);
        });
        final TextView mImageTV = dialog.findViewById(R.id.tv_image);
        if (contestType == AppConstant.TYPE_SNAKE_LADDER) {
            mImageTV.setText(R.string.help_snake_show_image);
        }
        mImageTV.setOnClickListener(v -> {
            Intent i = new Intent(activity, ImageActivity.class);
            i.putExtra(AppConstant.FROM, AppConstant.FROM_VIEW_LUDO);
            activity.startActivity(i);
        });
//        Coins coins = AppSharedPreference.getInstance().getProfileData().getCoins();
//        double walletBalance = coins.getBonus() + coins.getDeposit() + coins.getWinning();
        mSetBTN.setOnClickListener(v -> {
            String amount = amountET.getText().toString().trim();
            if (ludoIdET.getText().toString().trim().isEmpty()) {
                AppUtilityMethods.showMsg(activity, "Your game username can not be blank", false);
            } else if (amountET.getText().toString().trim().isEmpty()) {
                AppUtilityMethods.showMsg(activity, "Amount can not be blank", false);
            } else if (Integer.parseInt(amount) < 10) {
                AppUtilityMethods.showMsg(activity, "Challenge can not be of less than 10 coins", false);
            } else if (Integer.parseInt(amount) > 100000) {
                AppUtilityMethods.showMsg(activity, "Challenge can not be of greater than 100000 coins", false);
            } else if (Integer.parseInt(amount) % 10 != 0) {
                AppUtilityMethods.showMsg(activity, "Challenge coins must be multiple of 10.(Ex-10,20,30,40,50,100 and so on)", false);
            } else if (Integer.parseInt(amount) > walletBalance) {
                AppUtilityMethods.showRechargeMsg(activity, "Your wallet balance is insufficient. Please recharge your wallet to play and earn.");
            } else if (listener != null) {
                listener.onAddChallengeListener(ludoIdET.getText().toString().trim(), amountET.getText().toString().trim(), mode);
            }
        });
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        return dialog;
    }

    public static Dialog updateLudoDialog(Activity activity, final IOnLudoUpdateListener listener) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_update_ludo);
        final EditText roomIdET = dialog.findViewById(R.id.et_room_id);
        Button mSetBTN = dialog.findViewById(R.id.btn_set);
        mSetBTN.setOnClickListener(v -> {
            if (roomIdET.getText().toString().trim().isEmpty()) {
                AppUtilityMethods.showMsg(activity, "Your Room Id cannot be blank", false);
            } else if (listener != null) {
                listener.onUpdateLudo(roomIdET.getText().toString().trim());
            }
        });
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        return dialog;
    }

    public static Dialog updateGameUsernameDialog(Activity activity, final IOnUpdateGameUsernameListener listener, int position) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_update_username);
        final EditText roomIdET = dialog.findViewById(R.id.et_room_id);
        Button mSetBTN = dialog.findViewById(R.id.btn_set);
        mSetBTN.setOnClickListener(v -> {
            if (roomIdET.getText().toString().trim().isEmpty()) {
                AppUtilityMethods.showMsg(activity, "Please provide your ludo king username", false);
            } else if (listener != null) {
                listener.onUpdateGameUsername(roomIdET.getText().toString().trim(), position);
            }
        });
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        return dialog;
    }

    public static Dialog showMsgVersionExit(Activity activity, final IOnVesrionDownloadListener listener) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.version);
        ProgressBar progressBar = dialog.findViewById(R.id.pb_apk_download);
        TextView tvDownloadSize = dialog.findViewById(R.id.tv_size);
        tvDownloadSize.setText(R.string.text_click_below_download);
        TextView tvVersion = dialog.findViewById(R.id.tv_version);
        tvVersion.setText(AppSharedPreference.getInstance().getString(AppConstant.VERSION, ""));
        TextView tv_msg = dialog.findViewById(R.id.tv_msg);
        tv_msg.setText(AppSharedPreference.getInstance().getString(AppConstant.VERSION_DESC, ""));
        TextView tvHelp = dialog.findViewById(R.id.tv_help);
        Button mWebsiteBTN = dialog.findViewById(R.id.btn_update_website);
        mWebsiteBTN.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.khiladiadda.com"));
            activity.startActivity(browserIntent);
        });
        Button mUpdateBTN = dialog.findViewById(R.id.btn_update);
        mUpdateBTN.setOnClickListener(v -> {

            if (SDK_INT >= Build.VERSION_CODES.R) {
                if (Environment.isExternalStorageManager()) {
                    if (listener != null) {
                        progressBar.setVisibility(View.VISIBLE);
                        tvDownloadSize.setText(R.string.text_downloading_progress);
                        mUpdateBTN.setVisibility(View.GONE);
                        tvHelp.setVisibility(View.GONE);
                        mWebsiteBTN.setVisibility(View.GONE);
                        listener.onDownloadVersion();
                        MoEAnalyticsHelper.INSTANCE.setAppStatus(activity, AppStatus.UPDATE);
                    }
                } else {
                    Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                    Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
                    intent.setData(uri);
                    activity.startActivity(intent);
                }

            } else {
                if (PermissionUtils.hasStoragePermission(activity)) {
                    if (listener != null) {
                        progressBar.setVisibility(View.VISIBLE);
                        tvDownloadSize.setText(R.string.text_downloading_progress);
                        mUpdateBTN.setVisibility(View.GONE);
                        tvHelp.setVisibility(View.GONE);
                        mWebsiteBTN.setVisibility(View.GONE);
                        listener.onDownloadVersion();
                        MoEAnalyticsHelper.INSTANCE.setAppStatus(activity, AppStatus.UPDATE);
                    }
                } else {
                    AppUtilityMethods.showStoragePermisisionMsg(activity, "", false);
                }

            }


        });
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        return dialog;
    }

    public static void showPaytmUpi(Activity activity) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.paytm_upi);
        TextView website1 = dialog.findViewById(R.id.website_btn1);
        TextView website2 = dialog.findViewById(R.id.website_btn2);
        TextView dismiss = dialog.findViewById(R.id.dismiss);
        website1.setOnClickListener(v -> {
            Intent terms = new Intent(activity, TermsActivity.class);
            terms.putExtra(AppConstant.FROM, AppConstant.PATYMUPI);
            activity.startActivity(terms);
        });
        website2.setOnClickListener(v -> {
            Intent terms = new Intent(activity, TermsActivity.class);
            terms.putExtra(AppConstant.FROM, AppConstant.PATYMUPI);
            activity.startActivity(terms);
        });
        dismiss.setOnClickListener(v -> {
            dialog.dismiss();
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    public static void showReedemVoucher(Activity activity) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.redeem_voucher);
        EditText vouchernumber = dialog.findViewById(R.id.et_vouchernumber);
        EditText voucherpin = dialog.findViewById(R.id.et_voucherpin);
        Button submit = dialog.findViewById(R.id.btn_submit);
        Button close = dialog.findViewById(R.id.btn_cancel);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        submit.setOnClickListener(v -> {
            if (TextUtils.isEmpty(vouchernumber.getText().toString().trim())) {
                AppUtilityMethods.showMsg(activity, "Enter Voucher Number", false);
            } else if (vouchernumber.getText().toString().length() < 10) {
                AppUtilityMethods.showMsg(activity, "Voucher number cannot be less than 10 digit", false);
            } else if (TextUtils.isEmpty(voucherpin.getText().toString().trim())) {
                AppUtilityMethods.showMsg(activity, "Enter Voucher Pin", false);
            } else if (voucherpin.getText().toString().length() < 6) {
                AppUtilityMethods.showMsg(activity, "Voucher pin cannot be less than 6 digit", false);
            } else {
                IOnRedeemVoucherListener dialogListerner = (IOnRedeemVoucherListener) activity;
                dialogListerner.voucher(vouchernumber.getText().toString(), voucherpin.getText().toString());
                dialog.dismiss();
            }
        });
        close.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

    public static Dialog addLudoUniverseDialog(Activity activity, final IOnAddLudoUniverseListener listener) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_ludo_universe);
        final TextView nameTV = dialog.findViewById(R.id.tv_dialog_name);
        final EditText amountET = dialog.findViewById(R.id.et_amount);
        Button mSetBTN = dialog.findViewById(R.id.btn_set);
        final TextView tenTV = dialog.findViewById(R.id.tv_ten);
        final TextView fiftyTV = dialog.findViewById(R.id.tv_fifty);
        final TextView hundredTV = dialog.findViewById(R.id.tv_hundred);
        final TextView twoHundredTV = dialog.findViewById(R.id.tv_two_hundred);
        final TextView fiveHundredTV = dialog.findViewById(R.id.tv_five_hundred);
        final TextView thousandTV = dialog.findViewById(R.id.tv_thousand);
        final TextView twoThousandTV = dialog.findViewById(R.id.tv_two_thousand);
        final TextView fiveThousandTV = dialog.findViewById(R.id.tv_five_thousand);
        tenTV.setOnClickListener(v -> {
            tenTV.setSelected(true);
            fiftyTV.setSelected(false);
            hundredTV.setSelected(false);
            twoHundredTV.setSelected(false);
            fiveHundredTV.setSelected(false);
            thousandTV.setSelected(false);
            twoThousandTV.setSelected(false);
            fiveThousandTV.setSelected(false);
            amountET.setText(R.string.text_ten);
        });
        fiftyTV.setOnClickListener(v -> {
            tenTV.setSelected(false);
            fiftyTV.setSelected(true);
            hundredTV.setSelected(false);
            twoHundredTV.setSelected(false);
            fiveHundredTV.setSelected(false);
            thousandTV.setSelected(false);
            twoThousandTV.setSelected(false);
            fiveThousandTV.setSelected(false);
            amountET.setText(R.string.text_fifty);
        });
        hundredTV.setOnClickListener(v -> {
            tenTV.setSelected(false);
            fiftyTV.setSelected(false);
            hundredTV.setSelected(true);
            twoHundredTV.setSelected(false);
            fiveHundredTV.setSelected(false);
            thousandTV.setSelected(false);
            twoThousandTV.setSelected(false);
            fiveThousandTV.setSelected(false);
            amountET.setText(R.string.text_hundred);
        });
        twoHundredTV.setOnClickListener(v -> {
            tenTV.setSelected(false);
            fiftyTV.setSelected(false);
            hundredTV.setSelected(false);
            twoHundredTV.setSelected(true);
            fiveHundredTV.setSelected(false);
            thousandTV.setSelected(false);
            twoThousandTV.setSelected(false);
            fiveThousandTV.setSelected(false);
            amountET.setText(R.string.text_two_hundred);
        });
        fiveHundredTV.setOnClickListener(v -> {
            tenTV.setSelected(false);
            fiftyTV.setSelected(false);
            hundredTV.setSelected(false);
            twoHundredTV.setSelected(false);
            fiveHundredTV.setSelected(true);
            thousandTV.setSelected(false);
            twoThousandTV.setSelected(false);
            fiveThousandTV.setSelected(false);
            amountET.setText(R.string.text_five_hundred);
        });
        thousandTV.setOnClickListener(v -> {
            tenTV.setSelected(false);
            fiftyTV.setSelected(false);
            hundredTV.setSelected(false);
            twoHundredTV.setSelected(false);
            fiveHundredTV.setSelected(false);
            thousandTV.setSelected(true);
            twoThousandTV.setSelected(false);
            fiveThousandTV.setSelected(false);
            amountET.setText(R.string.text_thousand);
        });
        twoThousandTV.setOnClickListener(v -> {
            tenTV.setSelected(false);
            fiftyTV.setSelected(false);
            hundredTV.setSelected(false);
            twoHundredTV.setSelected(false);
            fiveHundredTV.setSelected(false);
            thousandTV.setSelected(false);
            twoThousandTV.setSelected(true);
            fiveThousandTV.setSelected(false);
            amountET.setText(R.string.text_two_thousand);
        });
        fiveThousandTV.setOnClickListener(v -> {
            tenTV.setSelected(false);
            fiftyTV.setSelected(false);
            hundredTV.setSelected(false);
            twoHundredTV.setSelected(false);
            fiveHundredTV.setSelected(false);
            thousandTV.setSelected(false);
            twoThousandTV.setSelected(false);
            fiveThousandTV.setSelected(true);
            amountET.setText(R.string.text_five_thousand);
        });
        Coins coins = AppSharedPreference.getInstance().getProfileData().getCoins();
        double walletBalance = coins.getBonus() + coins.getDeposit() + coins.getWinning();
        mSetBTN.setOnClickListener(v -> {
            String amount = amountET.getText().toString().trim();
            if (amountET.getText().toString().trim().isEmpty()) {
                AppUtilityMethods.showMsg(activity, "Amount can not be blank", false);
            } else if (Integer.parseInt(amount) < 10) {
                AppUtilityMethods.showMsg(activity, "Challenge can not be of less than 10 coins", false);
            } else if (Integer.parseInt(amount) > 100000) {
                AppUtilityMethods.showMsg(activity, "Challenge can not be of greater than 100000 coins", false);
            } else if (Integer.parseInt(amount) % 10 != 0) {
                AppUtilityMethods.showMsg(activity, "Challenge coins must be multiple of 10.(Ex-10,20,30,40,50,100 and so on)", false);
            } else if (Integer.parseInt(amount) > walletBalance) {
                AppUtilityMethods.showRechargeMsg(activity, "Your wallet balance is insufficient. Please recharge your wallet to play and earn.");
            } else if (listener != null) {
                listener.onAddLuodUniverseListener(amountET.getText().toString().trim());
            }
        });
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        return dialog;
    }

    public static Dialog showNetworkErrorDialog(Activity activity, final IOnNetworkErrorListener listener) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setContentView(R.layout.dialog_network);
        TextView mTryAgainBTN = dialog.findViewById(R.id.tv_try_again);
        ImageView mCrossIV = dialog.findViewById(R.id.iv_cross);
        mCrossIV.setOnClickListener(v -> {
            if (listener != null) {
                dialog.dismiss();
                listener.onRetry();
            }
        });
        mTryAgainBTN.setOnClickListener(v -> {
            if (listener != null) {
                dialog.dismiss();
                listener.onRetry();
            }
        });
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        return dialog;
    }

    public static Dialog showStatusSuccessDialog(Activity activity, String msg) {
        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.dialog_status_success);
        Button btnOkay = dialog.findViewById(R.id.btn_okay_dialog);
        TextView msgTv = dialog.findViewById(R.id.tv_amt);
        msgTv.setText(msg);
        btnOkay.setOnClickListener(v -> {
            activity.finish();
            dialog.dismiss();

        });
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        return dialog;
    }

    public static Dialog showStatusFailureDialog(Activity activity, String msg) {
        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.dialog_status_failure);
        Button btnOkay = dialog.findViewById(R.id.btn_okay_dialog);
        TextView msgTv = dialog.findViewById(R.id.tv_amt);
        msgTv.setText(msg);
        btnOkay.setOnClickListener(v -> {
            dialog.dismiss();
            activity.finish();
        });
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        return dialog;
    }

    public static Dialog showStatuspendingDialog(Activity activity) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_status_pending);
        Button btnOkay = dialog.findViewById(R.id.btn_okay_dialog);
        btnOkay.setOnClickListener(v -> {
            dialog.dismiss();
        });
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        return dialog;
    }


    public static Dialog showTrendingDialog(Activity activity) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setContentView(R.layout.dialog_video);
        ImageView mCloseIV = dialog.findViewById(R.id.iv_close);
        ImageView mImageIV = dialog.findViewById(R.id.iv_logo);
        AppSharedPreference preference = AppSharedPreference.getInstance();
        Glide.with(mImageIV.getContext()).load(preference.getDashboardData().getDialogueBanner().getImg()).placeholder(R.drawable.app_logo).into(mImageIV);
        mCloseIV.setOnClickListener(v -> {
            dialog.dismiss();
        });
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        return dialog;
    }

    public static void droidoDialog(Activity activity, IDialogFilter listener) {
        final int[] counter = {0};
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.new_droido_dialog);
        MaterialCardView mcb_trending = dialog.findViewById(R.id.mcb_trending);
        ImageView imgTrending = dialog.findViewById(R.id.img_trending);
        MaterialCardView mcb_classic = dialog.findViewById(R.id.mcb_classic);
        ImageView imgClassic = dialog.findViewById(R.id.img_classic);
        MaterialCardView mcb_head2head = dialog.findViewById(R.id.mcb_h2h);
        ImageView imgHead2Head = dialog.findViewById(R.id.img_h2h);
        MaterialCardView mcb_low2high = dialog.findViewById(R.id.mcb_l2h);
        ImageView imglow2high = dialog.findViewById(R.id.img_l2h);
        MaterialCardView mcb_high2low = dialog.findViewById(R.id.mcb_h2l);
        ImageView imghigh2low = dialog.findViewById(R.id.img_h2l);
        Button btnApply = dialog.findViewById(R.id.btn_apply);
        mcb_trending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counter[0] = R.integer.int_1;
                mcb_trending.setStrokeColor(Color.parseColor("#DA3A41"));
                imgTrending.setVisibility(View.VISIBLE);
                mcb_classic.setStrokeColor(Color.parseColor("#CECECE"));
                imgClassic.setVisibility(View.INVISIBLE);
                mcb_head2head.setStrokeColor(Color.parseColor("#CECECE"));
                imgHead2Head.setVisibility(View.INVISIBLE);
                mcb_low2high.setStrokeColor(Color.parseColor("#CECECE"));
                imglow2high.setVisibility(View.INVISIBLE);
                mcb_high2low.setStrokeColor(Color.parseColor("#CECECE"));
                imghigh2low.setVisibility(View.INVISIBLE);
            }
        });

        mcb_classic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counter[0] = 2;
                mcb_trending.setStrokeColor(Color.parseColor("#CECECE"));
                imgTrending.setVisibility(View.INVISIBLE);
                mcb_classic.setStrokeColor(Color.parseColor("#DA3A41"));
                imgClassic.setVisibility(View.VISIBLE);
                mcb_head2head.setStrokeColor(Color.parseColor("#CECECE"));
                imgHead2Head.setVisibility(View.INVISIBLE);
                mcb_low2high.setStrokeColor(Color.parseColor("#CECECE"));
                imglow2high.setVisibility(View.INVISIBLE);
                mcb_high2low.setStrokeColor(Color.parseColor("#CECECE"));
                imghigh2low.setVisibility(View.INVISIBLE);

            }
        });

        mcb_head2head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counter[0] = 3;
                mcb_trending.setStrokeColor(Color.parseColor("#CECECE"));
                imgTrending.setVisibility(View.INVISIBLE);
                mcb_classic.setStrokeColor(Color.parseColor("#CECECE"));
                imgClassic.setVisibility(View.INVISIBLE);
                mcb_head2head.setStrokeColor(Color.parseColor("#DA3A41"));
                imgHead2Head.setVisibility(View.VISIBLE);
                mcb_low2high.setStrokeColor(Color.parseColor("#CECECE"));
                imglow2high.setVisibility(View.INVISIBLE);
                mcb_high2low.setStrokeColor(Color.parseColor("#CECECE"));
                imghigh2low.setVisibility(View.INVISIBLE);
            }
        });

        mcb_low2high.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counter[0] = 4;
                mcb_trending.setStrokeColor(Color.parseColor("#CECECE"));
                imgTrending.setVisibility(View.INVISIBLE);
                mcb_classic.setStrokeColor(Color.parseColor("#CECECE"));
                imgClassic.setVisibility(View.INVISIBLE);
                mcb_head2head.setStrokeColor(Color.parseColor("#CECECE"));
                imgHead2Head.setVisibility(View.INVISIBLE);
                mcb_low2high.setStrokeColor(Color.parseColor("#DA3A41"));
                imglow2high.setVisibility(View.VISIBLE);
                mcb_high2low.setStrokeColor(Color.parseColor("#CECECE"));
                imghigh2low.setVisibility(View.INVISIBLE);
            }
        });

        mcb_high2low.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counter[0] = 5;
                mcb_trending.setStrokeColor(Color.parseColor("#CECECE"));
                imgTrending.setVisibility(View.INVISIBLE);
                mcb_classic.setStrokeColor(Color.parseColor("#CECECE"));
                imgClassic.setVisibility(View.INVISIBLE);
                mcb_head2head.setStrokeColor(Color.parseColor("#CECECE"));
                imgHead2Head.setVisibility(View.INVISIBLE);
                mcb_low2high.setStrokeColor(Color.parseColor("#CECECE"));
                imglow2high.setVisibility(View.INVISIBLE);
                mcb_high2low.setStrokeColor(Color.parseColor("#DA3A41"));
                imghigh2low.setVisibility(View.VISIBLE);
            }
        });
        ImageView dismiss = dialog.findViewById(R.id.image_cross);
        dismiss.setOnClickListener(v -> {
            dialog.dismiss();
        });
        btnApply.setOnClickListener(v -> {
            listener.dialogFilters(counter[0]);
            dialog.dismiss();
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    //GAMERCASE DIALOG
    public static Dialog showInsufficientGCDialog(Activity activity) {
        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.dialog_status_insufficient);
        Button btnOkay = dialog.findViewById(R.id.btn_okay_dialog);
        btnOkay.setOnClickListener(v -> {
            dialog.dismiss();

        });
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        return dialog;
    }

    public static Dialog showAlertDialog(Activity activity, String msg) {
        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.dialog_alert_dialog);
        Button btnOkay = dialog.findViewById(R.id.btn_okay_dialog);
        TextView msgTv = dialog.findViewById(R.id.tv_amt);
        msgTv.setText(msg);
        btnOkay.setOnClickListener(v -> {
            activity.finish();
            dialog.dismiss();

        });
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        return dialog;
    }

    public static Dialog showDisclaimerDialog(Activity activity, String msg) {
        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.dialog_alert_dialog);
        Button btnOkay = dialog.findViewById(R.id.btn_okay_dialog);
        TextView msgTv = dialog.findViewById(R.id.tv_amt);
        msgTv.setText(msg);
        btnOkay.setOnClickListener(v -> {
            dialog.dismiss();

        });
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        return dialog;
    }

    public static Dialog DialogWithLocationCallBack(Activity activity, String message) {
        final Dialog dialog = new Dialog(activity);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.show_error_popup);
        TextView mMessageTV = dialog.findViewById(R.id.tv_error_message);
        mMessageTV.setText(message);
        Button mOkayTV = dialog.findViewById(R.id.btn_done);
        mOkayTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                activity.startActivity(new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.fromParts("package", activity.getPackageName(), null)));
            }
        });
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        return dialog;
    }

    public static void showRummyRechargeMsg(final Activity activity, String msg) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.rummy_recharge_dialog);
        TextView tv_msg = dialog.findViewById(R.id.tv_msg);
        tv_msg.setText(msg);
        Button mOkBTN = dialog.findViewById(R.id.btn_ok);
        mOkBTN.setOnClickListener(arg0 -> {
            dialog.dismiss();
            activity.startActivity(new Intent(activity, AddWalletActivity.class));
            activity.finish();
        });
        Button mNoBTN = dialog.findViewById(R.id.btn_no);
        mNoBTN.setText(R.string.cancel);
        mNoBTN.setOnClickListener(arg0 -> dialog.dismiss());
        dialog.show();
    }

    //    Restart Dialog
    public static void showRestartDialog(final Activity activity, String msg) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable());
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.layout_restart_dialog);
        dialog.show();
    }

    //Match Live Dialog
    public void showLiveDialog(Context context,String msg, boolean live) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.error_dialog);
        TextView text = dialog.findViewById(R.id.tv_text);
        ImageView image = dialog.findViewById(R.id.iv_tick);
        TextView heading = dialog.findViewById(R.id.tv_heading);
        text.setText(msg);
        TextView okBTN = dialog.findViewById(R.id.btn_ok);
        okBTN.setOnClickListener(arg0 -> {
                dialog.dismiss();
        });
        dialog.show();
    }
}