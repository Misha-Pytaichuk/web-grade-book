package com.example.ElectronicWebJournal.dto.group;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SetGroupDTO {
    @Size(max = 20, message = "Назва групи не може бути не більше 20 символів")
    private String name;
}
