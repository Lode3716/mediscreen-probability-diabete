package com.mediscreen.probability.diabete.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
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
