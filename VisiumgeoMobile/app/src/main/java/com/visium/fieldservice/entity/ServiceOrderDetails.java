package com.visium.fieldservice.entity;

import com.visium.fieldservice.api.visium.bean.response.ServiceOrderDetailsResponse;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Andrew Willard (andrewillard@gmail.com)
 */
public class ServiceOrderDetails implements Serializable {

    private static final long serialVersionUID = 3848349348215006506L;
    private static final String DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(DATE_PATTERN);

    private long id;
    private String number;
    private Date startDateTime;
    private Date finishDateTime;
    private String userName;
    private int totalPosts;
    private boolean update;
    private String finishDateTimeAsString;

    public ServiceOrderDetails() {}

    public ServiceOrderDetails(ServiceOrderDetailsResponse details) {
        this.id = details.getId();
        this.number = details.getNumber();
        this.startDateTime = details.getStartDateTime();
        setFinishDateTime(details.getFinishDateTime());
        this.userName = details.getUserName();
        this.totalPosts = details.getTotalPosts();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Date getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(Date startDateTime) {
        this.startDateTime = startDateTime;
    }

    public Date getFinishDateTime() {
        return finishDateTime;
    }

    public void setFinishDateTime(Date finishDateTime) {
        this.finishDateTime = finishDateTime;
        finishDateTimeAsString = finishDateTime==null ? "" : DATE_FORMAT.format(finishDateTime);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getTotalPosts() {
        return totalPosts;
    }

    public void setTotalPosts(int totalPosts) {
        this.totalPosts = totalPosts;
    }

    public boolean isUpdate() {
        return update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }
}