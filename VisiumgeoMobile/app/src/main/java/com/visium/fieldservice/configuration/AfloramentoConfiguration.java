package com.visium.fieldservice.configuration;

import com.visium.fieldservice.entity.enums.EnumClass;
import com.visium.fieldservice.preference.PreferenceHelper;

import java.util.HashMap;

/**
 * Created by ltonon on 28/11/2016.
 */

public class AfloramentoConfiguration {
    private static HashMap<String, EnumClass> mEnumsMap = null;
    public static final String EQUIPMENT_NAME = Configuration.Names.AFLORAMENTO;

    public interface Names {
        String STATUS = "Status";
        String PROPRIETARIO = "Proprietario";
    }

    public static void configure() {
         mEnumsMap = PreferenceHelper.getUserProfile().getEquipmentsConfiguration().get(EQUIPMENT_NAME).getEnumMap();
    }

    public static EnumClass getEnum(String enumName) {
        if(mEnumsMap == null) {
            configure();
        }
        return mEnumsMap.get(enumName);
    }
}
