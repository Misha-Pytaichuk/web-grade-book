package com.example.ElectronicWebJournal.services;

import com.example.ElectronicWebJournal.dto.comment.CommentPostDTO;
import com.example.ElectronicWebJournal.dto.comment.UpdateCommentDTO;
import com.example.ElectronicWebJournal.models.Comment;
import com.example.ElectronicWebJournal.models.Person;
import com.example.ElectronicWebJournal.repositories.CommentRepository;
import com.example.ElectronicWebJournal.repositories.GradeRepository;
import com.example.ElectronicWebJournal.util.convertors.ConverterToEntity;
import com.example.ElectronicWebJournal.util.exceptions.FindException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {
    private final CommentRepository commentRepository;
    private final ConverterToEntity converterToEntity;
    private final GradeRepository gradeRepository;

    @Transactional
    public void save(CommentPostDTO commentDTO, int id, Person person){
        Comment comment = converterToEntity.convertToCommentForSave(commentDTO, person);
        enrichComment(comment, id);
        commentRepository.save(comment);
    }

    @Transactional
    public void update(UpdateCommentDTO commentDTO){
        commentRepository.findById(commentDTO.getId()).orElseThrow(FindException::new);
        Comment comment = converterToEntity.convertToCommentForUpdate(commentDTO);
        commentRepository.save(comment);
    }

    @Transactional
    public void delete(int id){
        Comment comment = commentRepository.findById(id).orElseThrow(FindException::new);
        commentRepository.delete(comment);
    }
    private void enrichComment(Comment comment, int id){
        comment.setGrade(gradeRepository.findById(id).orElseThrow(FindException::new));
        comment.setCreateAt(LocalDateTime.now());
    }
}
