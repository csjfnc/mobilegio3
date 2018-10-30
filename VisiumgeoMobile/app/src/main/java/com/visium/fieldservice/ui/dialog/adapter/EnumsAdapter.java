package com.visium.fieldservice.ui.dialog.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.visium.fieldservice.entity.enums.EnumClass;
import com.visium.fieldservice.entity.enums.EnumItem;
import com.visium.fieldservice.util.LogUtils;

import java.util.List;

/**
 * Created by ltonon on 21/11/2016.
 */

public class EnumsAdapter extends ArrayAdapter {
    private Context mCtx;
    private int mLayout;
    private List<EnumItem> mMap;
    private static String[] mSpinnerArray;
    private static int[] mValueArray;

    public EnumsAdapter(Context context, int resource, List<EnumItem> map) {
        super(context, resource, mSpinnerArray = createSpinnerArray(map));
        mCtx = context;
        mLayout = resource;
        mMap = map;

    }
    public EnumsAdapter(Context context, int resource, EnumClass config) {
        super(context, resource, mSpinnerArray = createSpinnerArray(config.getEnums()));
        mCtx = context;
        mLayout = resource;
        mMap = config.getEnums();

    }

    public EnumsAdapter(Context context, int resource, Object[] objects) {
        super(context, resource, objects);
    }

    private static String[] createSpinnerArray(List<EnumItem> map) {
        LogUtils.log("mapsize "+map.size());
        String[] array = new String[map.size()];
        for (int i = 0; i < map.size(); i++)
        {
            array[i] = map.get(i).getValue();
        }
        return array;
    }

    public String getName(int position) {
        return mMap.get(position).getValue();
    }

    public int getKey(int position) {
        return (int) mMap.get(position).getKey();
    }
}
