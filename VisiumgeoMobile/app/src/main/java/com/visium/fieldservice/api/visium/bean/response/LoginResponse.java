package com.visium.fieldservice.api.visium.bean.response;

import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

import retrofit.client.Header;
import retrofit.client.Response;

/**
 * Created by andrew on 12/26/15.
 */
public class LoginResponse implements IResponse {

    private static final long serialVersionUID = -3649720967427102695L;

    @SerializedName("UserName")
    private String name;

    @SerializedName("Token")
    private String token;

    @SerializedName("TokenExpiry")
    private int tokenExpiry;

    @SerializedName("EnumsConfiguration")
    private List<EquipmentConfigurationResponse> listEquipmentConfiguration;

    public List<EquipmentConfigurationResponse> getListEquipmentConfiguration() {
        return listEquipmentConfiguration;
    }

    public void setListEquipmentConfiguration(List<EquipmentConfigurationResponse> listEquipmentConfiguration) {
        this.listEquipmentConfiguration = listEquipmentConfiguration;
    }

    public LoginResponse() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public int getTokenExpiry() {
        return tokenExpiry;
    }

    @Override
    public void setResponse(Response response) {
        if (response != null && StringUtils.isBlank(token)) {
            for (Header header : response.getHeaders()) {
                if (StringUtils.equalsIgnoreCase(header.getName(), "Token")) {
                    this.token = header.getValue();
                }
                else if (StringUtils.equalsIgnoreCase(header.getName(), "TokenExpiry")) {
                    this.tokenExpiry = Integer.parseInt(header.getValue());
                }
            }
        }
    }
}
