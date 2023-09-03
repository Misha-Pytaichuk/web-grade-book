package com.example.ElectronicWebJournal.dto.subject;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateSubjectDTO {
    private int id;
    @Size(max = 100, message = "Назва предмету не може бути не більше 100 символів")
    private String subjectName;
}
