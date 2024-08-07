package com.livemap.live.services;

import com.livemap.live.domain.entity.LiveMap;
import com.livemap.live.dto.RequestModel;
import com.livemap.live.dto.ResponseModel;
import com.livemap.live.exceptions.ConflictException;
import com.livemap.live.mappers.ResponseMapper;
import com.livemap.live.models.*;
import com.livemap.live.utils.RulesUtils;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Slf4j
public class MainService {

    private static final LinkedList<PointType> CALC_BASE = new LinkedList<>(List.of(PointType.A, PointType.B, PointType.V, PointType.G, PointType.D, PointType.K, PointType.L, PointType.M,
            PointType.N, PointType.O, PointType.E, PointType.I, PointType.J, PointType.Z));

    private static final LinkedList<PointType> CALC_ADD = new LinkedList<>(List.of(PointType.E1, PointType.E2, PointType.B1, PointType.B2, PointType.B3,
            PointType.J1, PointType.J2, PointType.V1, PointType.I1, PointType.I2, PointType.G1, PointType.Z1, PointType.Z2, PointType.A1, PointType.A2, PointType.A3,
            PointType.PURPOSE_2040_SKY_AND_EARTH, PointType.PURPOSE_4060_SOCIALIZATION, PointType.PURPOSE_AFTER60_SPIRITUAL, PointType.PURPOSE_PLANETARY, PointType.FAMILY_STRENGTH,
            PointType.INSIDE_POWER_CODE_1, PointType.INSIDE_POWER_CODE_2, PointType.INSIDE_POWER_CODE_3));

    private static final LinkedList<PointType> CALC_YEARS_0_10 = new LinkedList<>(List.of(PointType.Y_5, PointType.Y_2_3, PointType.Y_1_2, PointType.Y_3_4, PointType.Y_7_8, PointType.Y_6_7, PointType.Y_8_9));


    private static final Map<Integer, PointType> POINT_MAP = new HashMap<>();

    static {
        Arrays.asList(PointType.values()).stream().forEach(pt -> {
            if(POINT_MAP.containsKey(pt.ordinal())){
                throw new ConflictException("Point map duplicate key" + pt);
            }else{
                POINT_MAP.put(pt.ordinal(), pt);
            }
        });
    }
    private final LiveMapService liveMapService;

    private final ResponseMapper responseMapper;

    public MainService(LiveMapService liveMapService, ResponseMapper responseMapper) {
        this.liveMapService = liveMapService;
        this.responseMapper = responseMapper;
    }

    @Transactional
    public ResponseModel calculate(RequestModel request){

        LiveMap liveMap = liveMapService.create(request);
        UserInfo userInfo = new UserInfo(request.getBirthday());
        EnumMap<PointType, DataMapElement> dataMap = calculatePoints(userInfo, liveMap);
        EnumMap<HealthPointType, HealthElement> healthDataMap = calculateHealthPoints(dataMap);
//        calculateBasePoints(userInfo, liveMap);
//        calculateAdditionPoints(userInfo, liveMap);
//        calculateYearsPoints010(userInfo, liveMap);

        liveMap.setDataMap(dataMap);
        liveMap.setHealthDataMap(healthDataMap);
//        liveMapService.update(liveMap);
        liveMap.liveMapToString();
        return new ResponseModel(liveMap);
    }

    private EnumMap<PointType, DataMapElement> calculatePoints(UserInfo userInfo, LiveMap liveMap){

        EnumMap<PointType, DataMapElement> dataMap = new EnumMap<>(PointType.class);
        Arrays.asList(PointType.values()).forEach(pt -> {
//            log.info("Calculate point {}", pt);

            DataMapElement element = new DataMapElement();
            element.setType(pt);
            element.setValue(RulesUtils.rule22(calculate(pt, userInfo, dataMap)));
            dataMap.put(pt, element);
//            log.info("Point {} value = {}", pt, element.getValue());
        });
        return dataMap;
    }

