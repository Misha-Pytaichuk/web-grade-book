package com.example.ElectronicWebJournal.dto.grade;

import com.example.ElectronicWebJournal.dto.comment.CommentGetDTO;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
public class GradeGetDTO {
    private int id;
    @Min(0)
    @Max(100)
    private double value;
    private int personId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Set<CommentGetDTO> comments;
}
