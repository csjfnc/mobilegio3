package com.visium.fieldservice.api.visium.bean.response;

import retrofit.client.Response;

import java.io.Serializable;

/**
 * @author Andrew Willard (andrewillard@gmail.com)
 */
public interface IResponse extends Serializable {

    public void setResponse(Response response);

}
