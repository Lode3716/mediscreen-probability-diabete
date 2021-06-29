package com.mediscreen.probability.diabete.services;

import com.mediscreen.probability.diabete.domain.Note;
import com.mediscreen.probability.diabete.domain.Patient;
import com.mediscreen.probability.diabete.domain.Rapport;
import com.mediscreen.probability.diabete.domain.filters.RiskLevel;
import com.mediscreen.probability.diabete.proxies.NoteProxy;
import com.mediscreen.probability.diabete.proxies.PatientProxy;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@Log4j2
@ExtendWith(MockitoExtension.class)
class RapportServiceTest {

    @InjectMocks
    private RapportService rapportService;

    @Mock
    private PatientProxy patientProxy;

    @Mock
    private NoteProxy noteProxy;

    private static Note note1;
    private static Note note2;
    private static Note note3;
    private static Note note4;

    private static List<Note> noteList1;
    private static List<Note> noteList2;
    private static List<Note> noteList3;
    private static List<Note> noteListAll;

    private static Patient patient1Man;
    private static Patient patient2Man;


    private static Patient patient1Women;
    private static Patient patient2Women;
    private static Patient patient3WommenDoBWrong;


    @BeforeEach
    void setUp() {
        note1 = new Note(1, "Le patient déclare qu'il est fumeur et qu'il a cessé de fumer l'année dernière ." +
                "Il se plaint également de crises d’apnée respiratoire anormales ." +
                "Tests de laboratoire indiquant un taux de cholestérol LDL élevé", "Medecin");
        note2 = new Note(1, "Le patient déclare qu'il lui est devenu difficile de monter les escaliers ." +
                "Il se plaint également d’être essoufflé." +
                "Tests de laboratoire indiquant que les anticorps sont élevés." +
                "Réaction aux médicaments", "Medecin");
        note3 = new Note(1, "Le patient déclare qu'il n'a aucun problème", "Medecin");
        note4 = new Note(1, "En recherche de perte de poids, microalbumine élevé", "Medecin");

        noteList1 = Arrays.asList(note1);
        noteList2 = Arrays.asList(note2);
        noteList3 = Arrays.asList(note3, note4);
        noteListAll = Arrays.asList(note1, note2, note3, note4);


        patient1Man = new Patient(1, LocalDate.of(1978, 12, 31), 'M');
        patient2Man = new Patient(1, LocalDate.of(2000, 9, 16), 'M');

        patient1Women = new Patient(2, LocalDate.of(1980, 1, 31), 'F');
        patient2Women = new Patient(2, LocalDate.of(1999, 4, 15), 'F');
        patient3WommenDoBWrong = new Patient(2, null, 'F');
    }

    @Test
    public void givenListNote_countSymptome_thenReturn3Symptomes() {
        Integer checkReturn = rapportService.checkTrigger(noteList1);
        assertThat(checkReturn).isEqualTo(3);
    }

    @Test
    public void givenListNoteWith2Note_countSymptome_thenReturn2Symptomes() {
        Integer checkReturn = rapportService.checkTrigger(noteList3);
        assertThat(checkReturn).isEqualTo(2);
    }


    @Test
    public void givenListNote_countSymptoms_thenReturn2Symptomes() {
        Integer checkReturn = rapportService.checkTrigger(noteList2);
        assertThat(checkReturn).isEqualTo(2);
    }

    @Test
    public void givenListNoteNull_countSymptome_thenReturn0Symptome() {
        List<Note> noteList = new ArrayList<>();
        Integer checkReturn = rapportService.checkTrigger(noteList);
        assertThat(checkReturn).isEqualTo(0);
    }

    @Test
    public void givenPatientMan_when3symptomsAnMore30old_ReturnAge41andBordeline() {
        when(patientProxy.getPatient(anyInt())).thenReturn(patient1Man);
        when(noteProxy.getNoteByIdPatient(anyInt())).thenReturn(noteList1);

        Rapport result = rapportService.createRapport(anyInt());

        log.info("Resultat du rapport : " + result.getLevel() + " / " + result.getAge());

        assertThat(result.getLevel()).isEqualTo(RiskLevel.BORDERLINE.getLibelle());
        assertThat(result.getAge()).isEqualTo(42);
    }

