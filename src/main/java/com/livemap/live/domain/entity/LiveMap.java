package com.livemap.live.domain.entity;

import com.livemap.live.dto.RequestModel;
import com.livemap.live.models.DataMapElement;
import com.livemap.live.models.HealthElement;
import com.livemap.live.models.HealthPointType;
import com.livemap.live.models.PointType;
import com.livemap.live.utils.TimeUtils;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.EnumMap;
import java.util.Map;

@Getter
@Setter
@Entity
@Table(name = "raws")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Slf4j
public class LiveMap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private Long phone;

    private String name;

    private LocalDateTime birthday;

    @Getter
    @JdbcTypeCode(SqlTypes.JSON)
    private EnumMap<PointType, DataMapElement> dataMap;

    @Getter
    @JdbcTypeCode(SqlTypes.JSON)
    private EnumMap<HealthPointType, HealthElement> healthDataMap;

    private String description;

    private String comment;

    private LocalDateTime created;

    public LiveMap(RequestModel request) {
        this.phone = request.getPhone();
        this.name = request.getName();
        this.dataMap = new EnumMap<>(PointType.class);
        this.birthday = request.getBirthday();
        this.description = "None";
        this.created = TimeUtils.now();
    }

    public static LiveMap create(RequestModel request) {

        return new LiveMap(request);
    }

    public void addLivePoint(DataMapElement dataMapElement) {
        dataMap.put(dataMapElement.getType(), dataMapElement);
    }

    public void liveMapToString() {
        log.info("============================");
        log.info("Имя: {}", this.name);
        log.info("Телефон: {}", this.phone);
        log.info("Дата рождения: {}", this.birthday);
        log.info("============================");
        for (Map.Entry<PointType, DataMapElement> entry : dataMap.entrySet()) {
            log.info("|  {}  " + "||" + "  {}" + "  |", entry.getKey(), entry.getValue().getValue());
        }
        log.info("============================");
        log.info("Карта здоровья");
        for (Map.Entry<HealthPointType, HealthElement> entry : healthDataMap.entrySet()) {
            log.info("|  {}  " + "||" + "  {}  " + "||" + "  {}  " + "||" + "  {}  " + "  |", entry.getKey(), entry.getValue().getPhysical(), entry.getValue().getEnergy(), entry.getValue().getEmotions());
        }
        log.info("============================");
        log.info("Расчет завершен");
        log.info("============================");
    }
}
