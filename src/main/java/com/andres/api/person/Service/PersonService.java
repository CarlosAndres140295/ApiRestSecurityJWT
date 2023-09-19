package com.andres.api.person.Service;

import com.andres.api.person.repository.IPersonRepository;
import org.springframework.stereotype.Service;

import com.andres.api.person.model.Person;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonService {
    
    private final IPersonRepository repository;

    public void addPerson(Person person){
        repository.save(person);
    }

    public Optional<Person> getPerson(Long id) {
        return repository.findById(id);
    }
}
