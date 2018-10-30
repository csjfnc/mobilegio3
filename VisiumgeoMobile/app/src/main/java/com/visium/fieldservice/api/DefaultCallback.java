package com.visium.fieldservice.api;

import com.visium.fieldservice.api.visium.bean.response.ErrorResponse;
import com.visium.fieldservice.api.visium.bean.response.GenericResponse;
import com.visium.fieldservice.api.visium.bean.response.IResponse;
import com.visium.fieldservice.controller.VisiumApiCallback;
import com.visium.fieldservice.util.LogUtils;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * @author Andrew Willard
 */
public class DefaultCallback<T> implements retrofit.Callback {

    private VisiumApiCallback<T> callback;

    public DefaultCallback(VisiumApiCallback<T> callback) {
        this.callback = callback;
    }

    @Override
    public void success(Object o, Response response) {
        ErrorResponse error = null;
        T t = (T) o;
        GenericResponse gResp = null;
        IResponse iResp;
        if (t instanceof GenericResponse
                && (gResp = (GenericResponse) t) != null
                && gResp.getData() instanceof IResponse
                && (iResp = (IResponse) gResp.getData()) != null) {
            iResp.setResponse(response);
        }
        if (gResp != null && gResp.getStatus() <= 0) {
            error = ErrorResponse.createError(gResp);
        }
        callback.callback(t, error);
    }

    @Override
    public void failure(RetrofitError retrofitError) {
        LogUtils.apiError(this, retrofitError);
        callback.callback(null, ErrorResponse.createError(retrofitError));
    }
}