package com.visium.fieldservice.ui.login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.visium.fieldservice.BuildConfig;
import com.visium.fieldservice.R;
import com.visium.fieldservice.api.visium.bean.response.ErrorResponse;
import com.visium.fieldservice.controller.UserController;
import com.visium.fieldservice.controller.VisiumApiCallback;
import com.visium.fieldservice.entity.UserProfile;
import com.visium.fieldservice.ui.common.CommonAppCompatActivity;
import com.visium.fieldservice.ui.maps.MapsServiceOrderActivity;
import com.visium.fieldservice.ui.util.ViewUtils;
import com.visium.fieldservice.util.LogUtils;

import org.apache.commons.lang3.StringUtils;

import java.util.logging.Logger;

public class LoginActivity extends CommonAppCompatActivity {

    public static final String VALIDATE_LOGIN_ACTION = "VALIDATE_LOGIN_ACTION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        forceStatusBarColor();

        final Button loginButton = (Button) findViewById(R.id.login_button);
        final EditText editUserName = (EditText) findViewById(R.id.login_username);
        final EditText editPassword = (EditText) findViewById(R.id.login_password);

        String portServer = getResources().getString(R.string.fieldservice_server_api).split(":")[2].split("/")[0];
        final TextView txtServer = (TextView) findViewById(R.id.txtServer);
        txtServer.setText("Version: " + BuildConfig.VERSION_CODE + "/" + portServer);

        if (loginButton != null) {
            loginButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    final String userName = ViewUtils.getTextViewValue(editUserName);
                    String password = ViewUtils.getTextViewValue(editPassword);

                    if (StringUtils.isBlank(userName) || StringUtils.isBlank(password)) {

                        Toast.makeText(LoginActivity.this, R.string.login_invalid_fields, Toast.LENGTH_LONG).show();

                    } else {
                        final ProgressDialog alertDialog = new ProgressDialog(LoginActivity.this, R.style.AlertDialogTheme);
                        alertDialog.setTitle(getString(R.string.login_dialog_title));
                        alertDialog.setMessage(getString(R.string.login_dialog_message));
                        alertDialog.setCancelable(false);
                        alertDialog.show();

                        UserController.get().auth(userName, password, new VisiumApiCallback<UserProfile>() {
                            private static final long serialVersionUID = -3060359646472825030L;

                            @Override
                            public void callback(UserProfile userProfile, ErrorResponse e) {
                                alertDialog.dismiss();
                                //Log.d("DEV", "userProfile name: "+userProfile.getName()+" token: "+userProfile.getAuthToken().exists());

                                if (userProfile != null && userProfile.getAuthToken().exists()) {
                                    startActivity(new Intent(LoginActivity.this, MapsServiceOrderActivity.class));

                                    SharedPreferences.Editor preferences = getSharedPreferences("LocalUsuario", Context.MODE_PRIVATE).edit();
                                    preferences.putString("userLocal",userProfile.getName());
                                    preferences.putInt("panico", 0);
                                    preferences.commit();

                                    finish();
                                } else if (e != null && e.isCustomized()) {
                                    Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(LoginActivity.this, R.string.login_error, Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                }
            });
        }

        Intent intent = getIntent();

        if (intent != null && StringUtils.equals(intent.getAction(), VALIDATE_LOGIN_ACTION)) {
            Toast.makeText(LoginActivity.this, R.string.unauthorized, Toast.LENGTH_LONG).show();
        }

    }

}