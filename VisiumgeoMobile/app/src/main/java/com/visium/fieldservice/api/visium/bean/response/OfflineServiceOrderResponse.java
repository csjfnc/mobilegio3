package com.visium.fieldservice.api.visium.bean.response;

import com.google.gson.annotations.SerializedName;
import com.visium.fieldservice.util.LogUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author Andrew Willard (andrewillard@gmail.com)
 */
public class OfflineServiceOrderResponse implements Serializable {

    private static final long serialVersionUID = -2639082179861442012L;

    private static final String DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(DATE_PATTERN);

    @SerializedName("IdOrdemDeServico")
    private long id;

    @SerializedName("NumeroOS")
    private String number;

    private Date startDateTime;

    @SerializedName("DataInicio")
    private String startDateTimeAsString;

    private Date finishDateTime;

    @SerializedName("DataFinal")
    private String finishDateTimeAsString;

    @SerializedName("Usuario")
    private String userName;

    @SerializedName("NumeroDePostes")
    private int totalPosts;

    @SerializedName("PoligonosOS")
    private List<ServiceOrderPolygonResponse> polygons;

    @SerializedName("Status")
    private int status;

    @SerializedName("Update")
    final private boolean update = true;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<ServiceOrderPolygonResponse> getPolygons() {
        return polygons;
    }

    public void setPolygons(List<ServiceOrderPolygonResponse> polygons) {
        this.polygons = polygons;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getStartDateTime() {
        try {
            startDateTime = startDateTime == null && StringUtils.isNotBlank(startDateTimeAsString) ?
                    DATE_FORMAT.parse(startDateTimeAsString) : startDateTime;
        } catch (ParseException e) {
            LogUtils.error(this, e);
        }

        return startDateTime;
    }

    public void setStartDateTime(Date startDateTime) {
        this.startDateTime = startDateTime;
    }

    public Date getFinishDateTime() {
        try {
            finishDateTime = finishDateTime == null && StringUtils.isNotBlank(finishDateTimeAsString) ?
                    DATE_FORMAT.parse(finishDateTimeAsString) : finishDateTime;
        } catch (ParseException e) {
            LogUtils.error(this, e);
        }

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

    public String getFinishDateTimeAsString() {
        return finishDateTimeAsString;
    }

    public void setFinishDateTimeAsString(String finishDateTimeAsString) {
        this.finishDateTimeAsString = finishDateTimeAsString;
    }

    public String getStartDateTimeAsString() {
        return startDateTimeAsString;
    }

    public void setStartDateTimeAsString(String startDateTimeAsString) {
        this.startDateTimeAsString = startDateTimeAsString;
    }
}