package com.example.ElectronicWebJournal.dto.person;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentRegistrationDTO {
@Size(max = 30, message = "В імені має бути не більше 30 символів")
    @NotBlank(message = "Це поле не може бути пустим")
    private String firstname;

    @Size(max = 30, message = "В прізвищі має бути не більше 30 символів")
    @NotBlank(message = "Це поле не може бути пустим")
    private String secondName;

    @Size(max = 30, message = "В цьому полі має бути не більше 30 символів")
    @NotBlank(message = "Це поле не може бути пустим")
    private String lastname;

    @Size(max = 30, message = "В імені має бути не більше 30 символів")
    @Pattern(regexp = "^\\+380\\d{9}$")
    @NotBlank(message = "Це поле не може бути пустим")
    private String telephoneNumber;

    @Email(message = "Не вірний формат")
    private String email;

    @NotBlank(message = "Це поле не може бути пустим")
    @Size(min = 4, max = 36, message = "Пароль повинен містити не менше 4 та не більше 36 символів")
    private String password;

    private Integer groupId;
}
