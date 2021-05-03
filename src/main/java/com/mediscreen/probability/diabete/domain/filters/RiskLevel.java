package com.mediscreen.probability.diabete.domain.filters;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RiskLevel {
    NONE("none"),
    BORDERLINE("borderline"),
    DANGER("in danger"),
    EARLY_ONSET("early onset");

    private final String libelle;

}
