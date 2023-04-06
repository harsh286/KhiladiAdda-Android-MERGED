package com.khiladiadda.utility;

import static android.os.Build.VERSION.SDK_INT;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.google.android.material.snackbar.Snackbar;
import com.khiladiadda.R;
import com.khiladiadda.gamercash.InstallActivity;
import com.khiladiadda.gamercash.PayActivity;
import com.khiladiadda.gamercash.VerifiedActivity;
import com.khiladiadda.interfaces.IOnDateSetListener;
import com.khiladiadda.interfaces.IOnLeagueJoinListener;
import com.khiladiadda.login.LoginActivity;
import com.khiladiadda.ludoTournament.activity.LudoTmtRulesActivity;
import com.khiladiadda.network.model.response.Active;
import com.khiladiadda.network.model.response.CategoryList;
import com.khiladiadda.network.model.response.GameCategory;
import com.khiladiadda.network.model.response.MasterDetails;
import com.khiladiadda.network.model.response.MasterResponse;
import com.khiladiadda.network.model.response.ProfileDetails;
import com.khiladiadda.preference.AppSharedPreference;
import com.khiladiadda.preference.PreferenceKeysModel;
import com.khiladiadda.profile.ProfileActivity;
import com.khiladiadda.profile.update.AadharActivity;
import com.khiladiadda.splash.SplashActivity;
import com.khiladiadda.wallet.AddWalletActivity;
import com.moengage.core.MoECoreHelper;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AppUtilityMethods {

    private static Context context;

    public AppUtilityMethods(Context context) {
        AppUtilityMethods.context = context;
    }

    public static void showMsg(final Context activity, String msg, boolean isCancel) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(isCancel);
        dialog.setCancelable(isCancel);
        dialog.setContentView(R.layout.popup);
        TextView tv_msg = dialog.findViewById(R.id.tv_msg);
        tv_msg.setText(msg);
        Button okBTN = dialog.findViewById(R.id.btn_ok);
        okBTN.setOnClickListener(arg0 -> {
            dialog.dismiss();
        });
        dialog.show();
    }

    public static void showMsgWithCancel(final Context context, Activity activity, String msg, boolean isCancel) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(isCancel);
        dialog.setCancelable(isCancel);
        dialog.setContentView(R.layout.popup);
        TextView tv_msg = dialog.findViewById(R.id.tv_msg);
        tv_msg.setText(msg);
        Button okBTN = dialog.findViewById(R.id.btn_ok);
        okBTN.setOnClickListener(arg0 -> {
            if (!isCancel) {
                dialog.dismiss();
            } else {
                activity.finish();
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    public static void showProfileUpdateMsg(final Activity activity, String msg, int from, boolean isCancel) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(isCancel);
        dialog.setContentView(R.layout.popup);
        TextView tv_msg = dialog.findViewById(R.id.tv_msg);
        tv_msg.setText(msg);
        Button okBTN = dialog.findViewById(R.id.btn_ok);
        okBTN.setOnClickListener(arg0 -> {
            dialog.dismiss();
            if (from == 1 || from == 3) {
                activity.startActivity(new Intent(activity, ProfileActivity.class));
            } else {
                Intent pan = new Intent(activity, AadharActivity.class);
                pan.putExtra(AppConstant.FROM, AppConstant.FROM_AADHAR);
                activity.startActivity(pan);
            }
            activity.finish();
        });
        dialog.show();
    }

    public static String getLudoCurrentDate() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }

    public static void openDatePickerDialog(final Context context, final IOnDateSetListener onDateSetListener, Date date) {
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        DatePickerDialog dialog = new DatePickerDialog(context, R.style.DialogTheme, (datePicker, year, month, day) -> onDateSetListener.onDateSet(year, month, day), calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
        dialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
        dialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
    }

    public static void openToDatePickerDialog(final Context context, final IOnDateSetListener onDateSetListener) {
        Calendar calendar = Calendar.getInstance();
        //        DatePickerDialog dialog = new DatePickerDialog(context, R.style.DialogTheme, (datePicker, year, month, day) -> onDateSetListener.onDateSet(year, month, day), calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        DatePickerDialog dialog = new DatePickerDialog(context, R.style.DialogTheme, (datePicker, year, month, day) -> onDateSetListener.onDateSet(year, month, day), calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        dialog.getDatePicker().setMaxDate(new Date().getTime());
        dialog.show();
        dialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
        dialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
    }

    public static void openDOBDatePickerDialog(final Context context, final IOnDateSetListener onDateSetListener, Date date) {
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        DatePickerDialog dialog = new DatePickerDialog(context, R.style.DialogTheme, (datePicker, year, month, day) -> onDateSetListener.onDateSet(year, month, day), calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        dialog.getDatePicker().setMaxDate(new Date().getTime());
        dialog.show();
        dialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
        dialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
    }


    public static String getDateStringFromDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        return format.format(date);
    }

    public static Date getDateFromString(String dateString) {
        if (TextUtils.isEmpty(dateString)) {
            return Calendar.getInstance().getTime();
        }
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        try {
            return format.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return Calendar.getInstance().getTime();
    }

    public static String getConvertDateTimeForServer(String date) {
        String date1 = "";
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            return format.format(dateFormat.parseObject(date));
        } catch (ParseException e) {
            e.printStackTrace();
            return date1;
        }
    }

    public static String getConvertDateQuiz(String date) {
        String date1 = "";
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            SimpleDateFormat format = new SimpleDateFormat("dd MMM hh:mm aa");
            return format.format(dateFormat.parseObject(date));
        } catch (ParseException e) {
            e.printStackTrace();
            return date1;
        }
    }

    public static String getConvertDateMatch(String date) {
        String date1 = "";
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            SimpleDateFormat format = new SimpleDateFormat("hh:mm aa | dd MMM, yyyy");
            return format.format(dateFormat.parseObject(date));
        } catch (ParseException e) {
            e.printStackTrace();
            return date1;
        }
    }

    public static String getConvertDateTimeMatch(String date) {
        String date1 = "";
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            SimpleDateFormat format = new SimpleDateFormat("dd MMM, hh:mm aa");
            return format.format(dateFormat.parseObject(date));
        } catch (ParseException e) {
            e.printStackTrace();
            return date1;
        }
    }

    public static String getConvertDateFacts(String date) {
        String date1 = "";
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            SimpleDateFormat format = new SimpleDateFormat("dd MMM, yyyy");
            return format.format(dateFormat.parseObject(date));
        } catch (ParseException e) {
            e.printStackTrace();
            return date1;
        }
    }

    public static String getConvertDateParkIn(String date) {
        String date1 = "";
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
            return format.format(dateFormat.parseObject(date));
        } catch (ParseException e) {
            e.printStackTrace();
            return date1;
        }
    }

    public static String getConvertTimeMatch(String date) {
        String date1 = "";
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            SimpleDateFormat format = new SimpleDateFormat("hh:mm aa");
            return format.format(dateFormat.parseObject(date));
        } catch (ParseException e) {
            e.printStackTrace();
            return date1;
        }
    }

    public static void showMsgCancel(final Activity activity, String msg, boolean isCancel) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(isCancel);
        dialog.setContentView(R.layout.popup);
        TextView tv_msg = dialog.findViewById(R.id.tv_msg);
        tv_msg.setText(msg);
        Button okBTN = dialog.findViewById(R.id.btn_ok);
        okBTN.setOnClickListener(arg0 -> {
            dialog.dismiss();
            activity.finish();
        });
        dialog.show();
    }

    public static boolean isEmailValidator(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isMobileValidator(String mobile) {
        Pattern pattern;
        Matcher matcher;
        // The given argument to compile() method is regular expression. With the help of
        // regular expression we can validate mobile number.
        // 1) Begins with 0 or 91
        // 2) Then contains 6 or 7 or 8 or 9.
        // 3) Then contains 9 digits
        pattern = Pattern.compile("[4-9][0-9]{9}");
        // Pattern class contains matcher() method
        // to find matching between given number
        // and regular expression
        matcher = pattern.matcher(mobile);
        return (matcher.find() && matcher.group().equals(mobile));
    }

    public static boolean isValidPassword(String password) {
        String passwordPattern = "^(?=.*?[A-Za-z])(?=.*?[0-9]).{8,}$";//"^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";
        //^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$
        return password.matches(passwordPattern);
    }

    public static boolean isValidPanNumber(String panNumber) {
        String panPattern = "[A-Z]{5}[0-9]{4}[A-Z]";
        return panNumber.matches(panPattern);
    }

    public static void showChangePassword(final Activity activity, String msg, boolean isCancel) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(isCancel);
        dialog.setContentView(R.layout.popup);
        TextView tv_msg = dialog.findViewById(R.id.tv_msg);
        tv_msg.setText(msg);
        Button okBTN = dialog.findViewById(R.id.btn_ok);
        okBTN.setOnClickListener(arg0 -> {
            dialog.dismiss();
            activity.startActivity(new Intent(activity, LoginActivity.class));
            activity.finish();
        });
        dialog.show();
    }

    public static String getQuizRemainingTime(String endDateString) {
        try {
            //current date - end date
            endDateString = endDateString.replace("T", " ");
            endDateString = endDateString.replace("Z", " ");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            Date startDate = Calendar.getInstance().getTime();
            Date endDate = dateFormat.parse(endDateString);
            long timeDiff = endDate.getTime() - startDate.getTime();

            long days = timeDiff / (1000 * 60 * 60 * 24);
            long hours = (timeDiff / (1000 * 60 * 60)) % 24;
            long minutes = (timeDiff / (1000 * 60)) % 60;
            long seconds = (timeDiff / (1000)) % 60;

            if (days > 0) {
                return days + " days";
            } else {
                return hours + "h:" + minutes + "m:" + seconds + "s";
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getBattleRemainingTime(String endDateString) {
        try {
            endDateString = endDateString.replace("T", " ");
            endDateString = endDateString.replace("Z", " ");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            Date startDate = Calendar.getInstance().getTime();
            Date endDate = dateFormat.parse(endDateString);
            long timeDiff = endDate.getTime() - startDate.getTime();
            long days = timeDiff / (1000 * 60 * 60 * 24);
            long hours = (timeDiff / (1000 * 60 * 60)) % 24;
            long minutes = (timeDiff / (1000 * 60)) % 60;
            long seconds = (timeDiff / (1000)) % 60;
            String hr = String.valueOf(hours), min = String.valueOf(minutes), sec = String.valueOf(seconds);
            if (hours < 10) {
                hr = "0" + hours;
            }
            if (minutes < 10) {
                min = "0" + minutes;
            }
            if (seconds < 10) {
                sec = "0" + seconds;
            }
            if (days > 0) {
                return days + " days";
            } else if (hours > 0) {
                return hr + ":" + min + ":" + sec + "";
            } else {
                return min + ":" + sec + "";
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void showLogout(final Activity activity, int from, String msg) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.logout);
        TextView tv_msg = dialog.findViewById(R.id.tv_msg);
        Button mOkBTN = dialog.findViewById(R.id.btn_ok);
        Button mNoBTN = dialog.findViewById(R.id.btn_no);
        if (from == AppConstant.FROM_LOGOUT) {
            tv_msg.setText(R.string.txt_alert_logout);
        } else {
            tv_msg.setText(msg);
            mNoBTN.setVisibility(View.GONE);
            mOkBTN.setText(R.string.ok);
        }
        mOkBTN.setOnClickListener(arg0 -> {
            //logoutListener.onLogoutConfirm();
            AppSharedPreference.getInstance().clearEditor();
            Intent logout = new Intent(activity, SplashActivity.class);
            // Closing all the Activities
            logout.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            // Add new Flag to start new Activity
            logout.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            deleteCache(activity);
            dialog.dismiss();
            activity.finish();
            activity.finishAffinity();
            activity.startActivity(logout);
            MoECoreHelper.INSTANCE.logoutUser(context);
        });
        mNoBTN.setOnClickListener(arg0 -> dialog.dismiss());
        dialog.show();
    }

    public static String getKeyHash(final Activity activity) {
        PackageInfo info;
        String hashKey = "";
        try {
            info = activity.getPackageManager().getPackageInfo("com.khiladiadda", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                hashKey = new String(Base64.encode(md.digest(), 0));
                Log.e("HASH", hashKey);
                //String something = new String(Base64.encodeBytes(md.digest()));
                //                Log.e("hash key", hashKey);
                //SHA1: 05:29:29:32:FA:2C:0C:BD:6A:62:05:9F:8D:8B:F3:A7:83:1B:8B:D7
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("no such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        return hashKey;
    }

    public static String getVersion() {
        String versionCode = "";
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            versionCode = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    public static String getURL(String url) {
        String text = "";
        try {
            byte[] data = Base64.decode(url, Base64.DEFAULT);
            text = new String(data, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return text;
    }

    public static void showRegistrationConfirmation(final Activity activity, String msg, boolean isCancel) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(isCancel);
        dialog.setCancelable(isCancel);
        dialog.setContentView(R.layout.popup);
        TextView tv_msg = dialog.findViewById(R.id.tv_msg);
        tv_msg.setText(msg);
        Button okBTN = dialog.findViewById(R.id.btn_ok);
        okBTN.setOnClickListener(arg0 -> {
            dialog.dismiss();
        });
        dialog.show();
    }

    public static void showEmailUpdate(final Activity activity, String msg, boolean isCancel) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(isCancel);
        dialog.setCancelable(isCancel);
        dialog.setContentView(R.layout.popup);
        TextView tv_msg = dialog.findViewById(R.id.tv_msg);
        tv_msg.setText(msg);
        Button okBTN = dialog.findViewById(R.id.btn_ok);
        okBTN.setOnClickListener(arg0 -> {
            dialog.dismiss();
            activity.startActivity(new Intent(activity, ProfileActivity.class));
        });
        dialog.show();
    }

    public static void showPaymentConfirm(final Activity activity, String teamUniqueCode, final IOnLeagueJoinListener listener) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.share_teamid_dialog);
        TextView tv_msg = dialog.findViewById(R.id.tv_msg);
        tv_msg.setText("Team has been created successfully and your TeamId is " + teamUniqueCode + ". Please share this TeamId with your teammates.");
        Button mOkBTN = dialog.findViewById(R.id.btn_ok);
        mOkBTN.setText(R.string.text_share_teamid);
        mOkBTN.setOnClickListener(arg0 -> {
            dialog.dismiss();
            listener.onLeagueJoin(true, teamUniqueCode, AppConstant.SQUAD);
        });
        Button mNoBTN = dialog.findViewById(R.id.btn_no);
        mNoBTN.setText(R.string.ok);
        mNoBTN.setOnClickListener(arg0 -> {
            dialog.dismiss();
            listener.onLeagueJoin(false, teamUniqueCode, AppConstant.SQUAD);
        });
        dialog.show();
    }

    public static void showRechargeMsg(final Activity activity, String msg) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.logout);
        TextView tv_msg = dialog.findViewById(R.id.tv_msg);
        tv_msg.setText(msg);
        Button mOkBTN = dialog.findViewById(R.id.btn_ok);
        mOkBTN.setOnClickListener(arg0 -> {
            dialog.dismiss();
            activity.startActivity(new Intent(activity, AddWalletActivity.class));
        });
        Button mNoBTN = dialog.findViewById(R.id.btn_no);
        mNoBTN.setText(R.string.cancel);
        mNoBTN.setOnClickListener(arg0 -> dialog.dismiss());
        dialog.show();
    }

    public static void showRechargeMsghth(Context activity, String msg) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.logout);
        TextView tv_msg = dialog.findViewById(R.id.tv_msg);
        tv_msg.setText(msg);
        Button mOkBTN = dialog.findViewById(R.id.btn_ok);
        mOkBTN.setOnClickListener(arg0 -> {
            dialog.dismiss();
            activity.startActivity(new Intent(activity, AddWalletActivity.class));
        });
        Button mNoBTN = dialog.findViewById(R.id.btn_no);
        mNoBTN.setText(R.string.cancel);
        mNoBTN.setOnClickListener(arg0 -> dialog.dismiss()
        );
        dialog.show();
    }

    public static void showVideoMsg(final Activity activity, boolean isCancel) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(isCancel);
        dialog.setContentView(R.layout.video_help);
        Button videoBTN = dialog.findViewById(R.id.btn_video);
        setAnimation(activity, videoBTN);
        videoBTN.setOnClickListener(arg0 -> {
            dialog.dismiss();
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + "www.youtube.com/playlist?list=PLIvWNKDITNJA-lKa_RUj6L1mJvfy8McSG"));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivity(intent);
            } catch (ActivityNotFoundException e) {
                // youtube is not installed.Will be opened in other available apps
                activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/playlist?list=PLIvWNKDITNJA-lKa_RUj6L1mJvfy8McSG")));
            }
            AppSharedPreference.getInstance().setIsVideoSeen(true);
        });
        TextView tv_msg = dialog.findViewById(R.id.tv_msg);
        tv_msg.setText(R.string.video_message);
        Button mOkBTN = dialog.findViewById(R.id.btn_ok);
        mOkBTN.setOnClickListener(arg0 -> dialog.dismiss());
        Button mNoBTN = dialog.findViewById(R.id.btn_no);
        mNoBTN.setOnClickListener(arg0 -> {
            dialog.dismiss();
            AppSharedPreference.getInstance().setIsVideoSeen(true);
        });
        dialog.show();
    }

    public static void showCODVideoMsg(final Activity activity, boolean isCancel) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(isCancel);
        dialog.setContentView(R.layout.video_help);
        Button videoBTN = dialog.findViewById(R.id.btn_video);
        setAnimation(activity, videoBTN);
        videoBTN.setOnClickListener(arg0 -> {
            dialog.dismiss();
            openLudoVideo(activity);
            AppSharedPreference.getInstance().setIsCODVideoSeen(true);
        });
        TextView tv_msg = dialog.findViewById(R.id.tv_msg);
        tv_msg.setText(R.string.video_cod_message);
        Button mOkBTN = dialog.findViewById(R.id.btn_ok);
        mOkBTN.setOnClickListener(arg0 -> dialog.dismiss());
        Button mNoBTN = dialog.findViewById(R.id.btn_no);
        mNoBTN.setOnClickListener(arg0 -> {
            dialog.dismiss();
            AppSharedPreference.getInstance().setIsCODVideoSeen(true);
        });
        dialog.show();
    }

    public static void showTutorialMsg(final Activity activity, boolean isCancel) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(isCancel);
        dialog.setContentView(R.layout.video_help);
        Button videoBTN = dialog.findViewById(R.id.btn_video);
        setAnimation(activity, videoBTN);
        videoBTN.setOnClickListener(arg0 -> {
            dialog.dismiss();
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + "www.youtube.com/playlist?list=PLIvWNKDITNJA-lKa_RUj6L1mJvfy8McSG"));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivity(intent);
            } catch (ActivityNotFoundException e) {
                // youtube is not installed.Will be opened in other available apps
                activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/playlist?list=PLIvWNKDITNJA-lKa_RUj6L1mJvfy8McSG")));
            }
            AppSharedPreference.getInstance().setIsTutorialSeen(true);
        });
        TextView tv_msg = dialog.findViewById(R.id.tv_msg);
        tv_msg.setText(R.string.tutorial_message);
        Button mOkBTN = dialog.findViewById(R.id.btn_ok);
        mOkBTN.setOnClickListener(arg0 -> dialog.dismiss());
        Button mNoBTN = dialog.findViewById(R.id.btn_no);
        mNoBTN.setOnClickListener(arg0 -> {
            dialog.dismiss();
            AppSharedPreference.getInstance().setIsTutorialSeen(true);
        });
        dialog.show();
    }

    public static void showJoinMsg(final Context activity, String msg, boolean isCancel, final IOnLeagueJoinListener listener, String from) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(isCancel);
        dialog.setContentView(R.layout.jointeampopup);
        TextView tv_msg = dialog.findViewById(R.id.tv_msg);
        tv_msg.setText(msg);
        Button okBTN = dialog.findViewById(R.id.btn_ok);
        okBTN.setOnClickListener(arg0 -> {
            dialog.dismiss();
            listener.onLeagueJoin(false, "", from);
        });
        dialog.show();
    }

    @Nullable
    public static String getPath(Context context, Uri uri) {
        // DocumentProvider
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            } else if (isDownloadsDocument(uri)) {// DownloadsProvider
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);

            } else if (isMediaDocument(uri)) { // MediaProvider
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};
                return getDataColumn(context, contentUri, selection, selectionArgs);

            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {// MediaStore (and general)
            // Return the remote address
            if (isGooglePhotosUri(uri)) return uri.getLastPathSegment();
            return getDataColumn(context, uri, null, null);

        } else if ("file".equalsIgnoreCase(uri.getScheme())) {// File
            return uri.getPath();
        }
        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null) cursor.close();
        }
        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    public static String getMimeType(Context context, Uri uri) {
        String extension;
        //Check uri format to avoid null
        if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            //If scheme is a content
            final MimeTypeMap mime = MimeTypeMap.getSingleton();
            extension = mime.getExtensionFromMimeType(context.getContentResolver().getType(uri));
        } else {
            //If scheme is a File
            //This will replace white spaces with %20 and also other special characters. This will avoid returning null values on file name with spaces and special characters.
            extension = MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(new File(uri.getPath())).toString());
        }
        return extension;
    }

    public static boolean isAppInstalled(Context context, String packageName) {
        try {
            context.getPackageManager().getApplicationInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public static void saveMasterData(MasterResponse responseModel) {
        AppSharedPreference mAppPreference = AppSharedPreference.getInstance();
        mAppPreference.setDateSaveMaster(Calendar.getInstance().getTimeInMillis());
        MasterDetails masterDetails = responseModel.getResponse();
        mAppPreference.setMasterData(responseModel);
        //profile
        ProfileDetails profileDetail = masterDetails.getProfile();
        mAppPreference.setProfileData(profileDetail);
        mAppPreference.setString(AppConstant.USER_ID, profileDetail.getId());
        mAppPreference.setEmail(profileDetail.getEmail());
        mAppPreference.setUrl(profileDetail.getDp());
        mAppPreference.setName(profileDetail.getName());
        mAppPreference.setBoolean(AppConstant.USER_BLOCKED, profileDetail.getBlocked());
        if (!TextUtils.isEmpty(String.valueOf(profileDetail.getMobile())) || !String.valueOf(profileDetail.getMobile()).startsWith("8888888888")) {
            mAppPreference.setMobile(String.valueOf(profileDetail.getMobile()));
        }
        mAppPreference.setInviteCode(profileDetail.getInvitecode());
        //version
        if (masterDetails.getVersion().getAppVersion().equalsIgnoreCase(AppUtilityMethods.getVersion())) {
            mAppPreference.setIsVersionUpdated(false);
        } else {
            mAppPreference.setIsVersionUpdated(true);
        }
        mAppPreference.setString(AppConstant.VERSION, masterDetails.getVersion().getAppVersion());
        mAppPreference.setString(AppConstant.VERSION_DESC, masterDetails.getVersion().getUpdateDescription());
        mAppPreference.setString(AppConstant.VERSION_LINK, masterDetails.getVersion().getApkLink());
        mAppPreference.setString(AppConstant.VERSION_SIZE, String.valueOf(masterDetails.getVersion().getApkSize()));
        //gameId, categoryId
        List<Active> gameList = masterDetails.getGames();
        for (int i = 0; i < gameList.size(); i++) {
            Active game = gameList.get(i);
            PreferenceKeysModel preferenceKeysModel = getPreferenceKeysModel(game.getTitle());
            mAppPreference.setString(preferenceKeysModel.getIdKey(), game.getId());
            List<GameCategory> gameCategory = game.getCategories();
            for (int j = 0; j < gameCategory.size(); j++) {
                GameCategory gameCat = gameCategory.get(j);
                if (gameCat.getTitle().equalsIgnoreCase(AppConstant.SOLO)) {
                    mAppPreference.setString(preferenceKeysModel.getSoloKey(), gameCat.getId());
                }
                if (gameCat.getTitle().equalsIgnoreCase(AppConstant.DUO)) {
                    mAppPreference.setString(preferenceKeysModel.getDuoKey(), gameCat.getId());
                }
                if (gameCat.getTitle().equalsIgnoreCase(AppConstant.SQUAD)) {
                    mAppPreference.setString(preferenceKeysModel.getSquadKey(), gameCat.getId());
                }
                if (game.getTitle().equalsIgnoreCase(AppConstant.FAN_BATTLE)) {
                    mAppPreference.setString(preferenceKeysModel.getIdKey(), gameCat.getId());
                }
            }
        }
        //quiz category
        List<CategoryList> categoryLists = masterDetails.getQuizzesCategories();
        for (int i = 0; i < categoryLists.size(); i++) {
            String name = categoryLists.get(i).getName();
            CategoryList categoryData = categoryLists.get(i);
            if (name.equalsIgnoreCase("Picture Quiz") || name.equalsIgnoreCase("Picture")) {
                mAppPreference.setPicture(categoryData.getId());
            } else if (name.equalsIgnoreCase("Gaming Quiz") || name.equalsIgnoreCase("Gaming")) {
                mAppPreference.setGaming(categoryData.getId());
            } else if (name.equalsIgnoreCase("WebSeries Quiz") || name.equalsIgnoreCase("WebSeries") || name.equalsIgnoreCase("Web Series")) {
                mAppPreference.setWebSeries(categoryData.getId());
            } else if (name.equalsIgnoreCase("Logo Quiz") || name.equalsIgnoreCase("Logo")) {
                mAppPreference.setLogo(categoryData.getId());
            } else if (name.equalsIgnoreCase("Audio")) {
                mAppPreference.setAudio(categoryData.getId());
            } else if (name.equalsIgnoreCase("Video")) {
                mAppPreference.setVideo(categoryData.getId());
            } else if (name.equalsIgnoreCase("Sports Quiz") || name.equalsIgnoreCase("Sports")) {
                mAppPreference.setSports(categoryData.getId());
            } else if (name.equalsIgnoreCase("Technology Quiz") || name.equalsIgnoreCase("Technology") || name.equalsIgnoreCase("Technology ")) {
                mAppPreference.setTechnology(categoryData.getId());
            } else if (name.equalsIgnoreCase("Science Quiz") || name.equalsIgnoreCase("Science")) {
                mAppPreference.setScience(categoryData.getId());
            } else if (name.equalsIgnoreCase("Prediction Quiz") || name.equalsIgnoreCase("Prediction")) {
                mAppPreference.setPrediction(categoryData.getId());
            } else if (name.equalsIgnoreCase("Maths") || name.equalsIgnoreCase("Math") || name.equalsIgnoreCase("Maths Quiz")) {
                mAppPreference.setMath(categoryData.getId());
            } else if (name.equalsIgnoreCase("GK") || name.equalsIgnoreCase("G.K Quiz") || name.equalsIgnoreCase("G.K.")) {
                mAppPreference.setGK(categoryData.getId());
            } else if (name.equalsIgnoreCase("Movie") || name.equalsIgnoreCase("Movie Quiz") || name.equalsIgnoreCase("Movies")) {
                mAppPreference.setMovie(categoryData.getId());
            }
        }
    }

    public static PreferenceKeysModel getPreferenceKeysModel(String gameName) {
        if (gameName.equalsIgnoreCase(AppConstant.FROM_VIEW_PREMIUM_ESPORTS)) {
            return new PreferenceKeysModel(AppConstant.PREMIUM_ESPORTS_ID, AppConstant.PREMIUM_ESPORTS_SOLO, AppConstant.PREMIUM_ESPORTS_DUO, AppConstant.PREMIUM_ESPORTS_SQUAD);
        }
        if (gameName.equalsIgnoreCase(AppConstant.FROM_VIEW_PUBG_GLOBAL)) {
            return new PreferenceKeysModel(AppConstant.PUBG_GLOBAL_ID, AppConstant.PUBG_GLOBAL_SOLO, AppConstant.PUBG_GLOBAL_DUO, AppConstant.PUBG_GLOBAL_SQUAD);
        }
        if (gameName.equalsIgnoreCase(AppConstant.FROM_VIEW_TDM)) {
            return new PreferenceKeysModel(AppConstant.PUBG_ID, AppConstant.PUBG_SOLO, AppConstant.PUBG_DUO, AppConstant.PUBG_SQUAD);
        }
        if (gameName.equalsIgnoreCase(AppConstant.FROM_VIEW_BGMI)) {
            return new PreferenceKeysModel(AppConstant.PUBG_LITE_ID, AppConstant.PUBG_LITE_SOLO, AppConstant.PUBG_LITE_DUO, AppConstant.PUBG_LITE_SQUAD);
        }
        if (gameName.equalsIgnoreCase(AppConstant.CALLOFDUTY)) {
            return new PreferenceKeysModel(AppConstant.CALL_DUTY_ID, AppConstant.CALL_DUTY_SOLO, AppConstant.CALL_DUTY_DUO, AppConstant.CALL_DUTY_SQUAD);
        }
        if (gameName.equalsIgnoreCase(AppConstant.FREEFIRE)) {
            return new PreferenceKeysModel(AppConstant.FREEFIRE_ID, AppConstant.FREEFIRE_SOLO, AppConstant.FREEFIRE_DUO, AppConstant.FREEFIRE_SQUAD);
        }
        if (gameName.equalsIgnoreCase(AppConstant.FROM_VIEW_LUDO)) {
            return new PreferenceKeysModel(AppConstant.LUDO_ID);
        }
        if (gameName.equalsIgnoreCase(AppConstant.FROM_VIEW_LUDO_UNIVERSE)) {
            return new PreferenceKeysModel(AppConstant.LUDO_UNIVERSE_ID);
        }
        if (gameName.equalsIgnoreCase(AppConstant.CLASHROYALE)) {
            return new PreferenceKeysModel(AppConstant.CLASHROYALE_ID);
        }
        if (gameName.equalsIgnoreCase(AppConstant.SNAKE_LADDER)) {
            return new PreferenceKeysModel(AppConstant.SNAKE_LADDER_ID);
        }
        if (gameName.equalsIgnoreCase(AppConstant.FF_CLASH)) {
            return new PreferenceKeysModel(AppConstant.FF_CLASH_ID, AppConstant.FF_CLASH_SOLO, AppConstant.FF_CLASH_DUO, AppConstant.FF_CLASH_SQUAD);
        }
        if (gameName.equalsIgnoreCase(AppConstant.FAN_BATTLE)) {
            return new PreferenceKeysModel(AppConstant.FAN_BATTLE_GAME_ID);
        }
        if (gameName.equalsIgnoreCase(AppConstant.CLASH_X)) {
            return new PreferenceKeysModel(AppConstant.CLASH_X_GAME_ID);
        }
        if (gameName.equalsIgnoreCase(AppConstant.FF_MAX)) {
            return new PreferenceKeysModel(AppConstant.FF_MAX_ID, AppConstant.FF_MAX_SOLO, AppConstant.FF_MAX_DUO, AppConstant.FF_MAX_SQUAD);
        }
        if (gameName.equalsIgnoreCase(AppConstant.FROM_VIEW_PUBG_NEWSTATE)) {
            return new PreferenceKeysModel(AppConstant.PUBG_NEWSTATE_ID, AppConstant.PUBG_NEWSTATE_SOLO, AppConstant.PUBG_NEWSTATE_DUO, AppConstant.PUBG_NEWSTATE_SQUAD);
        }
        return new PreferenceKeysModel();
    }

    public static void openYoutube(final Activity activity) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + "www.youtube.com/channel/UCTW46GzbnxyHO0s4ekHmAeg"));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(intent);
        } catch (Exception e) {
            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/channel/UCTW46GzbnxyHO0s4ekHmAeg")));
        }
    }

    public static void openYoutubeCallbreak(final Activity activity, final String link, final String linkLast) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + link));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(intent);
        } catch (Exception e) {
            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(linkLast)));
        }
    }

    public static void openInstagram(final Activity activity) {
        try {
            Uri uri = Uri.parse("https://instagram.com/khiladiadda?igshid=lc8vrajuubq4");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setPackage("com.instagram.android");
            activity.startActivity(intent);
        } catch (Exception e) {
            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://instagram.com/khiladiadda?igshid=lc8vrajuubq4")));
        }
    }

    public static void openTutorial(final Activity activity) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + "www.youtube.com/playlist?list=PLIvWNKDITNJA-lKa_RUj6L1mJvfy8McSG"));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(intent);
        } catch (Exception e) {
            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/playlist?list=PLIvWNKDITNJA-lKa_RUj6L1mJvfy8McSG")));
        }
    }

    public static void inviteOnWhatsApp(Activity activity, Button mOptionBTN) {
        Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
        whatsappIntent.setType("text/plain");
        whatsappIntent.setPackage("com.whatsapp");
        whatsappIntent.putExtra(Intent.EXTRA_TEXT, "Hi, Please download this app and register through my referral code: " + AppSharedPreference.getInstance().getInviteCode() + " " + AppConstant.WEBSITE_URL);
        try {
            activity.startActivity(whatsappIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            Snackbar.make(mOptionBTN, "Whatsapp is not installed on your device.", Snackbar.LENGTH_LONG).show();
        }
    }

    public static void shareInviteCode(Activity activity) {
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.putExtra(Intent.EXTRA_TEXT, "Hi, Please download this app and register through my referral code: " + AppSharedPreference.getInstance().getInviteCode() + " " + AppConstant.WEBSITE_URL);
        activity.startActivity(Intent.createChooser(share, "Referral Code"));
    }

    public static void setAnimation(Activity activity, TextView btn) {
        ColorDrawable[] color = {new ColorDrawable(activity.getResources().getColor(R.color.view_video_color)), new ColorDrawable(Color.YELLOW)};
        TransitionDrawable trans = new TransitionDrawable(color);
        btn.setBackground(trans);
        trans.startTransition(7000); // duration 3 seconds
    }

    public static void setLudoAnimation(Activity activity, TextView btn) {
        ColorDrawable[] color = {new ColorDrawable(activity.getResources().getColor(R.color.colorPrimaryDark)), new ColorDrawable(activity.getResources().getColor(R.color.colorPrimary))};
        TransitionDrawable trans = new TransitionDrawable(color);
        btn.setBackground(trans);
        trans.startTransition(7000); // duration 3 seconds
    }

    public static void openLudoVideo(Activity activity) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + "www.youtube.com/playlist?list=PLIvWNKDITNJA-lKa_RUj6L1mJvfy8McSG"));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(intent);
        } catch (Exception e) {
            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/playlist?list=PLIvWNKDITNJA-lKa_RUj6L1mJvfy8McSG")));
        }
    }

    public static void openLudoKing(Activity activity) {
        try {
            boolean isAppInstall = AppUtilityMethods.isAppInstalled(activity, "com.ludo.king");
            if (isAppInstall) {
                Intent i = activity.getPackageManager().getLaunchIntentForPackage("com.ludo.king");
                activity.startActivity(i);
            } else {
                activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.ludo.king")));
            }
        } catch (Exception e) {
            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.ludo.king")));
        }
    }

    public static boolean installedGamerPE(Activity activity) {
        try {
            boolean isAppInstall = AppUtilityMethods.isAppInstalled(activity, "com.gamerpe.in");
            return isAppInstall;
        } catch (Exception e) {
            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.gamerpe.in")));
        }
        return false;
    }


    public static List<String> getLudoErrorReason() {
        List arrayListMake = new ArrayList<String>();
        arrayListMake.add("Select Reason");
        arrayListMake.add("Opponent didn't came on Ludo King");
        arrayListMake.add("Opponent updated won after losing");
        arrayListMake.add("Ludo King application was not working");
        arrayListMake.add("Other");
        return arrayListMake;
    }

    public static void showInfo(Activity activity, TextView mTV, String textMsg) {
        View prizePoolView = LayoutInflater.from(activity).inflate(R.layout.bonus_info_popup, null);
        PopupWindow mWindow = new PopupWindow(prizePoolView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        if (Build.VERSION.SDK_INT >= 21) {
            mWindow.setElevation(5.0f);
        }
        mWindow.setOutsideTouchable(true);
        mWindow.setFocusable(true);
        mWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ((TextView) prizePoolView.findViewById(R.id.tv_bonus_info)).setText(textMsg);
        mWindow.showAsDropDown(mTV, (int) -(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 150, activity.getResources().getDisplayMetrics())), 0, Gravity.END);
    }


    public static void showBlockMsg(final Activity activity, String msg) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.popup);
        TextView tv_msg = dialog.findViewById(R.id.tv_msg);
        tv_msg.setText(msg);
        Button okBTN = dialog.findViewById(R.id.btn_ok);
        okBTN.setOnClickListener(arg0 -> {
            dialog.dismiss();
            activity.finishAffinity();
            activity.finish();
        });
        dialog.show();
    }

    public static String getDeviceUniqueID(Activity activity) {
        return Settings.Secure.getString(activity.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static boolean getTimeDifference(String codeDateTime) {
        try {
            //current date - code date
            codeDateTime = codeDateTime.replace("T", " ");
            codeDateTime = codeDateTime.replace("Z", " ");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            Date currentDate = Calendar.getInstance().getTime();
            Date codeDate = dateFormat.parse(codeDateTime);
            long timeDiff = currentDate.getTime() - codeDate.getTime();
            long minutes = (timeDiff / (1000 * 60)) % 60;
            return minutes <= 15;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static double getBattleEstimatedWinning(double mBattleInvestedAmount, double mGroupInvestedAmount, long mGroupJoined) {
        double estimatedWinning = 0;
        if (mBattleInvestedAmount < 1) {
            mBattleInvestedAmount = 0;
        }
        if (mGroupInvestedAmount < 1) {
            mGroupInvestedAmount = 0;
        }
        if (mGroupJoined > 1) {
            estimatedWinning = (mBattleInvestedAmount - (mBattleInvestedAmount / 10)) * (mBattleInvestedAmount / mGroupInvestedAmount);
        } else {
            estimatedWinning = mBattleInvestedAmount * 1.2;
        }
        return estimatedWinning;
    }

    public static double getGroupEstimatedWinning(double mBattleInvestedAmount, double mGroupInvestedAmount, long mGroupJoined, double amount) {
        double estimatedWinning = 0;
        if (mGroupJoined > 1) {
            estimatedWinning = (mBattleInvestedAmount - (mBattleInvestedAmount / 10)) * (amount / mGroupInvestedAmount);
        } else {
            estimatedWinning = amount * 1.2;
        }
        return estimatedWinning;
    }

    public static boolean isDateTomorrow(String date) {
        Date current = new Date();
        Date givenDate = null;
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            givenDate = df.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
        //compare both dates
        if (givenDate.before(current)) {
            System.out.println("The date is older than current day");
            return true;
        } else {
            System.out.println("The date is future day");
            return false;
        }
    }

    public static boolean checkDates(String d1, String d2) {
        SimpleDateFormat dfDate = new SimpleDateFormat("dd MMM yyyy");
        boolean b = false;
        try {
            if (dfDate.parse(d2).before(dfDate.parse(d1))) {
                b = true;//If start date is before end date
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return b;
    }


    public static void showCreateBattleMsg(final Activity activity, String msg, int from) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.popup);
        TextView tv_msg = dialog.findViewById(R.id.tv_msg);
        if (from == 1) {
            tv_msg.setText(msg);
        } else {
            tv_msg.setText(msg);
        }
        Button okBTN = dialog.findViewById(R.id.btn_ok);
        okBTN.setOnClickListener(arg0 -> {
            dialog.dismiss();
            activity.finish();
            AppSharedPreference.getInstance().setBoolean(AppConstant.GROUP_JOINED, true);
        });
        dialog.show();
    }

    /**
     * Round Up Number
     **/
    public static String roundUpNumber(double number) {
        char[] suffix = {' ', 'k', 'M', 'B', 'T', 'P', 'E'};
        double numValue = number;
        int value = (int) Math.floor(Math.log10(numValue));
        int base = value / 3;
        if (value >= 3 && base < suffix.length) {
            return new DecimalFormat("#0.0").format(numValue / Math.pow(10, base * 3)) + suffix[base];
        } else {
            return new DecimalFormat("#,##0").format(numValue);
        }
    }

    public static String secure() throws NoSuchAlgorithmException {
        String encryptPassword = AppConstant.VALUE_ENCRYPTION + AppConstant.Enrcy;
        MessageDigest digest;
        String hash;
        digest = MessageDigest.getInstance("SHA-256");
        digest.update(encryptPassword.getBytes());
        hash = bytesToHex(digest.digest());
        return hash;
    }

    public static String hash256(String data) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(data.getBytes());
        return bytesToHex(md.digest());
    }

    public static String bytesToHex(byte[] bytes) {
        StringBuffer result = new StringBuffer();
        for (byte byt : bytes) {
            result.append(Integer.toString((byt & 0xff) + 0x100, 16).substring(1));
        }
        return result.toString();
    }

    @android.annotation.TargetApi(17)
    public static boolean isDevMode() {
        if (Integer.valueOf(android.os.Build.VERSION.SDK) == 16) {
            return android.provider.Settings.Secure.getInt(context.getContentResolver(),
                    Settings.Secure.DEVELOPMENT_SETTINGS_ENABLED, 0) != 0;
        } else if (Integer.valueOf(Build.VERSION.SDK_INT) >= 17) {
            return android.provider.Settings.Secure.getInt(context.getContentResolver(),
                    android.provider.Settings.Global.DEVELOPMENT_SETTINGS_ENABLED, 0) != 0;
        } else return false;
    }

    public static boolean isRooted() {
        // get from build info
        String buildTags = android.os.Build.TAGS;
        if (buildTags != null && buildTags.contains("test-keys")) {
            return true;
        }
        // check if /system/app/Superuser.apk is present
        try {
            File file = new File("/system/app/Superuser.apk");
            if (file.exists()) {
                return true;
            }
        } catch (Exception e1) {
            // ignore
        }
        // try executing commands
        return canExecuteCommand("/system/xbin/which su")
                || canExecuteCommand("/system/bin/which su") || canExecuteCommand("which su");
    }

    // executes a command on the system
    private static boolean canExecuteCommand(String command) {
        boolean executedSuccesfully;
        try {
            Runtime.getRuntime().exec(command);
            executedSuccesfully = true;
        } catch (Exception e) {
            executedSuccesfully = false;
        }
        return executedSuccesfully;
    }


    public static boolean isDeviceRooted() {
        return checkRootMethod1() || checkRootMethod2() || checkRootMethod3();
    }

    private static boolean checkRootMethod1() {
        String buildTags = android.os.Build.TAGS;
        return buildTags != null && buildTags.contains("test-keys");
    }

    private static boolean checkRootMethod2() {
        String[] paths = {"/system/app/Superuser.apk", "/sbin/su", "/system/bin/su", "/system/xbin/su", "/data/local/xbin/su", "/data/local/bin/su", "/system/sd/xbin/su",
                "/system/bin/failsafe/su", "/data/local/su", "/su/bin/su"};
        for (String path : paths) {
            if (new File(path).exists()) return true;
        }
        return false;
    }

    private static boolean checkRootMethod3() {
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(new String[]{"/system/xbin/which", "su"});
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            if (in.readLine() != null) return true;
            return false;
        } catch (Throwable t) {
            return false;
        } finally {
            if (process != null) process.destroy();
        }
    }

    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (String child : children) {
                boolean success = deleteDir(new File(dir, child));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if (dir != null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }

    public static String getDayLeft(String date) {
        try {
            String totalDates = null;
            Date dates;
            dates = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                    .parse(date);

            long remaining = 0;
            if (dates.getTime() != 0) {
                remaining = dates.getTime() - System.currentTimeMillis();
            }

            //Set time in milliseconds
            if (remaining > 0) {
                int days = (int) ((remaining / (1000 * 60 * 60 * 24)));
                long seconds = ((remaining / 1000) % 60);
                long minutes = ((remaining / (1000 * 60)) % 60);
                long hours = ((remaining / (1000 * 60 * 60)) % 24);
                if (days <= 1) {
                    totalDates = String.format("Ends in: %02d:%02d:%02d", hours, minutes, seconds);
                } else if (remaining != 0) {
                    totalDates = "Ends in: " + days + " Days Left";
                }
                if (days == 0 && remaining == 0) {
                    totalDates = "Ended";
                }
                return totalDates;
            } else
                return "Ends in: 00:00:00";
        } catch (ParseException e) {
            Log.e("TAG", "e: " + e.getLocalizedMessage());
        }
        return "";
    }

    public static boolean getTimeDiff(String date, Date CurrentTime) {
        Date dates1, dates2;
        String currentDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(CurrentTime);

        try {
            dates1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(date);
            dates2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(currentDate);

            return dates1.before(dates2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void showStoragePermisisionMsg(final Activity activity, String msg, boolean isCancel) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(isCancel);
        dialog.setCancelable(isCancel);
        dialog.setContentView(R.layout.popup);
        TextView tv_msg = dialog.findViewById(R.id.tv_msg);
        tv_msg.setText("You need to allow Storage or Files and Media Permission from your Permissions section.");
        tv_msg.setTextSize(15);
        tv_msg.setTypeface(tv_msg.getTypeface(), Typeface.BOLD);
        Button okBTN = dialog.findViewById(R.id.btn_ok);
        okBTN.setText("Allow Now");
        okBTN.setOnClickListener(arg0 -> {
            Intent intent;
            if (SDK_INT >= Build.VERSION_CODES.R) {
                if (Environment.isExternalStorageManager()) {
                } else {
                    intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                    Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
                    intent.setData(uri);
                    activity.startActivity(intent);
                }

            } else {
                if (PermissionUtils.hasStoragePermission(activity)) {
                } else {
                    Intent intent1 = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
                    intent1.setData(uri);
                    activity.startActivity(intent1);
                }

            }

            dialog.dismiss();
        });
        dialog.show();
    }

    public static String getTimeLeft(String codeDateTime) {
        try {
            //current date - code date
            codeDateTime = codeDateTime.replace("T", " ");
            codeDateTime = codeDateTime.replace("Z", " ");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            Date currentDate = Calendar.getInstance().getTime();
            Date codeDate = dateFormat.parse(codeDateTime);
            long timeDiff = currentDate.getTime() - codeDate.getTime();
            long hour = 48 - (timeDiff / (1000 * 60 * 60)) % 24;
            return "" + hour + " hour";
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static long getTimeLeftWithMilliSec(String codeDateTime) {
        try {
            //current date - code date
            codeDateTime = codeDateTime.replace("T", " ");
            codeDateTime = codeDateTime.replace("Z", " ");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            Date currentDate = Calendar.getInstance().getTime();
            Date codeDate = dateFormat.parse(codeDateTime);
            long timeDiff = currentDate.getTime() - codeDate.getTime();
            return timeDiff;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0l;
    }

    public static void showMsgNew(final Context context, Activity activity, String msg, boolean isCancel, boolean doFinish) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(isCancel);
        dialog.setCancelable(isCancel);
        dialog.setContentView(R.layout.popup);
        TextView tv_msg = dialog.findViewById(R.id.tv_msg);
        tv_msg.setText(msg);
        Button okBTN = dialog.findViewById(R.id.btn_ok);
        okBTN.setOnClickListener(arg0 -> {
            dialog.dismiss();
            if (doFinish) {
                activity.finish();
            }
        });
        dialog.show();
    }

    //LUDO Tournament
    public static void showTooltip(Activity activity, TextView mTV, String textMsg, int type) {
        View ludoToolTip = LayoutInflater.from(activity).inflate(R.layout.rules_info_tooltip, null);
        PopupWindow mWindow = new PopupWindow(ludoToolTip, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        if (Build.VERSION.SDK_INT >= 21) {
            mWindow.setElevation(5.0f);
        }
        mWindow.setOutsideTouchable(true);
        mWindow.setFocusable(true);
        mWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView tvEnglish = ludoToolTip.findViewById(R.id.tv_english);
        TextView tvhindi = ludoToolTip.findViewById(R.id.tv_hindi);
        tvEnglish.setOnClickListener(view -> {
            Intent intent = new Intent(activity, LudoTmtRulesActivity.class);
            intent.putExtra("lang", "english");
            intent.putExtra("type", type);
            activity.startActivity(intent);
            mWindow.dismiss();
        });
        tvhindi.setOnClickListener(view -> {
            Intent intent = new Intent(activity, LudoTmtRulesActivity.class);
            intent.putExtra("lang", "hindi");
            intent.putExtra("type", type);
            activity.startActivity(intent);
            mWindow.dismiss();
        });
        mWindow.showAsDropDown(mTV, (int) -(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 150, activity.getResources().getDisplayMetrics())), 0, Gravity.END);
    }

    //LUDO Tournament
    public static void showTooltipFromImage(Activity activity, ImageView mTV, String textMsg, int type) {
        View ludoToolTip = LayoutInflater.from(activity).inflate(R.layout.rules_info_tooltip, null);
        PopupWindow mWindow = new PopupWindow(ludoToolTip, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        if (Build.VERSION.SDK_INT >= 21) {
            mWindow.setElevation(5.0f);
        }
        mWindow.setOutsideTouchable(true);
        mWindow.setFocusable(true);
        mWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView tvEnglish = ludoToolTip.findViewById(R.id.tv_english);
        TextView tvhindi = ludoToolTip.findViewById(R.id.tv_hindi);
        tvEnglish.setOnClickListener(view -> {
            Intent intent = new Intent(activity, LudoTmtRulesActivity.class);
            intent.putExtra("lang", "english");
            intent.putExtra("type", type);
            activity.startActivity(intent);
            mWindow.dismiss();
        });
        tvhindi.setOnClickListener(view -> {
            Intent intent = new Intent(activity, LudoTmtRulesActivity.class);
            intent.putExtra("lang", "hindi");
            intent.putExtra("type", type);
            activity.startActivity(intent);
            mWindow.dismiss();
        });
        mWindow.showAsDropDown(mTV, (int) -(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 150, activity.getResources().getDisplayMetrics())), 0, Gravity.END);
    }

}