package com.example.ElectronicWebJournal.services;

import com.example.ElectronicWebJournal.models.Person;
import com.example.ElectronicWebJournal.repositories.PersonRepository;
import com.example.ElectronicWebJournal.util.exceptions.FindPersonException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PersonService {
    private final PersonRepository repository;

    public Person findById(int id){
        return repository.findPersonById(id).orElseThrow(FindPersonException::new);
    }

    public Optional<Person> findByEmail(String email)
    {
        return repository.findPersonByEmail(email);
    }

    public Optional<Person> findByTelephoneNumber(String num)
    {
        return repository.findPersonByTelephoneNumber(num);
    }
    @Transactional
    public void delete(Person person){
        repository.delete(person);
    }
}
