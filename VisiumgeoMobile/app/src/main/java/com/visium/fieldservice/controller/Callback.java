package com.visium.fieldservice.controller;

import java.io.Serializable;

/**
 * @author Andrew Willard (andrewillard@gmail.com)
 */
public abstract class Callback<T> implements Serializable {

    public abstract void callback(T t, Throwable e);
}
