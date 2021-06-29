package com.mediscreen.probability.diabete.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Rapport {

    private Integer idPatient;
    private Integer age;
    private String level;
}
