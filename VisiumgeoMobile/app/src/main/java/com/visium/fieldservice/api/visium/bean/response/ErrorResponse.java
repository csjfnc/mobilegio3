package com.visium.fieldservice.api.visium.bean.response;

import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.visium.fieldservice.FieldService;
import com.visium.fieldservice.R;
import org.apache.commons.lang3.StringUtils;
import retrofit.RetrofitError;

import java.lang.reflect.Type;

/**
 * @author Andrew Willard (andrewillard@gmail.com)
 */
public class ErrorResponse extends RuntimeException {

    private GenericResponse genericResponse;
    private boolean customized;

    private ErrorResponse(){}

    private ErrorResponse(RetrofitError e, GenericResponse genericResponse) {
        super(genericResponse != null && StringUtils.isNotBlank(genericResponse.getMessage()) ?
                genericResponse.getMessage() : e.getMessage(), e);

        customized = genericResponse != null && StringUtils.isNotBlank(genericResponse.getMessage());
    }

    private ErrorResponse(Exception e, GenericResponse genericResponse) {
        this(RetrofitError.unexpectedError(null, e), genericResponse);
    }

    public boolean isCustomized() {
        return customized;
    }

    public static <T extends GenericResponse> ErrorResponse createError(GenericResponse e) {
        return new ErrorResponse(new Exception(), e);
    }

    public static <T extends GenericResponse> ErrorResponse createError(RetrofitError e) {

        if (e != null && e.getResponse() != null && e.getResponse().getStatus() == 500) {
            try {
                Type type = new TypeToken<InternalServerError>(){}.getType();
                InternalServerError error = (InternalServerError) e.getBodyAs(type);
                return new ErrorResponse(e, new GenericResponse(error.getMessage()));
            } catch (Exception ex) {
                return null;
            }
        } else if (e != null && e.getResponse() != null && e.getResponse().getStatus() == 401) {
            GenericResponse resp = new GenericResponse();
            resp.setMessage(FieldService.get().getString(R.string.unauthorized));
            return new ErrorResponse(e, resp);
        } else {
            try {
                Type type = new TypeToken<T>(){}.getType();
                GenericResponse generic = (T) e.getBodyAs(type);
                return new ErrorResponse(e, generic);
            } catch (Exception ex) {
                return new ErrorResponse();
            }
        }
    }

    private class InternalServerError {

        @SerializedName("Message")
        private String message;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}