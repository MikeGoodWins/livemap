package com.livemap.live.models;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Getter
public class UserInfo {

    private LocalDateTime birthday;

    private ArrayList<Integer> userData;

    public UserInfo(LocalDateTime birthday) {
        this.userData = new ArrayList();
        this.birthday = birthday;
        init(birthday);
    }

    private void init(LocalDateTime birthday) {

        int year = birthday.getYear();
        int month = birthday.getMonthValue();
        int day = birthday.getDayOfMonth();

        userData.add(day / 10);
        userData.add(day - userData.get(0));
        userData.add(month / 10);
        userData.add(month - userData.get(2));
        userData.add(year / 1000);
        userData.add((year - userData.get(4) * 1000) / 100);
        userData.add((year - userData.get(4) * 1000 - userData.get(5) * 100) / 10);
        userData.add(year - userData.get(4) * 1000 - userData.get(5) * 100 - userData.get(6) * 10);
    }
}
