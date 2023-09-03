package com.example.ElectronicWebJournal.services;

import com.example.ElectronicWebJournal.models.Group;
import com.example.ElectronicWebJournal.models.GroupsOfLecturer;
import com.example.ElectronicWebJournal.models.Person;
import com.example.ElectronicWebJournal.models.Subject;
import com.example.ElectronicWebJournal.repositories.GroupOfLecturerRepository;
import com.example.ElectronicWebJournal.repositories.GroupRepository;
import com.example.ElectronicWebJournal.repositories.SubjectRepository;
import com.example.ElectronicWebJournal.util.exceptions.FindException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GroupOfLecturerService {
    private final GroupOfLecturerRepository repository;
    private final GroupRepository groupRepository;
    private final SubjectRepository subjectRepository;

    @Transactional
    public void save(Person person, int subjectId, int groupId){
        Group group = groupRepository.findById(groupId).orElseThrow(FindException::new);
        Subject subject = subjectRepository.findById(subjectId).orElseThrow(FindException::new);

        GroupsOfLecturer groupsOfLecturer = new GroupsOfLecturer(person, subject, group);
        groupsOfLecturer = repository.save(groupsOfLecturer);

        person.getGroupsOfLecturers().add(groupsOfLecturer);
        group.getGroupsOfLecturers().add(groupsOfLecturer);
        subject.getGroupsOfLecturers().add(groupsOfLecturer);
    }
}
