package com.example.ElectronicWebJournal.dto.group;

import com.example.ElectronicWebJournal.dto.person.PersonStudentInfoDTO;
import com.example.ElectronicWebJournal.dto.subject.SubjectDTO;
import com.example.ElectronicWebJournal.dto.task.TaskDTO;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class GroupDTO {
    private int id;
    @Size(max = 20, message = "Назва групи не може бути не більше 20 символів")
    private String name;
    private Set<SubjectDTO> subjectSet;
    private Set<PersonStudentInfoDTO> peopleGroups;
    private Set<TaskDTO> taskSet;
}
