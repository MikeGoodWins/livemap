package com.livemap.live.models;

import com.livemap.live.domain.entity.LiveMap;
import com.livemap.live.utils.RulesUtils;
import lombok.Getter;
import lombok.Setter;

import java.util.EnumMap;

@Getter
@Setter
public class DataMapElement {

    private PointType type;

    private Integer value;


//    public static DataMapElement create(PointType pointType, UserInfo userInfo, LiveMap liveMap) {
//        DataMapElement element = new DataMapElement();
//        element.setType(pointType);
//        element.setValue(RulesUtils.rule22(calculate(pointType, userInfo, liveMap.getDataMap())));
//        return element;
//    }
//
//    private static Integer calculate(PointType pointType, UserInfo userInfo) {
//
//        return calculate(pointType, userInfo, new EnumMap<>(PointType.class));
//    }

//    private static Integer calculate(PointType pointType, UserInfo userInfo, EnumMap<PointType, DataMapElement> dataMap) {
//
//        return switch (pointType) {
//            case A, Y_0 -> userInfo.getUserData().get(0) + userInfo.getUserData().get(1);
//            case B, Y_20 -> userInfo.getUserData().get(2) + userInfo.getUserData().get(3);
//            case V, Y_40 ->
//                    userInfo.getUserData().get(4) + userInfo.getUserData().get(5) + userInfo.getUserData().get(6) + userInfo.getUserData().get(7);
//            case G, Y_60 ->
//                    dataMap.get(PointType.A).value + dataMap.get(PointType.B).value + dataMap.get(PointType.V).value;
//            case D, INSIDE_POWER_CODE_1 ->
//                    dataMap.get(PointType.A).value + dataMap.get(PointType.B).value + dataMap.get(PointType.V).value + dataMap.get(PointType.G).value;
//            case K -> dataMap.get(PointType.D).value + dataMap.get(PointType.G).value;
//            case L -> dataMap.get(PointType.D).value + dataMap.get(PointType.V).value;
//            case M -> dataMap.get(PointType.K).value + dataMap.get(PointType.L).value;
//            case N -> dataMap.get(PointType.K).value + dataMap.get(PointType.M).value;
//            case O -> dataMap.get(PointType.M).value + dataMap.get(PointType.L).value;
//            case E, Y_10 -> dataMap.get(PointType.A).value + dataMap.get(PointType.B).value;
//            case I, Y_50 -> dataMap.get(PointType.V).value + dataMap.get(PointType.G).value;
//            case J, Y_30 -> dataMap.get(PointType.B).value + dataMap.get(PointType.V).value;
//            case Z, Y_70 -> dataMap.get(PointType.A).value + dataMap.get(PointType.G).value;
//            case E1 -> dataMap.get(PointType.E).value + dataMap.get(PointType.D).value;
//            case E2 -> dataMap.get(PointType.E1).value + dataMap.get(PointType.E).value;
//            case B1 -> dataMap.get(PointType.B).value + dataMap.get(PointType.D).value;
//            case B2 -> dataMap.get(PointType.B1).value + dataMap.get(PointType.B).value;
//            case B3 -> dataMap.get(PointType.B1).value + dataMap.get(PointType.D).value;
//            case J1 -> dataMap.get(PointType.J).value + dataMap.get(PointType.D).value;
//            case J2 -> dataMap.get(PointType.J1).value + dataMap.get(PointType.J).value;
//            case V1 -> dataMap.get(PointType.L).value + dataMap.get(PointType.V).value;
//            case I1 -> dataMap.get(PointType.I).value + dataMap.get(PointType.D).value;
//            case I2 -> dataMap.get(PointType.I1).value + dataMap.get(PointType.I).value;
//            case G1 -> dataMap.get(PointType.K).value + dataMap.get(PointType.G).value;
//            case Z1 -> dataMap.get(PointType.Z).value + dataMap.get(PointType.D).value;
//            case Z2 -> dataMap.get(PointType.Z).value + dataMap.get(PointType.Z1).value;
//            case A1 -> dataMap.get(PointType.A).value + dataMap.get(PointType.D).value;
//            case A2 -> dataMap.get(PointType.A).value + dataMap.get(PointType.A1).value;
//            case A3 -> dataMap.get(PointType.A1).value + dataMap.get(PointType.D).value;
//
//            case Y_5 -> dataMap.get(PointType.Y_0).getValue() + dataMap.get(PointType.Y_10).getValue();
//            case Y_15 -> dataMap.get(PointType.Y_10).getValue() + dataMap.get(PointType.Y_20).getValue();
//            case Y_25 -> dataMap.get(PointType.Y_20).getValue() + dataMap.get(PointType.Y_30).getValue();
//            case Y_35 -> dataMap.get(PointType.Y_30).getValue() + dataMap.get(PointType.Y_40).getValue();
//            case Y_45 -> dataMap.get(PointType.Y_40).getValue() + dataMap.get(PointType.Y_50).getValue();
//            case Y_55 -> dataMap.get(PointType.Y_50).getValue() + dataMap.get(PointType.Y_60).getValue();
//            case Y_65 -> dataMap.get(PointType.Y_60).getValue() + dataMap.get(PointType.Y_70).getValue();
//            case Y_75 -> dataMap.get(PointType.Y_70).getValue() + dataMap.get(PointType.Y_0).getValue();
//            case Y_2_3 -> dataMap.get(PointType.Y_0).value + dataMap.get(PointType.Y_5).value;
//            case Y_1_2 -> dataMap.get(PointType.Y_0).value + dataMap.get(PointType.Y_2_3).value;
//            case Y_3_4 -> dataMap.get(PointType.Y_5).value + dataMap.get(PointType.Y_2_3).value;
//            case Y_7_8 -> dataMap.get(PointType.Y_5).value + dataMap.get(PointType.Y_10).value;
//            case Y_6_7 -> dataMap.get(PointType.Y_5).value + dataMap.get(PointType.Y_7_8).value;
//            case Y_8_9 -> dataMap.get(PointType.Y_10).value + dataMap.get(PointType.Y_7_8).value;
//            case PURPOSE_2040_SKY_AND_EARTH ->
//                    (RulesUtils.rule22(dataMap.get(PointType.B).value + dataMap.get(PointType.G).value) +
//                            RulesUtils.rule22(dataMap.get(PointType.A).value + dataMap.get(PointType.V).value));
//            case PURPOSE_4060_SOCIALIZATION ->
//                    (RulesUtils.rule22(dataMap.get(PointType.E).value + dataMap.get(PointType.I).value) +
//                            RulesUtils.rule22(dataMap.get(PointType.G).value + dataMap.get(PointType.Z).value));
//            case PURPOSE_AFTER60_SPIRITUAL ->
//                    (RulesUtils.rule22(dataMap.get(PointType.PURPOSE_2040_SKY_AND_EARTH).value) +
//                            RulesUtils.rule22(dataMap.get(PointType.PURPOSE_4060_SOCIALIZATION).value));
//            case PURPOSE_PLANETARY -> (RulesUtils.rule22(dataMap.get(PointType.PURPOSE_4060_SOCIALIZATION).value) +
//                    RulesUtils.rule22(dataMap.get(PointType.PURPOSE_AFTER60_SPIRITUAL).value));
//            case FAMILY_STRENGTH, INSIDE_POWER_CODE_2 -> (RulesUtils.rule22(dataMap.get(PointType.E).value + dataMap.get(PointType.G).value +
//                    dataMap.get(PointType.Z).value) + dataMap.get(PointType.I).value);
//            case INSIDE_POWER_CODE_3 -> (RulesUtils.rule22(dataMap.get(PointType.INSIDE_POWER_CODE_1).value + dataMap.get(PointType.INSIDE_POWER_CODE_2).value));
//        };
//    }
}
