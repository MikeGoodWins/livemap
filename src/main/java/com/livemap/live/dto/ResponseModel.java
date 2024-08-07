package com.livemap.live.dto;

import com.livemap.live.domain.entity.LiveMap;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseModel {

    private Long id;

    private Long phone;

    private LocalDateTime birthday;

    private LocalDateTime created;

    public ResponseModel(LiveMap liveMap) {
        this.setId(liveMap.getId());
        this.birthday = liveMap.getBirthday();
        this.phone = liveMap.getPhone();
        this.created = liveMap.getCreated();
    }
}
