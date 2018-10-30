package com.visium.fieldservice.entity;

import com.visium.fieldservice.api.visium.bean.response.ServiceOrderPolygonResponse;

import java.io.Serializable;

/**
 * @author Andrew Willard (andrewillard@gmail.com)
 */
public class ServiceOrderPolygon implements Serializable {

    private Double lat;
    private Double lon;

    public ServiceOrderPolygon() {}

    public ServiceOrderPolygon(ServiceOrderPolygonResponse resp) {
        this.lat = resp.getLat();
        this.lon = resp.getLon();
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ServiceOrderPolygon polygon = (ServiceOrderPolygon) o;

        if (lat != null ? !lat.equals(polygon.lat) : polygon.lat != null) return false;
        return !(lon != null ? !lon.equals(polygon.lon) : polygon.lon != null);

    }

    @Override
    public int hashCode() {
        int result = lat != null ? lat.hashCode() : 0;
        result = 31 * result + (lon != null ? lon.hashCode() : 0);
        return result;
    }
}
