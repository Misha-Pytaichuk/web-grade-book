package com.example.ElectronicWebJournal.dto.person;

import com.example.ElectronicWebJournal.dto.group.GroupDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class PersonStudentInfoDTO {
    private int id;
    private String firstname;
    private String secondName;
    //private Set<GroupDTO> groupsDTO;
}
