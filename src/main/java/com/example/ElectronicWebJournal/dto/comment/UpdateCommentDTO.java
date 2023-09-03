package com.example.ElectronicWebJournal.dto.comment;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCommentDTO {
    private Integer id;
    @Size(max = 500, message = "В тексті може бути не більше 500 символів")
    private String comment;
}
