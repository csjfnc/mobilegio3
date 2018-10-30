package com.visium.fieldservice.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import com.google.android.gms.gcm.GcmListenerService;
import com.squareup.picasso.Picasso;
import com.visium.fieldservice.R;
import com.visium.fieldservice.ui.splash.SplashScreenActivity;
import com.visium.fieldservice.util.LogUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * @author Andrew Willard (andrewillard@gmail.com)
 */
public class FieldServiceGcmListenerService extends GcmListenerService {

    private static final String PACKAGE_SOUND_PATH = "android.resource://com.visium.fieldservice/";

    @Override
    public void onMessageReceived(String from, Bundle data) {

        String message = data.getString("message");
        String imgUrl = data.getString("img_url");
        String imgTitle = data.getString("img_title");
        String id = data.getString("id");
        int mNotificationId = StringUtils.isNumeric(id) ? Integer.valueOf(id) : 0;

        LogUtils.info(this, message);

        if (mNotificationId > 0 && StringUtils.isNotBlank(message)) {

            try {
                message = URLDecoder.decode(message, "UTF-8");
                if (imgTitle != null) {
                    imgTitle = URLDecoder.decode(imgTitle, "UTF-8");
                }
            } catch (UnsupportedEncodingException e) {
                LogUtils.error(this, e);
            }

            Intent resultIntent = new Intent(this, SplashScreenActivity.class);

            TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
            // Adds the back stack for the Intent (but not the Intent itself)
            stackBuilder.addParentStack(SplashScreenActivity.class);
            // Adds the Intent that starts the Activity to the top of the stack
            stackBuilder.addNextIntent(resultIntent);
            PendingIntent resultPendingIntent =
                    stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(this)
                            .setSmallIcon(R.drawable.logo)
                            .setContentTitle(getString(R.string.app_name))
                            .setContentText(message)
                            .setVibrate(new long[]{300, 300})
                            .setLights(Color.YELLOW, 3000, 3000)
                            .setAutoCancel(true)
                            .setContentIntent(resultPendingIntent);

            if (StringUtils.isNotBlank(imgUrl)) {

                try {
                    Bitmap bitmap = Picasso.with(this).load(imgUrl).get();

                    if (bitmap != null) {

                        String summaryText = id.startsWith("1") ? getString(R.string.app_name) : null;
                        mBuilder.setLargeIcon(bitmap)
                                .setStyle(new NotificationCompat.BigPictureStyle()
                                        .bigPicture(bitmap)
                                        .setBigContentTitle(imgTitle)
                                        .setSummaryText(summaryText));
                    }

                } catch (IOException e) {
                    LogUtils.error(this, e);
                }

            }

            NotificationManager mNotifyMgr =
                    (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            mNotifyMgr.notify(mNotificationId, mBuilder.build());

        }

    }
}
