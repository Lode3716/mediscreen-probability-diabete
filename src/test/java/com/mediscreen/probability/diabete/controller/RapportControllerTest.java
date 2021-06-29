package com.mediscreen.probability.diabete.controller;

import com.mediscreen.probability.diabete.controller.exception.BadRequestException;
import com.mediscreen.probability.diabete.domain.Rapport;
import com.mediscreen.probability.diabete.domain.filters.RiskLevel;
import com.mediscreen.probability.diabete.services.RapportService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Log4j2
@ExtendWith(SpringExtension.class)
@WebMvcTest(RapportController.class)
class RapportControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private RapportService rapportService;


    @Test
    @DisplayName("Given id Patient then rapport is create return patient with code 201")
    public void givenIdPatient_whenGenerate_thenReturnCreate() throws Exception {

        Rapport rapport = new Rapport(1, 15, RiskLevel.NONE.getLibelle());
        when(rapportService.createRapport(anyInt())).thenReturn(rapport);

        String url = "/assess/".concat(String.valueOf(1));

        mvc.perform(get(url)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isCreated());
    }


    @Test
    @DisplayName("Given id Patient not found then rapport return is not found")
    public void givenIdPatientNotFound_whenGenerate_thenReturnNotFound() throws Exception {
        when(rapportService.createRapport(anyInt())).thenThrow(new BadRequestException("Not Found"));
        String url = "/assess/".concat(String.valueOf(1));

        mvc.perform(get(url)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


}
