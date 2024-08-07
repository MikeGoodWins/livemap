package com.livemap.live.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class RequestModel {

    private Long phone;

    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDateTime birthday;

    private String name;

    private String comment;
}
