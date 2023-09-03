package com.example.ElectronicWebJournal.services.auth;

import com.example.ElectronicWebJournal.models.Grade;
import com.example.ElectronicWebJournal.models.Group;
import com.example.ElectronicWebJournal.models.Person;
import com.example.ElectronicWebJournal.repositories.PersonRepository;
import com.example.ElectronicWebJournal.security.PersonDetails;
import com.example.ElectronicWebJournal.services.GradeService;
import com.example.ElectronicWebJournal.services.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PersonDetailsService implements UserDetailsService {
    private final PersonRepository personRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println();
        Optional<Person> personOptional = personRepository.findPersonByEmail(email);
            if(personOptional.isEmpty()){
                throw new UsernameNotFoundException("User not found");
            }
            Person person = personOptional.get();
        System.out.println(person.getRole());

        return new PersonDetails(person);
    }
}
