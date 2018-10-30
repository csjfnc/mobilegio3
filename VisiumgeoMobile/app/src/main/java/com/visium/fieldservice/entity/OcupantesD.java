package com.visium.fieldservice.entity;

import java.util.ArrayList;
import java.util.List;


public enum OcupantesD {

    _0(0),
    _1(1),
    _2(2),
    _3(3),
    _4(4),
    _5(5),
    _6(6),
    _7(7),
    _8(8),
    _9(9),
    _10(10),
    _11(11),
    _12(12),
    _13(13),
    _14(14),
    _15(15),
    _16(16),
    _17(17),
    _18(18),
    _19(19),
    _20(20);

    private int ocupantesd;

    OcupantesD(int ocupantesd) {
        this.ocupantesd = ocupantesd;
    }

    public int getOcupantesD() {
        return ocupantesd;
    }

    public static OcupantesD parse(int ocupantesd) {
        for (OcupantesD type : OcupantesD.values()) {
            if (type.getOcupantesD() == ocupantesd) {
                return type;
            }
        }
        return _0;
    }

    public static List<Integer> getOcupantesd() {
        List<Integer> names = new ArrayList<>();
        for (OcupantesD rate : OcupantesD.values()) {
            names.add(rate.getOcupantesD());
        }
        return names;
    }

}