    private EnumMap<HealthPointType, HealthElement> calculateHealthPoints(EnumMap<PointType, DataMapElement> dataMap){

        EnumMap<HealthPointType, HealthElement> healthDataMap = new EnumMap<>(HealthPointType.class);
        Arrays.asList(HealthPointType.values()).forEach(hpt -> {
//            log.info("Calculate point {}", pt);

            HealthElement healthElement = healthCalculate(hpt, dataMap);
            healthDataMap.put(hpt, healthElement);
//            log.info("Point {} value = {}", pt, element.getValue());
        });
        healthSumCalculate(healthDataMap);
        return healthDataMap;
    }

    private Integer calculate(PointType pointType, UserInfo userInfo, EnumMap<PointType, DataMapElement> dataMap) {

        return switch (pointType) {
            case A, Y_0 -> userInfo.getUserData().get(0) + userInfo.getUserData().get(1);
            case B, Y_20 -> userInfo.getUserData().get(2) + userInfo.getUserData().get(3);
            case V, Y_40 ->
                    userInfo.getUserData().get(4) + userInfo.getUserData().get(5) + userInfo.getUserData().get(6) + userInfo.getUserData().get(7);
            case G, Y_60 ->
                    dataMap.get(PointType.A).getValue() + dataMap.get(PointType.B).getValue() + dataMap.get(PointType.V).getValue();
            case D, INSIDE_POWER_CODE_1 ->
                    dataMap.get(PointType.A).getValue() + dataMap.get(PointType.B).getValue() + dataMap.get(PointType.V).getValue() + dataMap.get(PointType.G).getValue();
            case K -> dataMap.get(PointType.D).getValue() + dataMap.get(PointType.G).getValue();
            case L -> dataMap.get(PointType.D).getValue() + dataMap.get(PointType.V).getValue();
            case M -> dataMap.get(PointType.K).getValue() + dataMap.get(PointType.L).getValue();
            case N -> dataMap.get(PointType.K).getValue() + dataMap.get(PointType.M).getValue();
            case O -> dataMap.get(PointType.M).getValue() + dataMap.get(PointType.L).getValue();
            case E, Y_10 -> dataMap.get(PointType.A).getValue() + dataMap.get(PointType.B).getValue();
            case I, Y_50 -> dataMap.get(PointType.V).getValue() + dataMap.get(PointType.G).getValue();
            case J, Y_30 -> dataMap.get(PointType.B).getValue() + dataMap.get(PointType.V).getValue();
            case Z, Y_70 -> dataMap.get(PointType.A).getValue() + dataMap.get(PointType.G).getValue();
            case E1 -> dataMap.get(PointType.E).getValue() + dataMap.get(PointType.D).getValue();
            case E2 -> dataMap.get(PointType.E1).getValue() + dataMap.get(PointType.E).getValue();
            case B1 -> dataMap.get(PointType.B).getValue() + dataMap.get(PointType.D).getValue();
            case B2 -> dataMap.get(PointType.B1).getValue() + dataMap.get(PointType.B).getValue();
            case B3 -> dataMap.get(PointType.B1).getValue() + dataMap.get(PointType.D).getValue();
            case J1 -> dataMap.get(PointType.J).getValue() + dataMap.get(PointType.D).getValue();
            case J2 -> dataMap.get(PointType.J1).getValue() + dataMap.get(PointType.J).getValue();
            case V1 -> dataMap.get(PointType.L).getValue() + dataMap.get(PointType.V).getValue();
            case I1 -> dataMap.get(PointType.I).getValue() + dataMap.get(PointType.D).getValue();
            case I2 -> dataMap.get(PointType.I1).getValue() + dataMap.get(PointType.I).getValue();
            case G1 -> dataMap.get(PointType.K).getValue() + dataMap.get(PointType.G).getValue();
            case Z1 -> dataMap.get(PointType.Z).getValue() + dataMap.get(PointType.D).getValue();
            case Z2 -> dataMap.get(PointType.Z).getValue() + dataMap.get(PointType.Z1).getValue();
            case A1 -> dataMap.get(PointType.A).getValue() + dataMap.get(PointType.D).getValue();
            case A2 -> dataMap.get(PointType.A).getValue() + dataMap.get(PointType.A1).getValue();
            case A3 -> dataMap.get(PointType.A1).getValue() + dataMap.get(PointType.D).getValue();

            case Y_5 -> dataMap.get(PointType.Y_0).getValue() + dataMap.get(PointType.Y_10).getValue();
            case Y_15 -> dataMap.get(PointType.Y_10).getValue() + dataMap.get(PointType.Y_20).getValue();
            case Y_25 -> dataMap.get(PointType.Y_20).getValue() + dataMap.get(PointType.Y_30).getValue();
            case Y_35 -> dataMap.get(PointType.Y_30).getValue() + dataMap.get(PointType.Y_40).getValue();
            case Y_45 -> dataMap.get(PointType.Y_40).getValue() + dataMap.get(PointType.Y_50).getValue();
            case Y_55 -> dataMap.get(PointType.Y_50).getValue() + dataMap.get(PointType.Y_60).getValue();
            case Y_65 -> dataMap.get(PointType.Y_60).getValue() + dataMap.get(PointType.Y_70).getValue();
            case Y_75 -> dataMap.get(PointType.Y_70).getValue() + dataMap.get(PointType.Y_0).getValue();

            case Y_2_3 -> dataMap.get(PointType.Y_0).getValue() + dataMap.get(PointType.Y_5).getValue();
            case Y_1_2 -> dataMap.get(PointType.Y_0).getValue() + dataMap.get(PointType.Y_2_3).getValue();
            case Y_3_4 -> dataMap.get(PointType.Y_5).getValue() + dataMap.get(PointType.Y_2_3).getValue();
            case Y_7_8 -> dataMap.get(PointType.Y_5).getValue() + dataMap.get(PointType.Y_10).getValue();
            case Y_6_7 -> dataMap.get(PointType.Y_5).getValue() + dataMap.get(PointType.Y_7_8).getValue();
            case Y_8_9 -> dataMap.get(PointType.Y_10).getValue() + dataMap.get(PointType.Y_7_8).getValue();

            case Y_12_13 -> dataMap.get(PointType.Y_10).getValue() + dataMap.get(PointType.Y_15).getValue();
            case Y_11_12 -> dataMap.get(PointType.Y_10).getValue() + dataMap.get(PointType.Y_12_13).getValue();
            case Y_13_14 -> dataMap.get(PointType.Y_15).getValue() + dataMap.get(PointType.Y_12_13).getValue();
            case Y_17_18 -> dataMap.get(PointType.Y_15).getValue() + dataMap.get(PointType.Y_20).getValue();
            case Y_16_17 -> dataMap.get(PointType.Y_15).getValue() + dataMap.get(PointType.Y_17_18).getValue();
            case Y_18_19 -> dataMap.get(PointType.Y_20).getValue() + dataMap.get(PointType.Y_17_18).getValue();

            case Y_22_23 -> dataMap.get(PointType.Y_20).getValue() + dataMap.get(PointType.Y_25).getValue();
            case Y_21_22 -> dataMap.get(PointType.Y_20).getValue() + dataMap.get(PointType.Y_22_23).getValue();
            case Y_23_24 -> dataMap.get(PointType.Y_25).getValue() + dataMap.get(PointType.Y_22_23).getValue();
            case Y_27_28 -> dataMap.get(PointType.Y_25).getValue() + dataMap.get(PointType.Y_30).getValue();
            case Y_26_27 -> dataMap.get(PointType.Y_25).getValue() + dataMap.get(PointType.Y_27_28).getValue();
            case Y_28_29 -> dataMap.get(PointType.Y_30).getValue() + dataMap.get(PointType.Y_27_28).getValue();

            case Y_32_33 -> dataMap.get(PointType.Y_30).getValue() + dataMap.get(PointType.Y_35).getValue();
            case Y_31_32 -> dataMap.get(PointType.Y_30).getValue() + dataMap.get(PointType.Y_32_33).getValue();
            case Y_33_34 -> dataMap.get(PointType.Y_35).getValue() + dataMap.get(PointType.Y_32_33).getValue();
            case Y_37_38 -> dataMap.get(PointType.Y_35).getValue() + dataMap.get(PointType.Y_40).getValue();
            case Y_36_37 -> dataMap.get(PointType.Y_35).getValue() + dataMap.get(PointType.Y_37_38).getValue();
            case Y_38_39 -> dataMap.get(PointType.Y_40).getValue() + dataMap.get(PointType.Y_37_38).getValue();

            case Y_42_43 -> dataMap.get(PointType.Y_40).getValue() + dataMap.get(PointType.Y_45).getValue();
            case Y_41_42 -> dataMap.get(PointType.Y_40).getValue() + dataMap.get(PointType.Y_42_43).getValue();
            case Y_43_44 -> dataMap.get(PointType.Y_45).getValue() + dataMap.get(PointType.Y_42_43).getValue();
            case Y_47_48 -> dataMap.get(PointType.Y_45).getValue() + dataMap.get(PointType.Y_50).getValue();
            case Y_46_47 -> dataMap.get(PointType.Y_45).getValue() + dataMap.get(PointType.Y_47_48).getValue();
            case Y_48_49 -> dataMap.get(PointType.Y_50).getValue() + dataMap.get(PointType.Y_47_48).getValue();

            case Y_52_53 -> dataMap.get(PointType.Y_50).getValue() + dataMap.get(PointType.Y_55).getValue();
            case Y_51_52 -> dataMap.get(PointType.Y_50).getValue() + dataMap.get(PointType.Y_52_53).getValue();
            case Y_53_54 -> dataMap.get(PointType.Y_55).getValue() + dataMap.get(PointType.Y_52_53).getValue();
            case Y_57_58 -> dataMap.get(PointType.Y_55).getValue() + dataMap.get(PointType.Y_60).getValue();
            case Y_56_57 -> dataMap.get(PointType.Y_55).getValue() + dataMap.get(PointType.Y_57_58).getValue();
            case Y_58_59 -> dataMap.get(PointType.Y_60).getValue() + dataMap.get(PointType.Y_57_58).getValue();

            case Y_62_63 -> dataMap.get(PointType.Y_60).getValue() + dataMap.get(PointType.Y_65).getValue();
            case Y_61_62 -> dataMap.get(PointType.Y_60).getValue() + dataMap.get(PointType.Y_62_63).getValue();
            case Y_63_64 -> dataMap.get(PointType.Y_65).getValue() + dataMap.get(PointType.Y_62_63).getValue();
            case Y_67_68 -> dataMap.get(PointType.Y_65).getValue() + dataMap.get(PointType.Y_70).getValue();
            case Y_66_67 -> dataMap.get(PointType.Y_65).getValue() + dataMap.get(PointType.Y_67_68).getValue();
            case Y_68_69 -> dataMap.get(PointType.Y_70).getValue() + dataMap.get(PointType.Y_67_68).getValue();

            case Y_72_73 -> dataMap.get(PointType.Y_70).getValue() + dataMap.get(PointType.Y_75).getValue();
            case Y_71_72 -> dataMap.get(PointType.Y_70).getValue() + dataMap.get(PointType.Y_72_73).getValue();
            case Y_73_74 -> dataMap.get(PointType.Y_75).getValue() + dataMap.get(PointType.Y_72_73).getValue();
            case Y_77_78 -> dataMap.get(PointType.Y_75).getValue() + dataMap.get(PointType.Y_0).getValue();
            case Y_76_77 -> dataMap.get(PointType.Y_75).getValue() + dataMap.get(PointType.Y_77_78).getValue();
            case Y_78_79 -> dataMap.get(PointType.Y_0).getValue() + dataMap.get(PointType.Y_77_78).getValue();
            case PURPOSE_2040_SKY_AND_EARTH ->
                    RulesUtils.rule22(
                            RulesUtils.rule22(dataMap.get(PointType.B).getValue() + dataMap.get(PointType.G).getValue()) +
                            RulesUtils.rule22(dataMap.get(PointType.A).getValue() + dataMap.get(PointType.V).getValue()));
            case PURPOSE_4060_SOCIALIZATION ->
                    RulesUtils.rule22(
                            RulesUtils.rule22(dataMap.get(PointType.E).getValue() + dataMap.get(PointType.I).getValue()) +
                            RulesUtils.rule22(dataMap.get(PointType.J).getValue() + dataMap.get(PointType.Z).getValue()));
            case PURPOSE_AFTER60_SPIRITUAL ->
                    (RulesUtils.rule22((RulesUtils.rule22(dataMap.get(PointType.PURPOSE_2040_SKY_AND_EARTH).getValue()) +
                            RulesUtils.rule22(dataMap.get(PointType.PURPOSE_4060_SOCIALIZATION).getValue()))));
            case PURPOSE_PLANETARY -> (RulesUtils.rule22(dataMap.get(PointType.PURPOSE_4060_SOCIALIZATION).getValue()) +
                    (RulesUtils.rule22(RulesUtils.rule22(dataMap.get(PointType.PURPOSE_AFTER60_SPIRITUAL).getValue()))));
            case FAMILY_STRENGTH, INSIDE_POWER_CODE_2 -> (RulesUtils.rule22(dataMap.get(PointType.E).getValue() + dataMap.get(PointType.G).getValue() +
                    dataMap.get(PointType.Z).getValue()) + dataMap.get(PointType.I).getValue());
            case INSIDE_POWER_CODE_3 -> (RulesUtils.rule22(dataMap.get(PointType.INSIDE_POWER_CODE_1).getValue() + dataMap.get(PointType.INSIDE_POWER_CODE_2).getValue()));
        };
    }

