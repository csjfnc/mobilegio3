package com.visium.fieldservice.api.visium.bean.response;

import com.google.gson.annotations.SerializedName;
import retrofit.client.Response;

import java.io.Serializable;

/**
 * Created by andrew on 12/26/15.
 */
public class GenericResponse<T> implements Serializable {

    private static final long serialVersionUID = 172892008789806188L;

    protected Response response;

    @SerializedName("Status")
    private int status;

    @SerializedName("Message")
    private String message;

    @SerializedName("Results")
    private T data;

    public GenericResponse(){}

    public GenericResponse(String message){
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Response getResponse() {
        return this.response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

}