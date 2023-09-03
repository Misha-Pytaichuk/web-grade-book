package com.example.ElectronicWebJournal.dto.grade;

import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GradePostDTO {
    @Min(value = 0, message = "Оцінка не може бути менше 0")
    @Max(value = 100, message = "Оцінка не може бути більше 100")
    private double value;
    private Integer personId;
    private Integer subjectId;
    private Integer taskId;
}
