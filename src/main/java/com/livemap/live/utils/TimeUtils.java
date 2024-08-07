package com.livemap.live.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class TimeUtils {
    private static final ZoneId zid = ZoneId.of("UTC+3");


    private TimeUtils() {
    }

    public static LocalDateTime now() {
        return LocalDateTime.now(TimeUtils.zid);
    }

    public static ZoneId mskZone() {
        return ZoneId.of("UTC+3");
    }
}
