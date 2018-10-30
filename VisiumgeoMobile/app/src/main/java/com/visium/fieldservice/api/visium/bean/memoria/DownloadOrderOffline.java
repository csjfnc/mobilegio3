package com.visium.fieldservice.api.visium.bean.memoria;

import com.visium.fieldservice.api.visium.bean.response.OfflineDownloadResponse;

/**
 * Created by fjesus on 11/04/2018.
 */

public class DownloadOrderOffline {

    private static OfflineDownloadResponse response;

    public static OfflineDownloadResponse getResponse() {
        return response;
    }

    public static void setResponse(OfflineDownloadResponse response) {
        DownloadOrderOffline.response = response;
    }
}
