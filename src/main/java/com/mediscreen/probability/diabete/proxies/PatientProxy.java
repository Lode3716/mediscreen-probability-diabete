package com.mediscreen.probability.diabete.proxies;

import com.mediscreen.probability.diabete.domain.Patient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "microservice-patient", url = "localhost:8081")
public interface PatientProxy {

    @GetMapping("patient/{id}")
    Patient getPatient(@PathVariable("id") Integer id);
}
