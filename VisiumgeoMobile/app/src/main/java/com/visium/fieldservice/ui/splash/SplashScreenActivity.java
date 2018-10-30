package com.visium.fieldservice.ui.splash;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.visium.fieldservice.R;
import com.visium.fieldservice.controller.UserController;
import com.visium.fieldservice.entity.AuthToken;
import com.visium.fieldservice.preference.PreferenceHelper;
import com.visium.fieldservice.ui.common.CommonAppCompatActivity;
import com.visium.fieldservice.ui.login.LoginActivity;
import com.visium.fieldservice.ui.maps.MapsServiceOrderActivity;
import com.visium.fieldservice.ui.util.PermissionUtils;

public class SplashScreenActivity extends CommonAppCompatActivity {

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final int PERMISSION_ALL = 0;

    private BroadcastReceiver mRegistrationBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        forceStatusBarColor();

        if (PreferenceHelper.getGcmTokenStatus()) {
            checkPermissions();
        } else {

            mRegistrationBroadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    if (PreferenceHelper.getGcmTokenStatus()) {
                        checkPermissions();
                    } else {
                        new AlertDialog.Builder(SplashScreenActivity.this)
                                .setTitle(R.string.internet_problem_question)
                                .setMessage(R.string.internet_problem)
                                .setCancelable(false)
                                .setNegativeButton(R.string.try_again, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        finish();
                                        startActivity(new Intent(SplashScreenActivity.this, SplashScreenActivity.class));
                                    }
                                }).create().show();
                    }
                }
            };

            if (checkPlayServices()) {
                // TODO uncomment this lines when when Visium Geo create a SENDER_ID
//                Intent intent = new Intent(this, RegistrationIntentService.class);
//                startService(intent);
                // TODO remove this line when when Visium Geo create a SENDER_ID
                checkPermissions();
            }
        }
    }

    private void checkPermissions() {
        if (!PermissionUtils.hasPermissions(PermissionUtils.PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PermissionUtils.PERMISSIONS,
                    PERMISSION_ALL);
        } else {
            initialize();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PERMISSION_ALL && permissions.length == grantResults.length
                && PermissionUtils.isGranted(grantResults)) {
            initialize();
        } else{
            Toast.makeText(SplashScreenActivity.this,
                    R.string.splash_permissions, Toast.LENGTH_LONG).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    checkPermissions();
                }
            }, 3000);
        }
    }

    private void initialize() {

        Intent intent;
        AuthToken authToken = UserController.get().getUserProfile() != null ?
                UserController.get().getUserProfile().getAuthToken() : null;

        if (authToken == null || !authToken.exists() || authToken.isExpired()) {
            intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
            if (authToken != null && authToken.exists()) {
                intent.setAction(LoginActivity.VALIDATE_LOGIN_ACTION);
            }
        } else {
            intent = new Intent(SplashScreenActivity.this, MapsServiceOrderActivity.class);
        }

        startActivity(intent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // TODO enable it when Visium Geo create a SENDER_ID
//        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
//                new IntentFilter(RegistrationIntentService.REGISTRATION_COMPLETE));
    }


    @Override
    protected void onPause() {
        // TODO enable it when Visium Geo create a SENDER_ID
//        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Toast.makeText(this, R.string.splash_device_not_supported, Toast.LENGTH_LONG).show();
                finish();
            }
            return false;
        }
        return true;
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}