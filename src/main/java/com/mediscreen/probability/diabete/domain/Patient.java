package com.mediscreen.probability.diabete.domain;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Patient {

    private Integer id;
    private String lastName;
    private String firstName;
    private LocalDate dob;
    private Character gender;
    private String address;
    private String phone;
}
