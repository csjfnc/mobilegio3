package com.visium.fieldservice.ui.util;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Andrew Willard (andrewillard@gmail.com)
 */
public class ViewUtils {

    public static String getTextViewValue(TextView view) {
        return view != null && view.getText() != null ?
                view.getText().toString() : StringUtils.EMPTY;
    }

    public static int getIndexArrayFromValue(Context context, int array, String value) {
        String[] values = context.getResources().getStringArray(array);
        if (ArrayUtils.contains(values, value)) {
            return ArrayUtils.indexOf(values, value);
        } else {
            return 0;
        }
    }

    public static void setViewMargins(Context con, ViewGroup.LayoutParams params,
                               int left, int top , int right, int bottom, View view) {

        final float scale = con.getResources().getDisplayMetrics().density;
        // convert the DP into pixel
        int pixel_left = (int) (left * scale + 0.5f);
        int pixel_top = (int) (top * scale + 0.5f);
        int pixel_right = (int) (right * scale + 0.5f);
        int pixel_bottom = (int) (bottom * scale + 0.5f);

        ViewGroup.MarginLayoutParams s = (ViewGroup.MarginLayoutParams) params;
        s.setMargins(pixel_left, pixel_top, pixel_right, pixel_bottom);

        view.setLayoutParams(params);
    }

}
