package com.mediscreen.probability.diabete.proxies;

import com.mediscreen.probability.diabete.domain.Note;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "microservice-note", url = "localhost:8082")
public interface NoteProxy {

    @GetMapping("patHistory/{id}")
    List<Note> getNoteByIdPatient(@PathVariable("id") Integer id);
}
