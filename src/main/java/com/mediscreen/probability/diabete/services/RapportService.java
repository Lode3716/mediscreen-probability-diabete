package com.mediscreen.probability.diabete.services;

import com.mediscreen.probability.diabete.domain.Note;
import com.mediscreen.probability.diabete.domain.Patient;
import com.mediscreen.probability.diabete.domain.Rapport;
import com.mediscreen.probability.diabete.domain.filters.RiskLevel;
import com.mediscreen.probability.diabete.domain.filters.Symptoms;
import com.mediscreen.probability.diabete.proxies.NoteProxy;
import com.mediscreen.probability.diabete.proxies.PatientProxy;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Period;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Log4j2
@Service
public class RapportService {

    @Autowired
    PatientProxy patientProxy;

    @Autowired
    NoteProxy noteProxy;

    private static String MAN = "M";
    private static String WOMAN = "F";

    /**
     * Calculate the person's age
     *
     * @param dob :Date of Birth
     * @return the age
     */
    private Optional<Integer> calulAge(LocalDate dob) {
        try {
            return Optional.of(Period.between(dob, LocalDate.now()).getYears());
        } catch (DateTimeException ex) {
            log.error("The date for the age calculation is invalid : {}", ex.getMessage());
            return Optional.empty();
        }
    }

    /**
     * Counts the number of times or triggers are found in the note
     *
     * @param noteList
     * @return numbers triggers
     */
    public Integer checkTrigger(List<Note> noteList) {
        AtomicInteger count = new AtomicInteger();
        if (noteList.size() == 0 || noteList.isEmpty()) {
            return 0;
        } else {
            noteList.stream()
                    .map(Note::getNote)
                    .forEach(note ->
                    {
                        Long countNote = Arrays.stream(Symptoms.values())
                                .filter(symptoms -> (note.toLowerCase().replace(" ", "")
                                        .contains(symptoms.getLibelle().toLowerCase().replace(" ", ""))))
                                .count();
                        count.getAndAdd(Math.toIntExact(countNote));
                    });
        }
        return count.get();
    }

    /**
     * Create the patient report
     * @param idPatient
     * @return rapport patient
     */
    public Rapport createRapport(Integer idPatient) {
        Patient patient = patientProxy.getPatient(idPatient);
        Integer triggers = checkTrigger(noteProxy.getNoteByIdPatient(idPatient));
        Integer patientAge = calulAge(patient.getDob()).orElseThrow(() -> new DateTimeException("Calculation of date of birth failed"));

        Rapport rapport=new Rapport();
        rapport.setIdPatient(idPatient);
        rapport.setAge(patientAge);
        rapport.setLevel(RiskLevel.NONE.getLibelle());

        if(checkRiskLevelIsSafe(triggers))
        {
            rapport.setLevel(RiskLevel.NONE.getLibelle());
        }
        if(checkRiskLevelIsBorderline(triggers,patientAge))
        {
            rapport.setLevel(RiskLevel.BORDERLINE.getLibelle());
        }
        if(checkRiskLevelIsInDanger(String.valueOf(patient.getGender()),triggers,patientAge))
        {
            rapport.setLevel(RiskLevel.DANGER.getLibelle());
        }
        if(checkRiskLevelIsEarlyOnSet(String.valueOf(patient.getGender()),triggers,patientAge))
        {
            rapport.setLevel(RiskLevel.EARLY_ONSET.getLibelle());
        }


        log.info("Service sucess - Create Rapport patient id : {}",idPatient);
        return rapport;
    }


    /**
     * Rule for determining risk level SAFE :
     *    - no symptoms
     *
     * @param trigger number of triggers in notes
     */
    private Boolean checkRiskLevelIsSafe(int trigger) {
        return trigger == 0;
    }

    /**
     * Rule for determining risk level Bordeline :
     *   -  2 or more symptoms and over 30
     *
     * @param trigger number of triggers in notes
     * @param age of patient
     * @return true if the rules match
     */
    private Boolean checkRiskLevelIsBorderline(int trigger, int age) {
        return trigger >= 2 && age > 30;
    }

    /**
     * Rule for determining risk level Danger:
     *   - 3 or more symptoms, is a man and is under 30
     *   - 4 or more symptoms, is a woman and is under 30
     *   - 6 or more symptoms and over 30
     *
     * @param gender patient
     * @param trigger number of triggers in notes
     * @param age of patient
     * @return true if the rules match
     */
    private Boolean checkRiskLevelIsInDanger(String gender, int trigger, int age) {
        return trigger >= 3 && gender.equals(MAN) &&  age < 30 ||
                trigger >= 4 && gender.equals(WOMAN) &&  age < 30 ||
                trigger >= 6 &&  age > 30;
    }

    /**
     * Rule for determining risk level Early onset :
     * - 5 or more symptoms, is a man and is under 30
     * - 7 or more symptoms, is a woman and is under 30
     * - 8 or more symptoms and over 30
     * @param gender patient
     * @param trigger number of triggers in notes
     * @param age of patient
     * @return true if the rules match
     */
    private Boolean checkRiskLevelIsEarlyOnSet(String gender, int trigger, int age) {
        return trigger >= 5 && gender.equals(MAN) &&  age < 30 ||
                trigger >= 7 && gender.equals(WOMAN) &&  age < 30 ||
                trigger >= 8 &&  age > 30;
    }
}
