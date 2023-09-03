package com.example.ElectronicWebJournal.dto.subject;

import com.example.ElectronicWebJournal.dto.grade.GradeGetDTO;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class SubjectDTO {
    private int id;
    @Size(max = 100, message = "Назва предмету не може бути не більше 100 символів")
    private String subjectName;
    private Set<GradeGetDTO> gradeList;
}
