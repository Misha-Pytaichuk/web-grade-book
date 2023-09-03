package com.example.ElectronicWebJournal.dto.comment;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentGetDTO {
    private int id;
    @Size(max = 500, message = "В тексті може бути не більше 500 символів")
    private String comment;
    private LocalDateTime createAt;
    private int personOwnerId;
}
