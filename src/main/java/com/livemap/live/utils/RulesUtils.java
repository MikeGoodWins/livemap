package com.livemap.live.utils;

public class RulesUtils {


    private RulesUtils() {
    }

    public static Integer rule22(Integer value) {
        int result = value;
        if (value > 22) {
            int dec = value / 10;
            int ed = value - dec * 10;
            result = dec + ed;
        }
        if (result > 22) {
            return rule22(result);
        } else {
            return result;
        }
    }
}
