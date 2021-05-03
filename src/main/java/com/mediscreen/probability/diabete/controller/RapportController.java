package com.mediscreen.probability.diabete.controller;


import com.mediscreen.probability.diabete.domain.Rapport;
import com.mediscreen.probability.diabete.services.RapportService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping(value = "assess")
public class RapportController {

    @Autowired
    RapportService service;

    /**
     * Generate rapport by Id patient
     *
     * @param id patient
     * @return Rapport generate
     */
    @GetMapping("/{id}")
    public ResponseEntity<Rapport> getGenerateRapport(@PathVariable("id") Integer id) {
        log.debug("GET : /assess/{}", id);

    return ResponseEntity.status(HttpStatus.CREATED).body(service.createRapport(id));


    }

}
