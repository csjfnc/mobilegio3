package com.visium.fieldservice.entity;

import com.visium.fieldservice.entity.enums.EnumsConfiguration;

import java.util.HashMap;

/**
 * @author Andrew Willard (andrewillard@gmail.com)
 */
public class UserProfile {

    private String name;
    private AuthToken authToken;
    private HashMap<String, EnumsConfiguration> globalEquipmentMap;


    public UserProfile() {}

    public UserProfile(String name, AuthToken authToken, HashMap<String, EnumsConfiguration> equipmentMap) {
        this.name = name;
        this.authToken = authToken;
        this.globalEquipmentMap = equipmentMap;

    }

    public AuthToken getAuthToken() {
        return authToken;
    }

    public void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<String, EnumsConfiguration> getEquipmentsConfiguration() {
        return globalEquipmentMap;
    }

    public void setEquipmentsConfiguration(HashMap<String, EnumsConfiguration> globalEquipmentMap) {
        globalEquipmentMap = globalEquipmentMap;
    }
}
