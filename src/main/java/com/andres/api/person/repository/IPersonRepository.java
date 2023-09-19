package com.andres.api.person.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.andres.api.person.model.Person;

@Repository
public interface IPersonRepository extends JpaRepository<Person, Long> {
    
}
