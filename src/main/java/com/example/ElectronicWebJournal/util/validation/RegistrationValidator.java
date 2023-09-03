package com.example.ElectronicWebJournal.util.validation;

import com.example.ElectronicWebJournal.models.Person;
import com.example.ElectronicWebJournal.services.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Component
@RequiredArgsConstructor
public class RegistrationValidator implements Validator {
    private final PersonService personService;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(Person.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;

        //Проверка на дубликат
        if (personService.findByEmail(person.getEmail()).isPresent()){
            errors.rejectValue("email", "", "User already exists");
        }
        if(personService.findByTelephoneNumber(person.getTelephoneNumber()).isPresent()){
            errors.rejectValue("telephoneNumber", "", "User with this telephone number already exists");
        }
    }
}
