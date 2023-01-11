package com.khiladiadda.fcm;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.appsflyer.AppsFlyerLib;
import com.google.firebase.messaging.RemoteMessage;
import com.khiladiadda.R;
import com.khiladiadda.main.MainActivity;
import com.khiladiadda.network.ApiManager;
import com.khiladiadda.network.ApiService;
import com.khiladiadda.network.SubscriberCallback;
import com.khiladiadda.network.model.request.FirebaseRequest;
import com.khiladiadda.network.model.request.NotificationModel;
import com.khiladiadda.preference.AppSharedPreference;
import com.khiladiadda.utility.AppConstant;
import com.moengage.firebase.MoEFireBaseHelper;
import com.moengage.firebase.listener.NonMoEngagePushListener;

import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Date;
import java.util.Map;

import io.kommunicate.Kommunicate;
import io.realm.Realm;

public class PushMessageListener implements NonMoEngagePushListener {
    private int mId, mType;
    private String mTitle, mMessage;
    protected Context context;

    public PushMessageListener(Context context) {
        this.context = context.getApplicationContext();
        onNewToken();
    }



    @Override
    public void onPushReceived(@NonNull RemoteMessage remoteMessage) {
        if (Kommunicate.isKmNotification(context, remoteMessage.getData())) {
            return;
        }
        if (remoteMessage.getData().size() > 0) {
            Map<String, String> dataMap = remoteMessage.getData();
            try {
                JSONObject json = new JSONObject(dataMap);
                mMessage = json.getString("body");
                mTitle = json.getString("title");
                mType = Integer.parseInt(json.getString("type"));
                if (mType == 13 || mType == 14 || mType == 15 || mType == 16 || mType == 17 || mType == 25 || mType == 26 || mType == 27 || mType == 32) {
                    Intent intent = new Intent("com.khiladiadda.LUDO_NOTIFY");
                    intent.putExtra(AppConstant.FROM, AppConstant.LUDO);
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                } else if (mType == 40 || mType == 42 || mType == 46) {
                    Intent intent = new Intent("com.khiladiadda.HTH_NOTIFY");
                    intent.putExtra(AppConstant.FROM, AppConstant.CLASHOFFANS);
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                } else if (mType == 48) {
                    Intent intent = new Intent("com.khiladiadda.HTH_MATCHES_NOTIFY");
                    intent.putExtra(AppConstant.FROM, AppConstant.HTHMATCHES);
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                } else if (mType == 49) {
                    Intent intent = new Intent("com.khiladiadda.HTH_MATCHES_LIVE_NOTIFY");
                    intent.putExtra(AppConstant.FROM, AppConstant.HTHLIVEREFRSH);
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                } else if (mType == 47) {
                    Intent intent = new Intent("com.khiladiadda.HTH_MATCHES_PAST_NOTIFY");
                    intent.putExtra(AppConstant.FROM, AppConstant.HTHPASTREFRSH);
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                } else if (mType == 51 || mType == 52 || mType == 53 || mType == 54) {
                    Intent intent = new Intent("com.khiladiadda.LUDO_UNI_NOTIFY");
                    intent.putExtra(AppConstant.FROM, AppConstant.LUDO);
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                }else if (mType==55||mType==56){
                    Intent intent = new Intent("com.khiladiadda.WEBPAYMENT_NOTIFY");
                    intent.putExtra(AppConstant.FROM, AppConstant.WebPayment);
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                }else if (mType==57||mType==58||mType==59){
                    Intent intent = new Intent("com.khiladiadda.WEBPAYMENTPAY_NOTIFY");
                    intent.putExtra(AppConstant.FROM, AppConstant.PaysharpPaymentDone);
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                }else if (mType == 61) {
                    Intent intent = new Intent(AppConstant.GAME_DROIDO_CLASS_PACKAGE);
                    intent.putExtra(AppConstant.FROM, AppConstant.DROIDO);
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                }
                sendNotification(mTitle, mMessage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public void onNewToken() {
        MoEFireBaseHelper.getInstance().addTokenListener(token -> {
            AppSharedPreference.getInstance().setDeviceToken(token.getPushToken());
            updateTokenOnServer(token.getPushToken());
            AppsFlyerLib.getInstance().updateServerUninstallToken(context, token.getPushToken());
        });

    }

    private void updateTokenOnServer(String token) {
        if (!AppSharedPreference.getInstance().getIsLogin()) {
            return;
        }
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        FirebaseRequest request = new FirebaseRequest(AppConstant.ANDORID, AppSharedPreference.getInstance().getString(AppConstant.UUID, ""), token);
        manager.createObservable(service.updateFirebaseId(request)).subscribe(new SubscriberCallback<>(null));
    }

    private void sendNotification(String title, String messageBody) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(AppConstant.FROM, AppConstant.FROM_NOTIFICATION);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0 /* Request code */, intent, PendingIntent.FLAG_ONE_SHOT);

        String channelId = context.getString(R.string.default_notification_channel_id);
        Uri defaultSoundUri = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.notification_new);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, channelId).
                setSmallIcon(R.mipmap.ic_launcher).
                setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher)).
                setContentTitle(title).
                setStyle(new NotificationCompat.BigTextStyle().bigText(messageBody)).
                setContentText(messageBody).
                setAutoCancel(true).setSound(defaultSoundUri).
                setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE).
                setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "Channel human readable mTitle", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        mId = AppSharedPreference.getInstance().getNotificationId();
        AppSharedPreference.getInstance().setNotificationId(mId + 1);
        AppSharedPreference.getInstance().setNotificationCount(AppSharedPreference.getInstance().getNotificationCount() + 1);
        AppSharedPreference.getInstance().setNotificationPending(true);

        saveData();

        notificationManager.notify(mId /* ID of notification */, notificationBuilder.build());
    }

    private void saveData() {
        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        final NotificationModel model = new NotificationModel(mId, mTitle, mMessage, currentDateTimeString, mType);
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> realm1.copyToRealmOrUpdate(model));
    }



}

