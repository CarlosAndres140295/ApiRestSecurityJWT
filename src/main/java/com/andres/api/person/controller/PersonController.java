package com.andres.api.person.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.andres.api.person.Service.PersonService;
import com.andres.api.person.model.Person;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RestController
@RequestMapping("/api/persons")
@RequiredArgsConstructor
public class PersonController {
    
    private final PersonService service;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Person> getPerson(@PathVariable Long id){
        return service.getPerson(id);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void addPerson(@RequestBody Person person) {
        service.addPerson(person);
    }

}
