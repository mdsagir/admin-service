package com.doctory.common;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class Util {

    private Util() {
    }

    public static LocalDateTime currentDate() {
        ZoneId zoneId = ZoneId.systemDefault();
        return LocalDateTime.now(zoneId);
    }
}
