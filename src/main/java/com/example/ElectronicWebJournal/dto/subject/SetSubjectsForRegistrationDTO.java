package com.example.ElectronicWebJournal.dto.subject;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class SetSubjectsForRegistrationDTO {
    private Set<String> subjectsId;
}
