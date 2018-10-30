package com.visium.fieldservice.receiver;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import com.visium.fieldservice.FieldService;
import com.visium.fieldservice.preference.PreferenceHelper;
import com.visium.fieldservice.ui.maps.MapsServiceOrderActivity;
import com.visium.fieldservice.util.LogUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Set;

/**
 * @author Andrew Willard (andrewillard@gmail.com)
 */
public class BroadcastReceiverDownloadManager extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent)
    {
        String action = intent.getAction();
        LogUtils.info(this, "Download Manager Received Message");

        if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {

            String id = get(intent.getExtras());

            if (StringUtils.isNotBlank(id)) {
                Set<String> ids = PreferenceHelper.getOfflineIds();
                ids.add(id);
                PreferenceHelper.setOfflineIds(ids);

                Intent i = new Intent(context, MapsServiceOrderActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        }
    }

    private String get(Bundle extras) {
        String title = StringUtils.EMPTY;
        DownloadManager.Query q = new DownloadManager.Query();
        q.setFilterById(extras.getLong(DownloadManager.EXTRA_DOWNLOAD_ID));
        DownloadManager manager = (DownloadManager) FieldService.get().getSystemService(Context.DOWNLOAD_SERVICE);
        Cursor c = manager.query(q);

        if (c.moveToFirst()) {
            int status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
            if (status == DownloadManager.STATUS_SUCCESSFUL) {
                title = c.getString(c.getColumnIndex(DownloadManager.COLUMN_DESCRIPTION));
            }
        }

        return title;
    }
}