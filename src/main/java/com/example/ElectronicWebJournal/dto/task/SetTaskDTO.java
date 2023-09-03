package com.example.ElectronicWebJournal.dto.task;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SetTaskDTO {
    private int id;
    @Size(max = 300, message = "В описі не може бути більше 300 символів")
    private String description;
    private LocalDateTime taskDate;
}
