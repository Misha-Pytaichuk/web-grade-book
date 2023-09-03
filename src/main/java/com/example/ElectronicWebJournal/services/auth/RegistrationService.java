package com.example.ElectronicWebJournal.services.auth;

import com.example.ElectronicWebJournal.controllers.AdminController;
import com.example.ElectronicWebJournal.dto.util_for_registration.GroupSubjectRegDTO;
import com.example.ElectronicWebJournal.models.Group;
import com.example.ElectronicWebJournal.models.GroupsOfLecturer;
import com.example.ElectronicWebJournal.models.Person;
import com.example.ElectronicWebJournal.models.Subject;
import com.example.ElectronicWebJournal.repositories.GroupOfLecturerRepository;
import com.example.ElectronicWebJournal.repositories.GroupRepository;
import com.example.ElectronicWebJournal.repositories.PersonRepository;
import com.example.ElectronicWebJournal.repositories.SubjectRepository;
import com.example.ElectronicWebJournal.services.GroupOfLecturerService;
import com.example.ElectronicWebJournal.services.GroupService;
import com.example.ElectronicWebJournal.services.SubjectService;
import com.example.ElectronicWebJournal.util.exceptions.FindException;
import com.example.ElectronicWebJournal.util.exceptions.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;
    private final GroupRepository groupRepository;
    private final SubjectRepository subjectRepository;
    private final GroupOfLecturerRepository groupOfLecturerRepository;

    @Transactional
    public void registerStudent(Person person, Integer groupId) {
        Set<Group> groupSet = new HashSet<>();

        if (groupId == null)
            throw new FindException();
        else {
            Group group = groupRepository.findById(groupId).orElse(null);

            if (group == null) {
                throw new ValidationException("Такої групи не існує");
            }

            group.getPeopleGroups().add(person);
            groupSet.add(group);
            System.out.println(group.getName());


            person.setGroups(groupSet);
            enrichPerson(person);
            personRepository.save(person);
        }
    }

    @Transactional
    public void registerLecturer(Person person) {
        enrichPerson(person);
        personRepository.save(person);
    }

    //"Обогащение" человека
    private void enrichPerson(Person person){
        person.setCreatedAt(LocalDateTime.now());
        person.setUpdatedAt(person.getCreatedAt());
        person.setRole(AdminController.globalRole);
        person.setPassword(passwordEncoder.encode(person.getPassword()));
    }
}
