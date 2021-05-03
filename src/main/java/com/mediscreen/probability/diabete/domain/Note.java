package com.mediscreen.probability.diabete.domain;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Note {

    private Integer idPatient;
    private String note;
    private String practitioner;
    private LocalDate createDate;
    private LocalDate updateDate;
}
