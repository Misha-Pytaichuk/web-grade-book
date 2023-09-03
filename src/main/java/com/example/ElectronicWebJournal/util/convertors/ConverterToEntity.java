package com.example.ElectronicWebJournal.util.convertors;

import com.example.ElectronicWebJournal.dto.comment.CommentPostDTO;
import com.example.ElectronicWebJournal.dto.comment.UpdateCommentDTO;
import com.example.ElectronicWebJournal.dto.group.SetGroupDTO;
import com.example.ElectronicWebJournal.dto.task.SetTaskDTO;
import com.example.ElectronicWebJournal.models.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

@RequiredArgsConstructor
public class ConverterToEntity {
    private final ModelMapper modelMapper;

    public Person convertToPerson(Object personDTO){
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        return modelMapper.map(personDTO, Person.class);
    }

    public Group convertToGroup(Object groupDTO){
        return modelMapper.map(groupDTO, Group.class);
    }

    public Subject convertToSubject(Object subjectDTO){
        return modelMapper.map(subjectDTO, Subject.class);
    }

    public Grade convertToGrade(Object object){
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        return modelMapper.map(object, Grade.class);
    }

    public Comment convertToCommentForSave(CommentPostDTO commentDTO, Person person){
        Comment comment = modelMapper.map(commentDTO, Comment.class);
        comment.setPersonOwnerComment(person);
        return comment;
    }

    public Comment convertToCommentForUpdate(UpdateCommentDTO commentDTO){
        return modelMapper.map(commentDTO, Comment.class);
    }

    public Task convertToTask(SetTaskDTO task){
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        return modelMapper.map(task, Task.class);
    }
}
