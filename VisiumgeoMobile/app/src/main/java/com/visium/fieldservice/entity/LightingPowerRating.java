package com.visium.fieldservice.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrew Willard (andrewillard@gmail.com)
 */
public enum LightingPowerRating {

    _0(0),
    _40(40),
    _58(58),
    _60(60),
    _70(70),
    _80(80),
    _100(100),
    _125(125),
    _127(127),
    _150(150),
    _250(250),
    _400(400);

    private double rate;

    LightingPowerRating(double rate) {
        this.rate = rate;
    }

    public double getRating() {
        return rate;
    }

    public static LightingPowerRating parse(double rate) {
        for (LightingPowerRating type : LightingPowerRating.values()) {
            if (type.getRating() == rate) {
                return type;
            }
        }
        return _0;
    }

    public static List<Double> getRates() {
        List<Double> names = new ArrayList<>();
        for (LightingPowerRating rate : LightingPowerRating.values()) {
            names.add(rate.getRating());
        }
        return names;
    }
}