    @Test
    public void givenPatientMan_when7symptomsAnMore30old_ReturnAge41andDanger() {
        when(patientProxy.getPatient(anyInt())).thenReturn(patient1Man);
        when(noteProxy.getNoteByIdPatient(anyInt())).thenReturn(noteListAll);

        Rapport result = rapportService.createRapport(anyInt());

        log.info("Resultat du rapport : " + result.getLevel() + " / " + result.getAge());

        assertThat(result.getLevel()).isEqualTo(RiskLevel.DANGER.getLibelle());
        assertThat(result.getAge()).isEqualTo(42);
    }


    @Test
    public void givenPatientMan_when7symptomsAndLess30old_ReturnAge21andDanger() {
        when(patientProxy.getPatient(anyInt())).thenReturn(patient2Man);
        when(noteProxy.getNoteByIdPatient(anyInt())).thenReturn(noteListAll);

        Rapport result = rapportService.createRapport(anyInt());

        log.info("Resultat du rapport : " + result.getLevel() + " / " + result.getAge());

        assertThat(result.getLevel()).isEqualTo(RiskLevel.EARLY_ONSET.getLibelle());
        assertThat(result.getAge()).isEqualTo(20);
    }

    @Test
    public void givenPatientMan_when2symptomsAndLess30old_ReturnAge21andNone() {
        when(patientProxy.getPatient(anyInt())).thenReturn(patient2Man);
        when(noteProxy.getNoteByIdPatient(anyInt())).thenReturn(noteList2);

        Rapport result = rapportService.createRapport(anyInt());

        log.info("Resultat du rapport : " + result.getLevel() + " / " + result.getAge());

        assertThat(result.getLevel()).isEqualTo(RiskLevel.NONE.getLibelle());
        assertThat(result.getAge()).isEqualTo(20);
    }

    @Test
    public void givenPatientWomen_when3symptomsAnMore30old_ReturnAge41andBordeline() {
        when(patientProxy.getPatient(anyInt())).thenReturn(patient1Women);
        when(noteProxy.getNoteByIdPatient(anyInt())).thenReturn(noteList1);

        Rapport result = rapportService.createRapport(anyInt());

        log.info("Resultat du rapport : " + result.getLevel() + " / " + result.getAge());

        assertThat(result.getLevel()).isEqualTo(RiskLevel.BORDERLINE.getLibelle());
        assertThat(result.getAge()).isEqualTo(41);
    }

    @Test
    public void givenPatientWomen__when7symptomsAnMore30old_ReturnAge41andDanger() {
        when(patientProxy.getPatient(anyInt())).thenReturn(patient1Women);
        when(noteProxy.getNoteByIdPatient(anyInt())).thenReturn(noteListAll);

        Rapport result = rapportService.createRapport(anyInt());

        log.info("Resultat du rapport : " + result.getLevel() + " / " + result.getAge());

        assertThat(result.getLevel()).isEqualTo(RiskLevel.DANGER.getLibelle());
        assertThat(result.getAge()).isEqualTo(41);
    }


    @Test
    public void givenPatientWomen_when7symptomsAndLess30old_ReturnAge22andEarlyOnset() {
        when(patientProxy.getPatient(anyInt())).thenReturn(patient2Women);
        when(noteProxy.getNoteByIdPatient(anyInt())).thenReturn(noteListAll);

        Rapport result = rapportService.createRapport(anyInt());

        log.info("Resultat du rapport : " + result.getLevel() + " / " + result.getAge());

        assertThat(result.getLevel()).isEqualTo(RiskLevel.EARLY_ONSET.getLibelle());
        assertThat(result.getAge()).isEqualTo(22);
    }

    @Test
    public void givenPatientWomen_when2symptomsAndLess30old_ReturnAge22andNone() {
        when(patientProxy.getPatient(anyInt())).thenReturn(patient2Women);
        when(noteProxy.getNoteByIdPatient(anyInt())).thenReturn(noteList2);

        Rapport result = rapportService.createRapport(anyInt());

        log.info("Resultat du rapport : " + result.getLevel() + " / " + result.getAge());

        assertThat(result.getLevel()).isEqualTo(RiskLevel.NONE.getLibelle());
        assertThat(result.getAge()).isEqualTo(22);
    }

}