    private HealthElement healthCalculate(HealthPointType healthPointType, EnumMap<PointType, DataMapElement> dataMap) {

        HealthElement healthElement = new HealthElement();
        return switch (healthPointType) {

            case CH1 -> new HealthElement(dataMap.get(PointType.A).getValue(), dataMap.get(PointType.B).getValue());
            case CH2 -> new HealthElement(dataMap.get(PointType.A2).getValue(), dataMap.get(PointType.B2).getValue());
            case CH3 -> new HealthElement(dataMap.get(PointType.A1).getValue(), dataMap.get(PointType.B1).getValue());
            case CH4 -> new HealthElement(dataMap.get(PointType.A3).getValue(), dataMap.get(PointType.B3).getValue());
            case CH5 -> new HealthElement(dataMap.get(PointType.D).getValue(), dataMap.get(PointType.D).getValue());
            case CH6 -> new HealthElement(dataMap.get(PointType.L).getValue(), dataMap.get(PointType.K).getValue());
            case CH7 -> new HealthElement(dataMap.get(PointType.V).getValue(), dataMap.get(PointType.G).getValue());
            case SUM -> new HealthElement();
        };
    }

    private void healthSumCalculate(EnumMap<HealthPointType, HealthElement> healthDataMap){

        int sumPhysics = 0;
        int sumEnergy = 0;
        int sumEmotion = 0;
        healthDataMap.remove(HealthPointType.SUM);
        for(Map.Entry<HealthPointType, HealthElement> entry : healthDataMap.entrySet()){
            sumPhysics += entry.getValue().getPhysical();
            sumEnergy += entry.getValue().getEnergy();
            sumEmotion += entry.getValue().getEmotions();
        }
        healthDataMap.put(HealthPointType.SUM,
                new HealthElement(
                        RulesUtils.rule22(sumPhysics),
                        RulesUtils.rule22(sumEnergy),
                        RulesUtils.rule22(sumEmotion)
                ));
    }
}
