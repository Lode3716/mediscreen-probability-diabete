package com.mediscreen.probability.diabete.domain;


import com.mediscreen.probability.diabete.domain.filters.RiskLevel;
import lombok.Data;

@Data
public class Rapport {

    private Integer idPatient;
    private Integer age;

    private RiskLevel level;
}
