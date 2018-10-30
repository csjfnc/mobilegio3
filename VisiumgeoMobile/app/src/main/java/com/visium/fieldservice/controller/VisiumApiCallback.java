package com.visium.fieldservice.controller;

import com.visium.fieldservice.api.visium.bean.response.ErrorResponse;

import java.io.Serializable;

/**
 * @author Andrew Willard (andrewillard@gmail.com)
 */
public abstract class VisiumApiCallback<T> implements Serializable {

    public abstract void callback(T t, ErrorResponse e);
}
