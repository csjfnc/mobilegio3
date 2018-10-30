package com.visium.fieldservice.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.visium.fieldservice.R;
import com.visium.fieldservice.preference.PreferenceHelper;
import com.visium.fieldservice.util.LogUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

/**
 * @author Andrew Willard (andrewillard@gmail.com)
 */
public class RegistrationIntentService extends IntentService {

    public static final String REGISTRATION_COMPLETE = "REGISTRATION_COMPLETE";

    private static final String TAG = "RegIntentService";

    public RegistrationIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        InstanceID instanceID = InstanceID.getInstance(this);
        try {
            String token = instanceID.getToken(getString(R.string.gcm_sender_id),
                    GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);

            sendRegistrationToServer(token);
            PreferenceHelper.setGcmTokenStatus(StringUtils.isNoneBlank(token));
            PreferenceHelper.setGcmToken(token);

            LogUtils.debug(this, "GCM - TOKEN - " + token);

        } catch (IOException e) {
            LogUtils.error(this, e);
            PreferenceHelper.setGcmTokenStatus(false);
            PreferenceHelper.setGcmToken(null);
        }

        Intent registrationComplete = new Intent(REGISTRATION_COMPLETE);
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
    }

    private void sendRegistrationToServer(String token) {
        // Add custom implementation, as needed.
    }
}
