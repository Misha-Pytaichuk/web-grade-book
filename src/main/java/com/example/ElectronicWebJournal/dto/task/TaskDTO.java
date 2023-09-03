package com.example.ElectronicWebJournal.dto.task;

import com.example.ElectronicWebJournal.dto.grade.GradesIdDTO;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
public class TaskDTO {
    private int id;
    @Size(max = 300, message = "В описі не може бути більше 300 символів")
    private String description;
    private LocalDateTime taskDate;
    private Set<GradesIdDTO> gradeSet;
}
