package com.example.ElectronicWebJournal.dto.grade;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GradeUpdateDTO {
    private int id;
    @Min(value = 0, message = "Оцінка не може бути менше 0")
    @Max(value = 100, message = "Оцінка не може бути більше 100")
    private Double value;
}
