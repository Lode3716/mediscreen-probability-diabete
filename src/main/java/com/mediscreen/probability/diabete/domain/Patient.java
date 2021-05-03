package com.mediscreen.probability.diabete.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class Patient {

    private Integer id;
    private String lastName;
    private String firstName;
    private LocalDate dob;
    private Character gender;
    private String address;
    private String phone;

    public Patient(Integer id, LocalDate dob, Character gender) {
        this.id = id;
        this.dob = dob;
        this.gender = gender;
    }
}
