package com.example.ElectronicWebJournal.dto.person;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonAuthenticationDTO {
    @Email(message = "Не вірний формат")
    private String email;

    @NotBlank(message = "Це поле не може бути пустим")
    @Size(min = 4, max = 36, message = "Пароль повинен містити не менше 4 та не більше 36 символів")
    private String password;
}
