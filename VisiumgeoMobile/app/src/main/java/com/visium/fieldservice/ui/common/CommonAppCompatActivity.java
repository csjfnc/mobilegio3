package com.visium.fieldservice.ui.common;

import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import com.visium.fieldservice.R;

/**
 * @author Andrew Willard (andrewillard@gmail.com)
 */
public class CommonAppCompatActivity extends AppCompatActivity {

    protected void forceStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.color_primary_alpha));
        }
    }

    protected void setupToolbar(boolean homeAsUpIndicator) {
        setupToolbar(homeAsUpIndicator, null);
    }

    protected void setupToolbar(boolean homeAsUpIndicator, Integer title) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            if (homeAsUpIndicator) {
                // TODO if in the future Visium needs a drawer menu
                // actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
            }

            if (title != null) {
                TextView textView = (TextView) toolbar.findViewById(R.id.appbar_title);
                textView.setText(title);
                textView.setVisibility(View.VISIBLE);
                toolbar.findViewById(R.id.appbar_logo).setVisibility(View.GONE);
            }

            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    protected void setupToolbar(int homeAsUpIndicatorId, String title) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            if (homeAsUpIndicatorId != 0) {
                actionBar.setHomeAsUpIndicator(homeAsUpIndicatorId);
            }

            if (title != null) {
                TextView textView = (TextView) toolbar.findViewById(R.id.appbar_title);
                textView.setText(title);
                textView.setVisibility(View.VISIBLE);
                toolbar.findViewById(R.id.appbar_logo).setVisibility(View.GONE);
            }

            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

}
