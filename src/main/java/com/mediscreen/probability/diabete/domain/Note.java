package com.mediscreen.probability.diabete.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class Note {

    private Integer idPatient;
    private String note;
    private String practitioner;
    private LocalDate createDate;
    private LocalDate updateDate;

    public Note(Integer idPatient, String note, String practitioner) {
        this.idPatient = idPatient;
        this.note = note;
        this.practitioner = practitioner;
    }
}
