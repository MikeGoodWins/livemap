package com.livemap.live.models;

import com.livemap.live.utils.RulesUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HealthElement {

    private Integer physical;

    private Integer energy;

    private Integer emotions;

    public HealthElement(Integer physical, Integer energy) {
        this.physical = physical;
        this.energy = energy;
        this.emotions = RulesUtils.rule22(this.physical + this.energy);
    }
}
