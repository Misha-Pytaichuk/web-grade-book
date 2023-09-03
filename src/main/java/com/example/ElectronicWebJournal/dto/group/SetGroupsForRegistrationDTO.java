package com.example.ElectronicWebJournal.dto.group;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class SetGroupsForRegistrationDTO {
    private Set<String> groupsId;
}
