package com.hsd.omubumu.Notification;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.hsd.omubumu.Common.Types.Soru;
import com.hsd.omubumu.Common.Types.Uye;
import com.hsd.omubumu.Giris;
import com.hsd.omubumu.R;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by APALYazilim on 24.7.2015.
 */
public class GcmIntentService extends IntentService {
    public static final int NOTIFICATION_ID = 1;
    private NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;
    private String TAG="Ege Talk";
    public GcmIntentService() {
        super("GcmIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);

        String messageType = gcm.getMessageType(intent);

        if (!extras.isEmpty()) {
            if (GoogleCloudMessaging.
                    MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
            } else if (GoogleCloudMessaging.
                    MESSAGE_TYPE_DELETED.equals(messageType)) {
                // If it's a regular GCM message, do some work.
            } else if (GoogleCloudMessaging.
                    MESSAGE_TYPE_MESSAGE.equals(messageType)) {
                try {
                    JSONObject data = new JSONObject(extras.getString("Data"));
                    String title = extras.getString("Title");
                    String message = extras.getString("Message");
                    int type = extras.getInt("Type");
                    switch (type)
                    {
                        case 1:
                            OyNotification(title,message,new Soru(
                                    data.getString("SoruID"),
                                    data.getString("UyeID"),
                                    data.getString("Resim1"),
                                    data.getString("Resim2"),
                                    data.getString("Aciklama"),
                                    data.getString("Baslik"),
                                    data.getString("KategoriID"),
                                    data.getString("SaveDate"),
                                    data.getString("KategoriAdi"),
                                    data.getString("AdiSoyadi"),
                                    data.getString("KullaniciAdi"),
                                    data.getString("ProfileImage")
                            ));
                            break;
                        case 2:
                            YorumNotification(title,message,new Soru(
                                    data.getString("SoruID"),
                                    data.getString("UyeID"),
                                    data.getString("Resim1"),
                                    data.getString("Resim2"),
                                    data.getString("Aciklama"),
                                    data.getString("Baslik"),
                                    data.getString("KategoriID"),
                                    data.getString("SaveDate"),
                                    data.getString("KategoriAdi"),
                                    data.getString("AdiSoyadi"),
                                    data.getString("KullaniciAdi"),
                                    data.getString("ProfileImage")
                            ));
                            break;
                        default:
                            break;
                    }
                }
                catch (JSONException ex){
                    Log.d(TAG, ex.getMessage());
                }

            }
        }
        // Release the wake lock provided by the WakefulBroadcastReceiver.
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }

    private void OyNotification(String title,String Message,Soru soru){
        mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(this, Giris.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                intent, 0);


        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle(title)
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(Message))
                        .setAutoCancel(true)
                        .setSound(Uri.parse("android.resource://" + getPackageName() + "/"))
                        .setContentText(Message);
        mBuilder.setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 });
        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }

    private void YorumNotification(String title,String Message,Soru soru) {
        mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(this, Giris.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                intent, 0);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle(title)
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(Message))
                        .setAutoCancel(true)
                        .setSound(Uri.parse("android.resource://" + getPackageName() + "/"))
                        .setContentText(Message);
        mBuilder.setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 });
        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }

}