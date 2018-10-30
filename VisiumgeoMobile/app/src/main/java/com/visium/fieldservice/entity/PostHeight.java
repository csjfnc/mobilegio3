package com.visium.fieldservice.entity;

import java.util.ArrayList;
import java.util.List;


public enum PostHeight {

    _7(7),
    _8(8),
    _9(9),
    _10(10),
    _10_5(10.5),
    _11(11),
    _12(12),
    _13(13),
    _14(14),
    _15(15),
    _16(16);

    private double height;

    PostHeight(double height) {
        this.height = height;
    }

    public double getHeight() {
        return height;
    }

    public static PostHeight parse(double height) {
        for (PostHeight type : PostHeight.values()) {
            if (type.getHeight() == height) {
                return type;
            }
        }
        return _7;
    }

    public static List<Double> getHeights() {
        List<Double> names = new ArrayList<>();
        for (PostHeight rate : PostHeight.values()) {
            names.add(rate.getHeight());
        }
        return names;
    }

}